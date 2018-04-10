package manulife.manulifesop.fragment.ManagerGroup.UMCreateCampaign.step5;

import android.os.Bundle;
import android.view.View;

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
