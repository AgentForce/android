package manulife.manulifesop.fragment.ManagerGroup.UMCreateCampaign.step4;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import manulife.manulifesop.R;
import manulife.manulifesop.activity.ManagerGroup.UMCreatePlan.UMCreatePlanActivity;
import manulife.manulifesop.base.BaseFragment;
import manulife.manulifesop.fragment.ManagerGroup.UMCreateCampaign.step3.UMCreatePlanStep3Contract;
import manulife.manulifesop.fragment.ManagerGroup.UMCreateCampaign.step3.UMCreatePlanStep3Present;


/**
 * Created by Chick on 10/27/2017.
 */

public class UMCreatePlanStep4Fragment extends BaseFragment<UMCreatePlanActivity, UMCreatePlanStep4Present> implements UMCreatePlanStep4Contract.View {

    @BindView(R.id.txt_num_agent_working)
    TextView txtAgentWorking;
    @BindView(R.id.txt_num_contract_per_agent)
    TextView txtContactPerAgent;
    @BindView(R.id.txt_FYC)
    TextView txtFYC;
    @BindView(R.id.txt_num_agent_year)
    TextView txtAgentYear;


    public static UMCreatePlanStep4Fragment newInstance() {
        Bundle args = new Bundle();
        UMCreatePlanStep4Fragment fragment = new UMCreatePlanStep4Fragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int contentViewLayout() {
        return R.layout.fragment_create_plan_um_step4;
    }

    @Override
    public void initializeLayout(View view) {
        mActionListener = new UMCreatePlanStep4Present(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }


    @OnClick({R.id.btn_next, R.id.layout_add_step4_row1, R.id.layout_sub_step4_row1,
            R.id.layout_add_step4_row2, R.id.layout_sub_step4_row2,
            R.id.layout_add_step4_row3, R.id.layout_sub_step4_row3,
            R.id.layout_add_step4_row4, R.id.layout_sub_step4_row4})
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btn_next: {
                mActivity.setDataStep4(Integer.valueOf(txtAgentWorking.getText().toString()),
                        Integer.valueOf(txtContactPerAgent.getText().toString()),
                        Integer.valueOf(txtFYC.getText().toString()),
                        Integer.valueOf(txtAgentYear.getText().toString()));
                mActivity.showNextFragment();
                break;
            }
            case R.id.layout_add_step4_row1: {
                int newValue = Integer.valueOf(txtAgentWorking.getText().toString()) + 1;
                txtAgentWorking.setText(String.valueOf(newValue));
                break;
            }
            case R.id.layout_sub_step4_row1: {
                int newValue = Integer.valueOf(txtAgentWorking.getText().toString()) - 1;
                if (newValue < 0) newValue = 0;
                txtAgentWorking.setText(String.valueOf(newValue));
                break;
            }
            case R.id.layout_add_step4_row2: {
                int newValue = Integer.valueOf(txtContactPerAgent.getText().toString()) + 1;
                txtContactPerAgent.setText(String.valueOf(newValue));
                break;
            }
            case R.id.layout_sub_step4_row2: {
                int newValue = Integer.valueOf(txtContactPerAgent.getText().toString()) - 1;
                if (newValue < 0) newValue = 0;
                txtContactPerAgent.setText(String.valueOf(newValue));
                break;
            }
            case R.id.layout_add_step4_row3: {
                int newValue = Integer.valueOf(txtFYC.getText().toString()) + 1;
                txtFYC.setText(String.valueOf(newValue));
                break;
            }
            case R.id.layout_sub_step4_row3: {
                int newValue = Integer.valueOf(txtFYC.getText().toString()) - 1;
                if (newValue < 0) newValue = 0;
                txtFYC.setText(String.valueOf(newValue));
                break;
            }
            case R.id.layout_add_step4_row4: {
                int newValue = Integer.valueOf(txtAgentYear.getText().toString()) + 1;
                txtAgentYear.setText(String.valueOf(newValue));
                break;
            }
            case R.id.layout_sub_step4_row4: {
                int newValue = Integer.valueOf(txtAgentYear.getText().toString()) - 1;
                if (newValue < 0) newValue = 0;
                txtAgentYear.setText(String.valueOf(newValue));
                break;
            }
        }
    }
}
