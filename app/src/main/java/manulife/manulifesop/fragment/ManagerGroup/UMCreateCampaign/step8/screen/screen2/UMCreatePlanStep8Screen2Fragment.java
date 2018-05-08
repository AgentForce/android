package manulife.manulifesop.fragment.ManagerGroup.UMCreateCampaign.step8.screen.screen2;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import manulife.manulifesop.R;
import manulife.manulifesop.activity.ManagerGroup.UMCreatePlan.UMCreatePlanActivity;
import manulife.manulifesop.api.ObjectResponse.ForcastIncomeUM;
import manulife.manulifesop.base.BaseFragment;
import manulife.manulifesop.util.Utils;


/**
 * Created by Chick on 10/27/2017.
 */

public class UMCreatePlanStep8Screen2Fragment extends BaseFragment<UMCreatePlanActivity, UMCreatePlanStep8Screen2Present> implements UMCreatePlanStep8Screen2Contract.View {

    @BindView(R.id.txt_recruitment)
    TextView txtRecruitment;
    @BindView(R.id.txt_bonus_new_um)
    TextView txtBonusNewUM;
    @BindView(R.id.txt_bonus_train_um)
    TextView txtBonusTrainUM;
    @BindView(R.id.txt_train_agent)
    TextView txtBonusTrainAgent;
    @BindView(R.id.txt_manager)
    TextView txtBonusManager;
    @BindView(R.id.txt_manager_capacity)
    TextView txtBonusManagerCapacity;
    @BindView(R.id.txt_quality_agent)
    TextView txtBonusQualityAgent;

    @BindView(R.id.title_num_campaign)
    TextView txtNumCampaign;
    @BindView(R.id.total_income_manager)
    TextView txtTotalIncome;

    private ForcastIncomeUM mData;

    public static UMCreatePlanStep8Screen2Fragment newInstance(ForcastIncomeUM data) {
        Bundle args = new Bundle();
        args.putSerializable("data", data);
        UMCreatePlanStep8Screen2Fragment fragment = new UMCreatePlanStep8Screen2Fragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int contentViewLayout() {
        return R.layout.fragment_create_plan_um_step8_screen2;
    }

    @Override
    public void initializeLayout(View view) {
        mActionListener = new UMCreatePlanStep8Screen2Present(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mData = (ForcastIncomeUM) getArguments().getSerializable("data");
        if (mData.statusCode == 1)
            initViews();
    }

    @Override
    public void initViews() {
        txtRecruitment.setText(Utils.longTypeTextFormat((long) (mData.data.manager.recruiterBonus * 1000000)));
        txtBonusNewUM.setText(Utils.longTypeTextFormat((long) (mData.data.manager.newUMBonus * 1000000)));
        txtBonusTrainUM.setText(Utils.longTypeTextFormat((long) (mData.data.manager.coachingUMBonus * 1000000)));
        txtBonusTrainAgent.setText(Utils.longTypeTextFormat((long) (mData.data.manager.nABMangerBonus * 1000000)));
        txtBonusManager.setText(Utils.longTypeTextFormat((long) (mData.data.manager.monthlyOverride * 1000000)));
        txtBonusManagerCapacity.setText(Utils.longTypeTextFormat((long) (mData.data.manager.individualManagerBonus * 1000000)));
        txtBonusQualityAgent.setText(Utils.longTypeTextFormat((long) (mData.data.manager.newAgentQualitityBonus * 1000000)));

        double totol = mData.data.manager.newUMBonus + mData.data.manager.monthlyOverride +
                mData.data.manager.recruiterBonus + mData.data.manager.nABMangerBonus +
                mData.data.manager.individualManagerBonus + mData.data.manager.coachingUMBonus +
                mData.data.manager.newAgentQualitityBonus;
        txtTotalIncome.setText(Utils.longTypeTextFormat((long) (totol * 1000000)));
        txtNumCampaign.setText("(" + mActivity.getMonthNumber() + " tháng)");
    }
}
