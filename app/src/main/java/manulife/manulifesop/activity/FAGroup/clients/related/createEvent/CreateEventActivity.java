package manulife.manulifesop.activity.FAGroup.clients.related.createEvent;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.SwitchCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import manulife.manulifesop.ProjectApplication;
import manulife.manulifesop.R;
import manulife.manulifesop.api.ObjectResponse.ActivityDetail;
import manulife.manulifesop.base.BaseActivity;
import manulife.manulifesop.element.callbackInterface.CallBackConfirmDialog;
import manulife.manulifesop.util.Utils;


public class CreateEventActivity extends BaseActivity<CreateEventPresenter> implements CreateEventContract.View, View.OnClickListener {

    @BindView(R.id.txt_actionbar_title)
    TextView txtActionbarTitle;
    @BindView(R.id.layout_btn_back)
    LinearLayout layoutBackButton;
    @BindView(R.id.status_bar)
    View viewStatusBar;

    @BindView(R.id.layout_root)
    RelativeLayout layoutRoot;

    @BindView(R.id.edt_title)
    EditText edtTitle;
    @BindView(R.id.txt_type)
    TextView txtType;

    @BindView(R.id.layout_start_date)
    RelativeLayout layoutStartDate;
    @BindView(R.id.txt_start_date)
    TextView txtStartDate;
    @BindView(R.id.txt_start_time)
    TextView txtStartTime;

    @BindView(R.id.txt_end_time)
    TextView txtEndTime;

    @BindView(R.id.edt_location)
    EditText edtLocation;
    @BindView(R.id.txt_notification_time)
    TextView txtNotificationTime;

    @BindView(R.id.switch_allday)
    SwitchCompat switchAllday;
    @BindView(R.id.switch_boss)
    SwitchCompat switchBoss;

    @BindView(R.id.txt_contact_name)
    TextView txtContactName;

    @BindView(R.id.edt_note)
    EditText edtNote;

    @BindView(R.id.layout_create)
    LinearLayout layoutCreate;
    @BindView(R.id.txt_button)
    TextView txtButton;

    //variable for add event
    private int mTypeInt;
    private int mContactID;

    //variable for update event
    //private ActivityDetail mData;
    private boolean mIsUpdate;
    private int mEventID;
    private ActivityDetail mData;

    private boolean mIsRecruit;

    private AlertDialog alertDialog;
    private String selectedStartTime;
    private String selectedEndTime;

