package manulife.manulifesop.activity.FAGroup.personal.personalInfo;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import manulife.manulifesop.R;
import manulife.manulifesop.activity.FAGroup.confirmCreatePlan.ConfirmCreatePlanContract;
import manulife.manulifesop.activity.FAGroup.confirmCreatePlan.ConfirmCreatePlanPresenter;
import manulife.manulifesop.activity.FAGroup.createPlan.CreatePlanActivity;
import manulife.manulifesop.activity.ManagerGroup.SMCreatePlan.SMCreatePlanActivity;
import manulife.manulifesop.activity.ManagerGroup.UMCreatePlan.UMCreatePlanActivity;
import manulife.manulifesop.activity.main.MainFAActivity;
import manulife.manulifesop.api.ObjectResponse.UserProfile;
import manulife.manulifesop.base.BaseActivity;
import manulife.manulifesop.util.Utils;


public class PersonalInfoActivity extends BaseActivity<PersonalInfoPresenter> implements PersonalInfoContract.View {

    @BindView(R.id.txt_actionbar_title)
    TextView txtActionbarTitle;
    @BindView(R.id.layout_btn_back)
    LinearLayout layoutBackButton;
    @BindView(R.id.status_bar)
    View viewStatusBar;

    @BindView(R.id.txt_user_name)
    TextView txtUserName;
    @BindView(R.id.txt_agent_number)
    TextView txtAgentNumber;
    @BindView(R.id.txt_user_title)
    TextView txtUserTitle;
    @BindView(R.id.txt_user_title_info)
    TextView txtUserTitleInfo;
    @BindView(R.id.txt_location)
    TextView txtLocation;
    @BindView(R.id.txt_phone)
    TextView txtPhone;
    @BindView(R.id.txt_join_date)
    TextView txtJoinDate;
    @BindView(R.id.txt_manager)
    TextView txtManager;
    @BindView(R.id.txt_sm_code)
    TextView txtSmCode;

    private UserProfile mDataProfie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);
        mActionListener = new PersonalInfoPresenter(this, this);
        mDataProfie = (UserProfile) getIntent().getSerializableExtra("data");
        setupSupportForApp();
        loadData();
    }

    private void setupSupportForApp() {
        txtActionbarTitle.setText("Thông tin tài khoản");
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
    public void loadData() {
        txtUserName.setText(mDataProfie.data.fullName);
        txtAgentNumber.setText(mDataProfie.data.username);
        txtUserTitle.setText(mDataProfie.data.codeLevel);
        txtUserTitleInfo.setText(mDataProfie.data.badge);
        txtLocation.setText("Vùng " + mDataProfie.data.zone);
        txtPhone.setText(mDataProfie.data.phone);

        txtJoinDate.setText(Utils.convertStringTimeZoneDateToStringDate(
                mDataProfie.data.expirence,
                "yyyy-MM-dd'T'HH:mm:ss.sss'Z'",
                "dd/MM/yyyy"));
        if (mDataProfie.data.reportToUsername != null && mDataProfie.data.reportToUsername.length() > 0) {
            String[] splitReportTo = mDataProfie.data.reportToUsername.split("-");
            txtManager.setText((splitReportTo.length > 1) ? splitReportTo[0].trim() : mDataProfie.data.reportToUsername);
            txtSmCode.setText((splitReportTo.length > 1) ? splitReportTo[1].trim() : mDataProfie.data.reportToUsername);
        } else {
            txtManager.setText("");
            txtSmCode.setText("");
        }
    }

    @OnClick({R.id.layout_btn_back})
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.layout_btn_back: {
                onBackPressed();
                break;
            }
        }
    }
}
