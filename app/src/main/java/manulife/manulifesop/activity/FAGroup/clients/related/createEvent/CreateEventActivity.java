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

import java.util.Calendar;

import butterknife.BindView;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import manulife.manulifesop.ProjectApplication;
import manulife.manulifesop.R;
import manulife.manulifesop.base.BaseActivity;
import manulife.manulifesop.util.Utils;


public class CreateEventActivity extends BaseActivity<CreateEventPresenter> implements CreateEventContract.View,View.OnClickListener {

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

    @BindView(R.id.layout_end_date)
    RelativeLayout layoutEndDate;
    @BindView(R.id.txt_end_date)
    TextView txtEndDate;
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

    private int mTypeInt;
    private int mContactID;

    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event_contact);
        mActionListener = new CreateEventPresenter(this, this);
        hideKeyboardOutside(layoutRoot);
        mTypeInt = getIntent().getIntExtra("typeInt", 1);
        mContactID = getIntent().getIntExtra("contactID", 0);
        setupSupportForApp();
        initViews();
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

    private void initViews() {
        //set default date
        Calendar calendar = Calendar.getInstance();
        String currentDate = Utils.convertDateToString(calendar.getTime(), "dd/MM/yyyy");
        String currentTime = Utils.convertDateToString(calendar.getTime(), "HH:mm");
        String currentDateSave = Utils.convertDateToString(calendar.getTime(), "yyyy-MM-dd HH:mm");

        txtStartDate.setText(currentDate);
        txtStartDate.setTag(currentDateSave);
        txtStartTime.setText(currentTime);
        txtEndDate.setText(currentDate);
        txtEndDate.setTag(currentDateSave);
        txtEndTime.setText(currentTime);

        txtType.setText(ProjectApplication.getInstance().getEventStringFromType(mTypeInt));

        switchAllday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    Animation out = AnimationUtils.loadAnimation(CreateEventActivity.this, R.anim.fade_out);
                    layoutEndDate.startAnimation(out);
                    layoutEndDate.setVisibility(View.GONE);
                } else {
                    Animation in = AnimationUtils.loadAnimation(CreateEventActivity.this, R.anim.fade_in);
                    layoutEndDate.startAnimation(in);
                    layoutEndDate.setVisibility(View.VISIBLE);
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
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.txt_first_meet:{
                mTypeInt =1;
                txtType.setText(ProjectApplication.getInstance().getEventStringFromType(1));
                alertDialog.dismiss();
                break;
            }
            case R.id.txt_advisory:{
                mTypeInt =2;
                txtType.setText(ProjectApplication.getInstance().getEventStringFromType(2));
                alertDialog.dismiss();
                break;
            }
            case R.id.txt_sign:{
                mTypeInt =3;
                txtType.setText(ProjectApplication.getInstance().getEventStringFromType(3));
                alertDialog.dismiss();
                break;
            }
            case R.id.txt_different:{
                mTypeInt =4;
                txtType.setText(ProjectApplication.getInstance().getEventStringFromType(4));
                alertDialog.dismiss();
                break;
            }
            case R.id.btn_cancel:{
                alertDialog.dismiss();
                break;
            }
        }
    }

    private void showDialogDateTimePicker(final String type) {
        final View dialogView = View.inflate(this, R.layout.date_time_picker,
                null);
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();

        dialogView.findViewById(R.id.btn_date_time_set).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        DatePicker datePicker = (DatePicker) dialogView
                                .findViewById(R.id.date_picker);
                        TimePicker timePicker = (TimePicker) dialogView.findViewById(R.id.time_picker);

                        int day = datePicker.getDayOfMonth();
                        int month = datePicker.getMonth();
                        int year = datePicker.getYear();
                        int hour;
                        if (Build.VERSION.SDK_INT >= 23)
                            hour = timePicker.getHour();
                        else
                            hour = timePicker.getCurrentHour();

                        int minute;
                        if (Build.VERSION.SDK_INT >= 23)
                            minute = timePicker.getMinute();
                        else
                            minute = timePicker.getCurrentMinute();

                        Calendar calendar = Calendar.getInstance();
                        calendar.set(year, month, day, hour, minute);

                        String selectDate = Utils.convertDateToString(calendar.getTime(), "dd/MM/yyyy");
                        String selectTime = Utils.convertDateToString(calendar.getTime(), "HH:mm");
                        String selectDateSave = Utils.convertDateToString(calendar.getTime(), "yyyy-MM-dd HH:mm:ss");

                        if (type.equals("start")) {
                            if (calendar.getTime()
                                    .before(Utils.convertStringToDate(txtEndDate.getTag().toString(), "dd/MM/yyyy HH:mm"))) {
                                txtStartDate.setText(selectDate);
                                txtStartTime.setText(selectTime);
                                txtStartDate.setTag(selectDateSave);
                            } else {
                                showMessage("Thông báo", "Thời gian bắt đầu phải nhỏ hơn thời gian kết thúc!", SweetAlertDialog.WARNING_TYPE);
                            }
                        } else {
                            //check end date is larger than start date
                            if (Utils.convertStringToDate(txtStartDate.getTag().toString(), "dd/MM/yyyy HH:mm")
                                    .before(calendar.getTime())) {
                                txtEndDate.setText(selectDate);
                                txtEndTime.setText(selectTime);
                                txtEndDate.setTag(selectDateSave);
                            } else
                                showMessage("Thông báo", "Thời gian kết thúc phải lớn hơn thời gian bắt đầu!", SweetAlertDialog.WARNING_TYPE);

                        }
                        alertDialog.dismiss();

                    }
                });

        //autoscroll when chose date
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePicker datePicker = (DatePicker) dialogView.findViewById(R.id.date_picker);
        datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
            public void onDateChanged(DatePicker view, int year, int month,
                                      int day) {
                NestedScrollView scroll = (NestedScrollView) dialogView.findViewById(R.id.scroll);
                TimePicker timePicker = (TimePicker) dialogView.findViewById(R.id.time_picker);
                //ObjectAnimator.ofInt(scroll, "scrollY",  timePicker.getBottom()).setDuration(1000).start();
                Utils.smoothScrollViewToPosition(getApplicationContext(), scroll, timePicker.getBottom());
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

    @OnClick({R.id.layout_btn_back, R.id.txt_start_date, R.id.txt_end_date, R.id.layout_create,
    R.id.txt_type})
    public void onClickView(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.layout_btn_back: {
                onBackPressed();
                break;
            }
            case R.id.txt_start_date: {
                showDialogDateTimePicker("start");
                break;
            }
            case R.id.txt_end_date: {
                showDialogDateTimePicker("end");
                break;
            }
            case R.id.layout_create: {
                if (checkValidate()) {
                    //thêm sự kiện
                    mActionListener.createEvent(mContactID, mTypeInt, txtContactName.getText().toString(),
                            edtLocation.getText().toString(), (String) txtStartDate.getTag(), (String) txtEndDate.getTag(),
                            edtNote.getText().toString(), switchAllday.isChecked(), 30, switchBoss.isChecked());
                }
            }
            case R.id.txt_type:{
                showMenuChooseEvent();
            }
        }
    }
}
