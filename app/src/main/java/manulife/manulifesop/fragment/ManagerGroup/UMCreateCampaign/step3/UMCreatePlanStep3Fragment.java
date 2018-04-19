package manulife.manulifesop.fragment.ManagerGroup.UMCreateCampaign.step3;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import manulife.manulifesop.R;
import manulife.manulifesop.activity.ManagerGroup.UMCreatePlan.UMCreatePlanActivity;
import manulife.manulifesop.base.BaseFragment;


/**
 * Created by Chick on 10/27/2017.
 */

public class UMCreatePlanStep3Fragment extends BaseFragment<UMCreatePlanActivity, UMCreatePlanStep3Present> implements UMCreatePlanStep3Contract.View {

    @BindView(R.id.txt_new_agent_monthly)
    TextView txtNewAgent;
    @BindView(R.id.txt_contract_monthly)
    TextView txtNewContract;
    @BindView(R.id.txt_FYC)
    TextView txtFYC;

    public static UMCreatePlanStep3Fragment newInstance() {
        Bundle args = new Bundle();
        UMCreatePlanStep3Fragment fragment = new UMCreatePlanStep3Fragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int contentViewLayout() {
        return R.layout.fragment_create_plan_um_step3;
    }

    @Override
    public void initializeLayout(View view) {
        mActionListener = new UMCreatePlanStep3Present(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }


    @OnClick({R.id.btn_next, R.id.layout_add_new_agent_monthly, R.id.layout_sub_new_agent_monthly,
            R.id.layout_add_contract_monthly, R.id.layout_sub_contract_monthly,
            R.id.layout_add_FYC, R.id.layout_sub_FYC})
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btn_next: {
                mActivity.setDataStep3(Integer.valueOf(txtNewAgent.getText().toString()),
                        Integer.valueOf(txtNewContract.getText().toString()),
                        Integer.valueOf(txtFYC.getText().toString()));
                mActivity.showNextFragment();
                break;
            }
            case R.id.layout_add_new_agent_monthly: {
                int newValue = Integer.valueOf(txtNewAgent.getText().toString()) + 1;
                txtNewAgent.setText(String.valueOf(newValue));
                break;
            }
            case R.id.layout_sub_new_agent_monthly: {
                int newValue = Integer.valueOf(txtNewAgent.getText().toString()) - 1;
                if (newValue < 0) newValue = 0;
                txtNewAgent.setText(String.valueOf(newValue));
                break;
            }
            case R.id.layout_add_contract_monthly: {
                int newValue = Integer.valueOf(txtNewContract.getText().toString()) + 1;
                txtNewContract.setText(String.valueOf(newValue));
                break;
            }
            case R.id.layout_sub_contract_monthly: {
                int newValue = Integer.valueOf(txtNewContract.getText().toString()) - 1;
                if (newValue < 0) newValue = 0;
                txtNewContract.setText(String.valueOf(newValue));
                break;
            }
            case R.id.layout_add_FYC: {
                int newValue = Integer.valueOf(txtFYC.getText().toString()) + 1;
                txtFYC.setText(String.valueOf(newValue));
                break;
            }
            case R.id.layout_sub_FYC: {
                int newValue = Integer.valueOf(txtFYC.getText().toString()) - 1;
                if (newValue < 0) newValue = 0;
                txtFYC.setText(String.valueOf(newValue));
                break;
            }
        }
    }
}
