package manulife.manulifesop.fragment.ManagerGroup.UMCreateCampaign.step7;

import android.os.Bundle;
import android.view.View;

import butterknife.OnClick;
import manulife.manulifesop.R;
import manulife.manulifesop.activity.ManagerGroup.UMCreatePlan.UMCreatePlanActivity;
import manulife.manulifesop.base.BaseFragment;


/**
 * Created by Chick on 10/27/2017.
 */

public class UMCreatePlanStep7Fragment extends BaseFragment<UMCreatePlanActivity, UMCreatePlanStep7Present> implements UMCreatePlanStep7Contract.View {


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
