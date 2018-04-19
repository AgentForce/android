package manulife.manulifesop.fragment.ManagerGroup.UMCreateCampaign.step5;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import manulife.manulifesop.R;
import manulife.manulifesop.activity.ManagerGroup.UMCreatePlan.UMCreatePlanActivity;
import manulife.manulifesop.base.BaseFragment;
import manulife.manulifesop.fragment.ManagerGroup.UMCreateCampaign.step4.UMCreatePlanStep4Contract;
import manulife.manulifesop.fragment.ManagerGroup.UMCreateCampaign.step4.UMCreatePlanStep4Present;


/**
 * Created by Chick on 10/27/2017.
 */

public class UMCreatePlanStep5Fragment extends BaseFragment<UMCreatePlanActivity, UMCreatePlanStep5Present> implements UMCreatePlanStep5Contract.View {

    @BindView(R.id.txt_new_agent_year)
    TextView txtNewAgent;
    @BindView(R.id.txt_profit_per_agent)
    TextView txtTotalProfit;


    public static UMCreatePlanStep5Fragment newInstance() {
        Bundle args = new Bundle();
        UMCreatePlanStep5Fragment fragment = new UMCreatePlanStep5Fragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int contentViewLayout() {
        return R.layout.fragment_create_plan_um_step5;
    }

    @Override
    public void initializeLayout(View view) {
        mActionListener = new UMCreatePlanStep5Present(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }


    @OnClick({R.id.btn_next, R.id.layout_add_step5_row1, R.id.layout_sub_step5_row1,
            R.id.layout_add_step5_row2, R.id.layout_sub_step5_row2})
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btn_next: {
                mActivity.setDataStep5(Integer.valueOf(txtNewAgent.getText().toString()),
                        Integer.valueOf(txtTotalProfit.getText().toString()));
                mActivity.showNextFragment();
                break;
            }
            case R.id.layout_add_step5_row1: {
                int newValue = Integer.valueOf(txtNewAgent.getText().toString()) + 1;
                txtNewAgent.setText(String.valueOf(newValue));
                break;
            }
            case R.id.layout_sub_step5_row1: {
                int newValue = Integer.valueOf(txtNewAgent.getText().toString()) - 1;
                if (newValue < 0) newValue = 0;
                txtNewAgent.setText(String.valueOf(newValue));
                break;
            }
            case R.id.layout_add_step5_row2: {
                int newValue = Integer.valueOf(txtTotalProfit.getText().toString()) + 1;
                txtTotalProfit.setText(String.valueOf(newValue));
                break;
            }
            case R.id.layout_sub_step5_row2: {
                int newValue = Integer.valueOf(txtTotalProfit.getText().toString()) - 1;
                if (newValue < 0) newValue = 0;
                txtTotalProfit.setText(String.valueOf(newValue));
                break;
            }
        }
    }
}
