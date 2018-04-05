package manulife.manulifesop.fragment.ManagerGroup.SMCreateCampaign.step3;

import android.os.Bundle;
import android.view.View;

import butterknife.OnClick;
import manulife.manulifesop.R;
import manulife.manulifesop.activity.ManagerGroup.SMCreatePlan.SMCreatePlanActivity;
import manulife.manulifesop.base.BaseFragment;


/**
 * Created by Chick on 10/27/2017.
 */

public class SMCreatePlanStep3Fragment extends BaseFragment<SMCreatePlanActivity, SMCreatePlanStep3Present> implements SMCreatePlanStep3Contract.View {


    public static SMCreatePlanStep3Fragment newInstance() {
        Bundle args = new Bundle();
        SMCreatePlanStep3Fragment fragment = new SMCreatePlanStep3Fragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int contentViewLayout() {
        return R.layout.fragment_create_plan_sm_step3;
    }

    @Override
    public void initializeLayout(View view) {
        mActionListener = new SMCreatePlanStep3Present(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
    }

    private void initViews(){

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
