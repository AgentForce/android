package manulife.manulifesop.fragment.ManagerGroup.UMCreateCampaign.step2;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.OnClick;
import manulife.manulifesop.R;
import manulife.manulifesop.activity.ManagerGroup.UMCreatePlan.UMCreatePlanActivity;
import manulife.manulifesop.api.ObjectResponse.UMForcastRecruit;
import manulife.manulifesop.base.BaseFragment;


/**
 * Created by Chick on 10/27/2017.
 */

public class UMCreatePlanStep2Fragment extends BaseFragment<UMCreatePlanActivity, UMCreatePlanStep2Present> implements UMCreatePlanStep2Contract.View {

    @BindView(R.id.txt_start_date)
    TextView txtStartDate;
    @BindView(R.id.txt_end_date)
    TextView txtEndDate;
    @BindView(R.id.txt_planed_agent)
    TextView txtPlanedAgent;
    @BindView(R.id.txt_planed_manager)
    TextView txtPlanedManager;
    @BindView(R.id.txt_not_planed_agent)
    TextView txtNotPlanedAgent;
    @BindView(R.id.txt_not_planed_manager)
    TextView txtNotPlanedManager;


    @BindView(R.id.txt_new_recruitment)
    TextView txtNewRecruitment;
    @BindView(R.id.txt_quit_job)
    TextView txtQuitJob;
    @BindView(R.id.txt_increase_agent)
    TextView txtIncreaseAgent;
    @BindView(R.id.txt_increase_contract)
    TextView txtIncreaseContract;

    @BindView(R.id.rb_month_year)
    RadioGroup rbMonthYear;

    UMForcastRecruit mData;

    public static UMCreatePlanStep2Fragment newInstance() {
        Bundle args = new Bundle();
        UMCreatePlanStep2Fragment fragment = new UMCreatePlanStep2Fragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int contentViewLayout() {
        return R.layout.fragment_create_plan_um_step2;
    }

    @Override
    public void initializeLayout(View view) {
        mActionListener = new UMCreatePlanStep2Present(this,getContext());
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
    }

    @Override
    public void getData() {
        mActionListener.getUmForcastRecruit();
    }

    @Override
    public void showData(UMForcastRecruit data) {
        mData = data;

        txtStartDate.setText(mActivity.getStartDate());
        txtEndDate.setText(mActivity.getEndDate());

        ((RadioButton)rbMonthYear.getChildAt(0)).setChecked(true);
        txtPlanedAgent.setText(data.data.goalSetup.fa + " Đại lý");
        txtPlanedManager.setText(data.data.goalSetup.um + " Quản lý");
        txtNotPlanedAgent.setText(data.data.noGoalSetup.fa + " Đại lý");
        txtNotPlanedManager.setText(data.data.noGoalSetup.um + " Quản lý");

        txtNewRecruitment.setText(String.valueOf(data.data.quantityNewAgent));
        txtQuitJob.setText(String.valueOf(data.data.quantityAgentTer));
        txtIncreaseAgent.setText(data.data.quantityAgentGrow.current + " ĐL + "
        + data.data.quantityAgentGrow.future + " ĐL/Tháng");
        txtIncreaseContract.setText(data.data.quantityContractGrow + " ĐL/Tháng");

    }

    private void initViews(){
        rbMonthYear.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                updateData(mData,mActivity.getMonthNumber());
            }
        });
    }

    @Override
    public void updateData(UMForcastRecruit data, int month) {
        if (rbMonthYear.getCheckedRadioButtonId() == R.id.rbn_campaign){
            txtPlanedAgent.setText((data.data.goalSetup.fa * month) + " Đại lý");
            txtPlanedManager.setText((data.data.goalSetup.um * month) + " Quản lý");
            txtNotPlanedAgent.setText((data.data.noGoalSetup.fa*month) + " Đại lý");
            txtNotPlanedManager.setText((data.data.noGoalSetup.um*month) + " Quản lý");

            txtNewRecruitment.setText(String.valueOf(data.data.quantityNewAgent*month));
            txtQuitJob.setText(String.valueOf(data.data.quantityAgentTer*month));
            txtIncreaseAgent.setText((data.data.quantityAgentGrow.current*month) + " ĐL + "
                    + (data.data.quantityAgentGrow.future*month) + " ĐL/Tháng");
            txtIncreaseContract.setText((data.data.quantityContractGrow*month) + " ĐL/Tháng");
        }else{
            txtPlanedAgent.setText(data.data.goalSetup.fa + " Đại lý");
            txtPlanedManager.setText(data.data.goalSetup.um + " Quản lý");
            txtNotPlanedAgent.setText(data.data.noGoalSetup.fa + " Đại lý");
            txtNotPlanedManager.setText(data.data.noGoalSetup.um + " Quản lý");

            txtNewRecruitment.setText(String.valueOf(data.data.quantityNewAgent));
            txtQuitJob.setText(String.valueOf(data.data.quantityAgentTer));
            txtIncreaseAgent.setText(data.data.quantityAgentGrow.current + " ĐL + "
                    + data.data.quantityAgentGrow.future + " ĐL/Tháng");
            txtIncreaseContract.setText(data.data.quantityContractGrow + " ĐL/Tháng");
        }
    }

    @OnClick(R.id.btn_next)
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btn_next: {
                mActivity.setDataStep2(mData);
                mActivity.showNextFragment();
                break;
            }
        }
    }
}