    //variable for update event in local calendar
    private String mOldTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event_contact);
        mActionListener = new CreateEventPresenter(this, this);
        hideKeyboardOutside(layoutRoot);
        mTypeInt = getIntent().getIntExtra("typeInt", 1);
        mContactID = getIntent().getIntExtra("contactID", 0);
        mIsRecruit = getIntent().getBooleanExtra("isRecruit", false);
        mEventID = getIntent().getIntExtra("eventID", -1);
        setupSupportForApp();

        if (mEventID != -1) {
            mActionListener.getActivityDetail(mEventID);
            mIsUpdate = true;
            txtButton.setText("Cập nhật sự kiện");
            txtActionbarTitle.setText("Cập nhật sự kiện");
            txtType.setClickable(false);
        } else {
            initViewsAdd();
            mIsUpdate = false;
            txtType.setClickable(true);
        }
        initViewEvents();
    }

    private void setupSupportForApp() {
        //txtActionbarTitle.setText(getResources().getString(R.string.activity_create_plan_title_actionbar));
        txtActionbarTitle.setText("Tạo sự kiện");
        layoutBackButton.setVisibility(View.VISIBLE);

        int statusBarHeight = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = getResources().getDimensionPixelSize(resourceId);
        }
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) viewStatusBar.getLayoutParams();
        params.height = statusBarHeight;
        viewStatusBar.setLayoutParams(params);
    }

    @Override
    public void showUpdateViews(ActivityDetail mData) {
        this.mData = mData;
        edtTitle.setText(mData.data.name);
        mOldTitle = mData.data.name;
        txtType.setText(ProjectApplication.getInstance().getEventStringFromType(mData.data.type));
        mTypeInt = mData.data.type;
        if(mData.data.type >4)
            mIsRecruit = true;
        else
            mIsRecruit = false;

        switchAllday.setChecked(mData.data.fullDate);

        String startDay = Utils.convertStringTimeZoneDateToStringDate(
                mData.data.startDate, "yyyy-MM-dd'T'HH:mm:ss.sss'Z'", "dd/MM/yyyy"
        );
        String startTime = Utils.convertStringTimeZoneDateToStringDate(
                mData.data.startDate, "yyyy-MM-dd'T'HH:mm:ss.sss'Z'", "HH:mm"
        );
        String endTime = Utils.convertStringTimeZoneDateToStringDate(
                mData.data.endDate, "yyyy-MM-dd'T'HH:mm:ss.sss'Z'", "HH:mm"
        );
        txtStartDate.setText(startDay);
        if (mData.data.fullDate) {
            txtStartTime.setText("00:01");
            txtEndTime.setText("23:59");
        } else {
            txtStartTime.setText(startTime);
            txtEndTime.setText(endTime);
        }
        edtLocation.setText(mData.data.location);

        txtNotificationTime.setText("Báo trước " + mData.data.notification + " phút");
        txtContactName.setText(mData.data.manulifeLead.name);
        switchBoss.setChecked(mData.data.isSupport);
        edtNote.setText(mData.data.description);
    }

    private void initViewsAdd() {
        txtContactName.setText(getIntent().getStringExtra("name"));
        //set default date
        Calendar calendar = Calendar.getInstance();
        String currentDate = Utils.convertDateToString(calendar.getTime(), "dd/MM/yyyy");
        String currentTime = Utils.convertDateToString(calendar.getTime(), "HH:mm");

        txtStartDate.setText(currentDate);
        txtStartTime.setText(currentTime);
        txtEndTime.setText(currentTime);

        selectedStartTime = currentTime;
        selectedEndTime = currentTime;

        txtType.setText(ProjectApplication.getInstance().getEventStringFromType(mTypeInt));
    }

    private void initViewEvents() {
        Calendar calendar = Calendar.getInstance();
        String currentTime = Utils.convertDateToString(calendar.getTime(), "HH:mm");

        selectedStartTime = currentTime;
        selectedEndTime = currentTime;

        switchAllday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    txtStartTime.setText("00:01");
                    txtEndTime.setText("23:59");

                } else {
                    txtStartTime.setText(selectedStartTime);
                    txtEndTime.setText(selectedEndTime);
                }
            }
        });
    }

    @Override
    public void showMenuChooseEvent() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(CreateEventActivity.this);

        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_create_event_contact, null);

        initDialogEvent(dialogView);

        dialogBuilder.setView(dialogView);

        alertDialog = dialogBuilder.create();
        alertDialog.setCancelable(true);
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        //alertDialog.getWindow().setGravity(Gravity.BOTTOM);

        alertDialog.show();
    }

    private void initDialogEvent(View view) {
        view.findViewById(R.id.txt_first_meet).setOnClickListener(this);
        view.findViewById(R.id.txt_advisory).setOnClickListener(this);
        view.findViewById(R.id.txt_sign).setOnClickListener(this);
        view.findViewById(R.id.txt_different).setOnClickListener(this);
        view.findViewById(R.id.btn_cancel).setOnClickListener(this);
    }

    @Override
    public void showMenuChooseEventUM() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(CreateEventActivity.this);

        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_create_event_contact_um, null);

        initDialogEventUM(dialogView);

        dialogBuilder.setView(dialogView);

        alertDialog = dialogBuilder.create();
        alertDialog.setCancelable(true);
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        alertDialog.getWindow().setGravity(Gravity.BOTTOM);

        alertDialog.show();
    }

    private void initDialogEventUM(View view) {
        view.findViewById(R.id.txt_survey).setOnClickListener(this);
        view.findViewById(R.id.txt_cop).setOnClickListener(this);
        view.findViewById(R.id.txt_mit).setOnClickListener(this);
        view.findViewById(R.id.btn_cancel).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.txt_first_meet: {
                mTypeInt = 1;
                txtType.setText(ProjectApplication.getInstance().getEventStringFromType(1));
                alertDialog.dismiss();
                break;
            }
            case R.id.txt_advisory: {
                mTypeInt = 2;
                txtType.setText(ProjectApplication.getInstance().getEventStringFromType(2));
                alertDialog.dismiss();
                break;
            }
            case R.id.txt_sign: {
                mTypeInt = 3;
                txtType.setText(ProjectApplication.getInstance().getEventStringFromType(3));
                alertDialog.dismiss();
                break;
            }
            case R.id.txt_different: {
                mTypeInt = 4;
                txtType.setText(ProjectApplication.getInstance().getEventStringFromType(4));
                alertDialog.dismiss();
                break;
            }
            case R.id.txt_survey: {
                mTypeInt = 7;
                txtType.setText(ProjectApplication.getInstance().getEventStringFromType(4));
                alertDialog.dismiss();
                break;
            }
            case R.id.txt_cop: {
                mTypeInt = 5;
                txtType.setText(ProjectApplication.getInstance().getEventStringFromType(4));
                alertDialog.dismiss();
                break;
            }
            case R.id.txt_mit: {
                mTypeInt = 6;
                txtType.setText(ProjectApplication.getInstance().getEventStringFromType(4));
                alertDialog.dismiss();
                break;
            }
            case R.id.btn_cancel: {
                alertDialog.dismiss();
                break;
            }

            case R.id.txt_10_minute: {
                txtNotificationTime.setText("Báo trước 10 phút");
                txtNotificationTime.setTag(10);
                alertDialog.dismiss();
                break;
            }
            case R.id.txt_20_minute: {
                txtNotificationTime.setText("Báo trước 20 phút");
                txtNotificationTime.setTag(20);
                alertDialog.dismiss();
                break;
            }
            case R.id.txt_30_minute: {
                txtNotificationTime.setText("Báo trước 30 phút");
                txtNotificationTime.setTag(30);
                alertDialog.dismiss();
                break;
            }
            case R.id.txt_60_minute: {
                txtNotificationTime.setText("Báo trước 1 tiếng");
                txtNotificationTime.setTag(60);
                alertDialog.dismiss();
                break;
            }
        }
    }

    @Override
    public void showMenuChooseTimeRemind() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(CreateEventActivity.this);

        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_event_remind_time, null);

        initDialogEventRemind(dialogView);

        dialogBuilder.setView(dialogView);

        alertDialog = dialogBuilder.create();
        alertDialog.setCancelable(true);
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        //alertDialog.getWindow().setGravity(Gravity.BOTTOM);

        alertDialog.show();
    }

    private void initDialogEventRemind(View view) {
        view.findViewById(R.id.txt_10_minute).setOnClickListener(this);
        view.findViewById(R.id.txt_20_minute).setOnClickListener(this);
        view.findViewById(R.id.txt_30_minute).setOnClickListener(this);
        view.findViewById(R.id.txt_60_minute).setOnClickListener(this);
        view.findViewById(R.id.btn_cancel).setOnClickListener(this);
    }

    private void showDialogDateTimePicker() {
        final View dialogView = View.inflate(this, R.layout.date_time_time_picker,
                null);
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();

        dialogView.findViewById(R.id.btn_date_time_set).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        DatePicker datePicker = (DatePicker) dialogView
                                .findViewById(R.id.date_picker);
                        TimePicker startTimePicker = (TimePicker) dialogView.findViewById(R.id.time_picker_start);
                        TimePicker endTimePicker = (TimePicker) dialogView.findViewById(R.id.time_picker_end);

                        int day = datePicker.getDayOfMonth();
                        int month = datePicker.getMonth();
                        int year = datePicker.getYear();
                        int hourStart, hourEnd;
                        if (Build.VERSION.SDK_INT >= 23) {
                            hourStart = startTimePicker.getHour();
                            hourEnd = endTimePicker.getHour();
                        } else {
                            hourStart = startTimePicker.getCurrentHour();
                            hourEnd = endTimePicker.getCurrentHour();
                        }


                        int minuteStart, minuteEnd;
                        if (Build.VERSION.SDK_INT >= 23) {
                            minuteStart = startTimePicker.getMinute();
                            minuteEnd = endTimePicker.getMinute();
                        } else {
                            minuteStart = startTimePicker.getCurrentMinute();
                            minuteEnd = endTimePicker.getCurrentMinute();
                        }

                        Calendar calendarStart = Calendar.getInstance();
                        calendarStart.set(year, month, day, hourStart, minuteStart);

                        Calendar calendarEnd = Calendar.getInstance();
                        calendarEnd.set(year, month, day, hourEnd, minuteEnd);

                        if (calendarStart.getTime().before(calendarEnd.getTime())) {
                            txtStartDate.setText(Utils.convertDateToString(calendarStart.getTime(), "dd/MM/yyyy"));
                            txtStartTime.setText(Utils.convertDateToString(calendarStart.getTime(), "HH:mm"));

                            txtEndTime.setText(Utils.convertDateToString(calendarEnd.getTime(), "HH:mm"));
                            txtEndTime.setTag(Utils.convertDateToString(calendarEnd.getTime(), "dd/MM/yyyy HH:mm"));

                            selectedStartTime = txtStartTime.getText().toString();
                            selectedEndTime = txtEndTime.getText().toString();

                            alertDialog.dismiss();
                        } else {
                            showMessage("Thông báo", "Thời gian bắt đầu phải nhỏ hơn thời gian kết thúc!", SweetAlertDialog.WARNING_TYPE);
                        }
                    }
                });

        //auto scroll when chose date
        Calendar calendar = Calendar.getInstance();
        if (mIsUpdate) {
            calendar.setTime(Utils.convertStringToDate(txtStartDate.getText().toString(), "dd/MM/yyyy"));
        }
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePicker datePicker = (DatePicker) dialogView.findViewById(R.id.date_picker);
        datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
            public void onDateChanged(DatePicker view, int year, int month,
                                      int day) {
                NestedScrollView scroll = (NestedScrollView) dialogView.findViewById(R.id.scroll);
                TimePicker timePicker = (TimePicker) dialogView.findViewById(R.id.time_picker_start);
                //ObjectAnimator.ofInt(scroll, "scrollY",  timePicker.getBottom()).setDuration(1000).start();
                Utils.smoothScrollViewToPosition(getApplicationContext(), scroll, timePicker.getTop());
            }
        });

        //autoscroll when choose start time
        TimePicker startTimePicker = (TimePicker) dialogView.findViewById(R.id.time_picker_start);
        int[] currentMinute = new int[1];
        if (Build.VERSION.SDK_INT >= 23) {
            currentMinute[0] = startTimePicker.getMinute();
        } else {
            currentMinute[0] = startTimePicker.getCurrentMinute();
        }
        if (mIsUpdate) {
            //set time for start
            Calendar c = Calendar.getInstance();
            c.setTime(Utils.convertStringToDate(txtStartTime.getText().toString(), "HH:mm"));
            if (Build.VERSION.SDK_INT >= 23) {
                startTimePicker.setHour(c.get(Calendar.HOUR_OF_DAY));
                startTimePicker.setMinute(c.get(Calendar.MINUTE));
            } else {
                startTimePicker.setCurrentHour(c.get(Calendar.HOUR_OF_DAY));
                startTimePicker.setCurrentMinute(c.get(Calendar.MINUTE));
            }
            //set time for end
            TimePicker endTimePicker = (TimePicker) dialogView.findViewById(R.id.time_picker_end);
            c.setTime(Utils.convertStringToDate(txtEndTime.getText().toString(), "HH:mm"));
            if (Build.VERSION.SDK_INT >= 23) {
                endTimePicker.setHour(c.get(Calendar.HOUR_OF_DAY));
                endTimePicker.setMinute(c.get(Calendar.MINUTE));
            } else {
                endTimePicker.setCurrentHour(c.get(Calendar.HOUR_OF_DAY));
                endTimePicker.setCurrentMinute(c.get(Calendar.MINUTE));
            }
        }

        startTimePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int hourse, int minute) {

                if (minute != currentMinute[0]) {
                    NestedScrollView scroll = (NestedScrollView) dialogView.findViewById(R.id.scroll);
                    TimePicker endTimePicker = (TimePicker) dialogView.findViewById(R.id.time_picker_end);
                    //ObjectAnimator.ofInt(scroll, "scrollY",  timePicker.getBottom()).setDuration(1000).start();
                    Utils.smoothScrollViewToPosition(getApplicationContext(), scroll, endTimePicker.getBottom());

                    //set time end
                    if (Build.VERSION.SDK_INT >= 23) {
                        endTimePicker.setHour(hourse);
                        endTimePicker.setMinute(minute);
                    } else {
                        endTimePicker.setCurrentHour(hourse);
                        endTimePicker.setCurrentMinute(minute);
                    }

                    currentMinute[0] = minute;
                }
            }
        });


        alertDialog.setView(dialogView);
        alertDialog.show();
    }

    private boolean checkValidate() {
        if (edtTitle.getText().length() < 1) {
            showMessage("Thông báo", "Nhập tiêu đề cho sự kiện", SweetAlertDialog.WARNING_TYPE);
            return false;
        }
        return true;
    }

    @Override
    public void finishCreate() {
        setResult(RESULT_OK);
        finish();
    }

    @OnClick({R.id.layout_btn_back, R.id.layout_start_date, R.id.layout_create,
            R.id.txt_type, R.id.txt_notification_time})
    public void onClickView(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.layout_btn_back: {
                onBackPressed();
                break;
            }
            case R.id.layout_start_date: {
                showDialogDateTimePicker();
                break;
            }
            case R.id.layout_create: {
                if (checkValidate()) {
                    if (mIsUpdate)
                        updateEvent();
                    else
                        addEvent();

                }
                break;
            }
            case R.id.txt_type: {
                if (mIsRecruit)
                    showMenuChooseEventUM();
                else
                    showMenuChooseEvent();
                break;
            }
            case R.id.txt_notification_time: {
                showMenuChooseTimeRemind();
                break;
            }

        }
    }

    private void addEvent() {
        showConfirm("Xác nhận", "Đồng ý tạo sự kiện", "Đồng ý",
                "Hủy", SweetAlertDialog.WARNING_TYPE, new CallBackConfirmDialog() {
                    @Override
                    public void DiaglogPositive() {
                        String startTimeInput = Utils.convertStringDateToStringDate(
                                txtStartDate.getText().toString() + " " + txtStartTime.getText().toString(), "dd/MM/yyyy HH:mm",
                                "yyyy-MM-dd HH:mm"
                        );
                        String endTimeInput = Utils.convertStringDateToStringDate(
                                txtStartDate.getText().toString() + " " + txtEndTime.getText().toString(), "dd/MM/yyyy HH:mm",
                                "yyyy-MM-dd HH:mm"
                        );
                        mActionListener.createEvent(mContactID, mTypeInt, edtTitle.getText().toString(),
                                edtLocation.getText().toString(), startTimeInput, endTimeInput,
                                edtNote.getText().toString(), switchAllday.isChecked(), Integer.valueOf(txtNotificationTime.getTag().toString()), switchBoss.isChecked());
                    }

                    @Override
                    public void DiaglogNegative() {
                    }
                });
    }

    public void updateEvent() {
        showConfirm("Xác nhận", "Cập nhật sự kiện", "Đồng ý",
                "Hủy", SweetAlertDialog.WARNING_TYPE, new CallBackConfirmDialog() {
                    @Override
                    public void DiaglogPositive() {
                        String startTimeInput = Utils.convertStringDateToStringDate(
                                txtStartDate.getText().toString() + " " + txtStartTime.getText().toString(), "dd/MM/yyyy HH:mm",
                                "yyyy-MM-dd HH:mm"
                        );
                        String endTimeInput = Utils.convertStringDateToStringDate(
                                txtStartDate.getText().toString() + " " + txtEndTime.getText().toString(), "dd/MM/yyyy HH:mm",
                                "yyyy-MM-dd HH:mm"
                        );
                        mActionListener.updateEvent(mData.data.id, mTypeInt, mOldTitle, edtTitle.getText().toString(),
                                edtLocation.getText().toString(), startTimeInput, endTimeInput,
                                edtNote.getText().toString(), switchAllday.isChecked(), Integer.valueOf(txtNotificationTime.getTag().toString()), switchBoss.isChecked());
                    }

                    @Override
                    public void DiaglogNegative() {
                    }
                });
    }
}
