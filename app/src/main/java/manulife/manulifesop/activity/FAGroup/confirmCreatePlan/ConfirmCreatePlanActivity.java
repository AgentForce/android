package manulife.manulifesop.activity.FAGroup.confirmCreatePlan;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import manulife.manulifesop.R;
import manulife.manulifesop.activity.FAGroup.createPlan.CreatePlanActivity;
import manulife.manulifesop.activity.main.MainFAActivity;
import manulife.manulifesop.api.ObjectResponse.UserProfile;
import manulife.manulifesop.base.BaseActivity;


public class ConfirmCreatePlanActivity extends BaseActivity<ConfirmCreatePlanPresenter> implements ConfirmCreatePlanContract.View {

    @BindView(R.id.txt_actionbar_title)
    TextView txtActionbarTitle;

    @BindView(R.id.btn_start)
    Button btnStart;
    @BindView(R.id.txt_go_main)
    TextView txtGoMain;

    @BindView(R.id.txt_user_name)
    TextView txtUserName;
    @BindView(R.id.txt_agent_number)
    TextView txtAgentNumber;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.txt_title_info)
    TextView txtTitleInfo;
    @BindView(R.id.txt_location)
    TextView txtLocation;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_create_campaing);
        mActionListener = new ConfirmCreatePlanPresenter(this,this);
        txtActionbarTitle.setText("Chào mừng bạn");
        mActionListener.getUserProfile(getIntent().getStringExtra("userName"));
    }

    @Override
    public void showData(UserProfile data) {
        txtUserName.setText(data.data.fullName);
        txtAgentNumber.setText(String.valueOf(data.data.username));
        txtTitle.setText(data.data.codeLevel);
        txtTitleInfo.setText(data.data.badge);
        txtLocation.setText("Vùng " + data.data.zone);
    }

    @OnClick({R.id.btn_start, R.id.txt_go_main})
    public void onClick(View view)
    {
        int id = view.getId();
        switch (id)
        {
            case R.id.btn_start:{
                Bundle data = new Bundle();
                data.putString("name",txtUserName.getText().toString());
                goNextScreen(CreatePlanActivity.class,data);
                break;
            }
            case R.id.txt_go_main:{
                goNextScreen(MainFAActivity.class);
                break;
            }
        }
    }
}
