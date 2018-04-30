package manulife.manulifesop.activity.FAGroup.clients.related.eventDetail;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import manulife.manulifesop.ProjectApplication;
import manulife.manulifesop.R;
import manulife.manulifesop.activity.FAGroup.clients.related.createEvent.CreateEventActivity;
import manulife.manulifesop.adapter.ObjectData.EventCalendar;
import manulife.manulifesop.adapter.ObjectData.EventData;
import manulife.manulifesop.api.ObjectResponse.ActivityDetail;
import manulife.manulifesop.base.BaseActivity;
import manulife.manulifesop.element.callbackInterface.CallBackConfirmDialog;
import manulife.manulifesop.util.CalendarEvents;
import manulife.manulifesop.util.Contants;
import manulife.manulifesop.util.Utils;


public class EventDetailActivity extends BaseActivity<EventDetailPresenter> implements EventDetailContract.View {

    @BindView(R.id.txt_actionbar_title)
    TextView txtActionbarTitle;
    @BindView(R.id.layout_btn_back)
    LinearLayout layoutBackButton;
    @BindView(R.id.layout_edit)
    RelativeLayout layoutEdit;
    @BindView(R.id.status_bar)
    View viewStatusBar;

    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.txt_type)
    TextView txtType;
    @BindView(R.id.switch_allday)
    SwitchCompat switchAllDay;
    @BindView(R.id.txt_start_date)
    TextView txtStartDay;
    @BindView(R.id.txt_start_time)
    TextView txtStartTime;
    @BindView(R.id.txt_end_time)
    TextView txtEndTime;
    @BindView(R.id.txt_location)
    TextView txtLocation;
    @BindView(R.id.txt_notification_time)
    TextView txtTimeNotification;
    @BindView(R.id.txt_contact_name)
    TextView txtContactName;
    @BindView(R.id.switch_boss)
    SwitchCompat switchBoss;
    @BindView(R.id.txt_note)
    TextView txtNote;

    private int mEventID = 0;
    private ActivityDetail mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);
        mActionListener = new EventDetailPresenter(this, this);
        setupSupportForApp();
        mEventID = getIntent().getIntExtra("eventID", 0);
        mActionListener.getActivityDetail(mEventID);

        switchAllDay.setClickable(false);
        switchBoss.setClickable(false);
    }

    private void setupSupportForApp() {
        //txtActionbarTitle.setText(getResources().getString(R.string.activity_create_plan_title_actionbar));
        txtActionbarTitle.setText("Chi tiết sự kiện");
        layoutBackButton.setVisibility(View.VISIBLE);
        layoutEdit.setVisibility(View.VISIBLE);


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
    public void loadData(ActivityDetail data) {
        this.mData = data;
        txtTitle.setText(data.data.name);
        txtType.setText(ProjectApplication.getInstance().getEventStringFromType(data.data.type));
        switchAllDay.setChecked(data.data.fullDate);

        String startDay = Utils.convertStringTimeZoneDateToStringDate(
                data.data.startDate, "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "dd/MM/yyyy"
        );
        String startTime = Utils.convertStringTimeZoneDateToStringDate(
                data.data.startDate, "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "HH:mm"
        );
        String endTime = Utils.convertStringTimeZoneDateToStringDate(
                data.data.endDate, "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "HH:mm"
        );
        txtStartDay.setText(startDay);
        if (data.data.fullDate) {
            txtStartTime.setText("00:01");
            txtEndTime.setText("23:59");
        } else {
            txtStartTime.setText(startTime);
            txtEndTime.setText(endTime);
        }
        txtLocation.setText(data.data.location);

        txtTimeNotification.setText("Báo trước " + data.data.notification + " phút");
        txtContactName.setText(data.data.manulifeLead.name);
        switchBoss.setChecked(data.data.isSupport);
        txtNote.setText(data.data.description);
    }

    @Override
    public void finishSuccess() {
        setResult(Activity.RESULT_OK);
        finish();
    }

    @OnClick({R.id.layout_delete, R.id.layout_btn_back,R.id.layout_edit})
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.layout_delete: {
                //Toast.makeText(this, "delete", Toast.LENGTH_SHORT).show();
                showConfirm("Xác nhận", "Xóa sự kiện?", "Đồng ý", "Hủy",
                        SweetAlertDialog.WARNING_TYPE, new CallBackConfirmDialog() {
                            @Override
                            public void DiaglogPositive() {
                                mActionListener.deleteEvent(mEventID,txtTitle.getText().toString());
                            }

                            @Override
                            public void DiaglogNegative() {

                            }
                        });
                break;
            }
            case R.id.layout_btn_back: {
                //onBackPressed();
                setResult(RESULT_OK);
                finish();
                break;
            }
            case R.id.layout_edit:{
                Bundle data = new Bundle();
                data.putSerializable("eventID",mData.data.id);
                goNextScreen(CreateEventActivity.class, data, Contants.UPDATE_EVENT);
                break;
            }

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == Contants.UPDATE_EVENT){
            mActionListener.getActivityDetail(mEventID);
        }
    }
}
