package manulife.manulifesop.fragment.ManagerGroup.UMCreateCampaign.step8.screen.screen1;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import butterknife.OnClick;
import manulife.manulifesop.R;
import manulife.manulifesop.activity.ManagerGroup.UMCreatePlan.UMCreatePlanActivity;
import manulife.manulifesop.base.BaseFragment;
import manulife.manulifesop.fragment.ManagerGroup.UMCreateCampaign.step8.UMCreatePlanStep8Contract;
import manulife.manulifesop.fragment.ManagerGroup.UMCreateCampaign.step8.UMCreatePlanStep8Present;


/**
 * Created by Chick on 10/27/2017.
 */

public class UMCreatePlanStep8Screen1Fragment extends BaseFragment<UMCreatePlanActivity, UMCreatePlanStep8Screen1Present> implements UMCreatePlanStep8Screen1Contract.View {


    public static UMCreatePlanStep8Screen1Fragment newInstance() {
        Bundle args = new Bundle();
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

    }
}
