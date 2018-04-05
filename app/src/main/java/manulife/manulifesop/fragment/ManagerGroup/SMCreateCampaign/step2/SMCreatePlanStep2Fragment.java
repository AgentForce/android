package manulife.manulifesop.fragment.ManagerGroup.SMCreateCampaign.step2;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import manulife.manulifesop.R;
import manulife.manulifesop.activity.ManagerGroup.SMCreatePlan.SMCreatePlanActivity;
import manulife.manulifesop.base.BaseFragment;


/**
 * Created by Chick on 10/27/2017.
 */

public class SMCreatePlanStep2Fragment extends BaseFragment<SMCreatePlanActivity, SMCreatePlanStep2Present> implements SMCreatePlanStep2Contract.View {

    @BindView(R.id.txt_start_date)
    TextView txtStartDate;
    @BindView(R.id.txt_end_date)
    TextView txtEndDate;
    @BindView(R.id.txt_new_recruitment)
    TextView txtNewRecruitment;
    @BindView(R.id.txt_quit_job)
    TextView txtQuitJob;
    @BindView(R.id.txt_increase_agent)
    TextView txtIncreaseAgent;
    @BindView(R.id.txt_increase_contract)
    TextView txtIncreaseContract;
    @BindView(R.id.txt_increase_profit)
    TextView txtIncreaseProfit;


    @BindView(R.id.rb_month_year)
    RadioGroup rbMonthYear;

    private int mContract;
    private int mMonthNum;


    public static SMCreatePlanStep2Fragment newInstance() {
        Bundle args = new Bundle();
        SMCreatePlanStep2Fragment fragment = new SMCreatePlanStep2Fragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int contentViewLayout() {
        return R.layout.fragment_create_plan_sm_step2;
    }

    @Override
    public void initializeLayout(View view) {
        mActionListener = new SMCreatePlanStep2Present(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
    }

    private void initViews(){
        rbMonthYear.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                updateDate(mContract,mMonthNum);
            }
        });
    }

    public void updateDate(int contractNum,int monthNum) {
        this.mContract = contractNum;
        this.mMonthNum = monthNum;
        if (rbMonthYear.getCheckedRadioButtonId() == R.id.rbn_campaign) {
            contractNum = contractNum * monthNum;
        }
    }


    @OnClick(R.id.btn_next)
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btn_next: {
                mActivity.showNextFragment("","");
                break;
            }
        }
    }
}
