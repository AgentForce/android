package manulife.manulifesop.fragment.ManagerGroup.UMCreateCampaign.step8.screen.screen1;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.OnClick;
import manulife.manulifesop.R;
import manulife.manulifesop.activity.ManagerGroup.UMCreatePlan.UMCreatePlanActivity;
import manulife.manulifesop.api.ObjectResponse.ForcastIncomeUM;
import manulife.manulifesop.base.BaseFragment;
import manulife.manulifesop.fragment.ManagerGroup.UMCreateCampaign.step8.UMCreatePlanStep8Contract;
import manulife.manulifesop.fragment.ManagerGroup.UMCreateCampaign.step8.UMCreatePlanStep8Present;
import manulife.manulifesop.util.Utils;


/**
 * Created by Chick on 10/27/2017.
 */

public class UMCreatePlanStep8Screen1Fragment extends BaseFragment<UMCreatePlanActivity, UMCreatePlanStep8Screen1Present> implements UMCreatePlanStep8Screen1Contract.View {

    @BindView(R.id.txt_profit)
    TextView txtProfit;
    @BindView(R.id.txt_bonus_new_agent)
    TextView txtBonusNewAgent;
    @BindView(R.id.txt_bonus_month)
    TextView txtBonusMonth;
    @BindView(R.id.txt_bonus_quarter)
    TextView txtBonusQuater;
    @BindView(R.id.txt_bonus_FC)
    TextView txtBonusFC;
    @BindView(R.id.txt_bonus_mdrt)
    TextView txtBonusMdrt;
    @BindView(R.id.txt_bonus_mdrt_year)
    TextView txtBonusMdrtYear;

    @BindView(R.id.total_income_personal)
    TextView txtTotalIncome;

    private ForcastIncomeUM mData;

    public static UMCreatePlanStep8Screen1Fragment newInstance(ForcastIncomeUM data) {
        Bundle args = new Bundle();
        args.putSerializable("data",data);
        UMCreatePlanStep8Screen1Fragment fragment = new UMCreatePlanStep8Screen1Fragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int contentViewLayout() {
        return R.layout.fragment_create_plan_um_step8_screen1;
    }

    @Override
    public void initializeLayout(View view) {
        mActionListener = new UMCreatePlanStep8Screen1Present(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mData = (ForcastIncomeUM)getArguments().getSerializable("data");
        initViews();
    }

    @Override
    public void initViews() {
        txtProfit.setText(Utils.longTypeTextFormat((long)(mData.data.personal.comm * 1000000)));
        txtBonusNewAgent.setText(Utils.longTypeTextFormat((long)(mData.data.personal.newAgentBonus * 1000000)));
        txtBonusMonth.setText(Utils.longTypeTextFormat((long)(mData.data.personal.monthlyBonus * 1000000)));
        txtBonusQuater.setText(Utils.longTypeTextFormat((long)(mData.data.personal.quarterBonus * 1000000)));
        txtBonusFC.setText(Utils.longTypeTextFormat((long)(mData.data.personal.fcTetBonus * 1000000)));
        txtBonusMdrt.setText(Utils.longTypeTextFormat((long)(mData.data.personal.monthlyMDRTBonus * 1000000)));
        txtBonusMdrtYear.setText(Utils.longTypeTextFormat((long)(mData.data.personal.yearlyMDRTBonus * 1000000)));

        double total = mData.data.personal.comm + mData.data.personal.newAgentBonus+
                mData.data.personal.monthlyBonus + mData.data.personal.quarterBonus +
                mData.data.personal.fcTetBonus + mData.data.personal.yearlyMDRTBonus +
                mData.data.personal.monthlyMDRTBonus;
        txtTotalIncome.setText(Utils.longTypeTextFormat((long)(total * 1000000)));
    }
}
