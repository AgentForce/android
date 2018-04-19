package manulife.manulifesop.fragment.ManagerGroup.UMCreateCampaign.step7;

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

public class UMCreatePlanStep7Fragment extends BaseFragment<UMCreatePlanActivity, UMCreatePlanStep7Present> implements UMCreatePlanStep7Contract.View {

    @BindView(R.id.txt_contract_per_month)
    TextView txtContactPerMonth;
    @BindView(R.id.txt_FYC_per_contract)
    TextView txtFYCPerContact;
    @BindView(R.id.txt_FYC)
    TextView txtFYC;
    @BindView(R.id.txt_RYP)
    TextView txtRYP;




    public static UMCreatePlanStep7Fragment newInstance() {
        Bundle args = new Bundle();
        UMCreatePlanStep7Fragment fragment = new UMCreatePlanStep7Fragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int contentViewLayout() {
        return R.layout.fragment_create_plan_um_step7;
    }

    @Override
    public void initializeLayout(View view) {
        mActionListener = new UMCreatePlanStep7Present(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }


    @OnClick({R.id.btn_next,R.id.layout_add_step7_row1,R.id.layout_sub_step7_row1,
            R.id.layout_add_step7_row2,R.id.layout_sub_step7_row2,
            R.id.layout_add_step7_row4,R.id.layout_sub_step7_row4})
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btn_next: {
                mActivity.setDataStep7(Integer.valueOf(txtContactPerMonth.getText().toString()),
                        Integer.valueOf(txtFYCPerContact.getText().toString()),
                        Integer.valueOf(txtFYC.getText().toString()),
                        Integer.valueOf(txtRYP.getText().toString()));
                mActivity.showNextFragment();
                break;
            }
            case R.id.layout_add_step7_row1: {
                int newValue = Integer.valueOf(txtContactPerMonth.getText().toString()) + 1;
                txtContactPerMonth.setText(String.valueOf(newValue));
                break;
            }
            case R.id.layout_sub_step7_row1: {
                int newValue = Integer.valueOf(txtContactPerMonth.getText().toString()) - 1;
                if (newValue < 0) newValue = 0;
                txtContactPerMonth.setText(String.valueOf(newValue));
                break;
            }
            case R.id.layout_add_step7_row2: {
                int newValue = Integer.valueOf(txtFYCPerContact.getText().toString()) + 1;
                txtFYCPerContact.setText(String.valueOf(newValue));
                break;
            }
            case R.id.layout_sub_step7_row2: {
                int newValue = Integer.valueOf(txtFYCPerContact.getText().toString()) - 1;
                if (newValue < 0) newValue = 0;
                txtFYCPerContact.setText(String.valueOf(newValue));
                break;
            }
            case R.id.layout_add_step7_row4: {
                int newValue = Integer.valueOf(txtFYC.getText().toString()) + 1;
                txtFYC.setText(String.valueOf(newValue));
                break;
            }
            case R.id.layout_sub_step7_row4: {
                int newValue = Integer.valueOf(txtFYC.getText().toString()) - 1;
                if (newValue < 0) newValue = 0;
                txtFYC.setText(String.valueOf(newValue));
                break;
            }
            case R.id.layout_add_step7_row5: {
                int newValue = Integer.valueOf(txtRYP.getText().toString()) + 1;
                txtRYP.setText(String.valueOf(newValue));
                break;
            }
            case R.id.layout_sub_step7_row5: {
                int newValue = Integer.valueOf(txtRYP.getText().toString()) - 1;
                if (newValue < 0) newValue = 0;
                txtRYP.setText(String.valueOf(newValue));
                break;
            }
        }
    }
}
