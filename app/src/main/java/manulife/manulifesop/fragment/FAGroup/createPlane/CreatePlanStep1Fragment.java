package manulife.manulifesop.fragment.FAGroup.createPlane;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.OnClick;

import manulife.manulifesop.R;
import manulife.manulifesop.activity.FAGroup.createPlan.CreatePlanActivity;
import manulife.manulifesop.base.BaseFragment;

/**
 * Created by Chick on 10/27/2017.
 */

public class CreatePlanStep1Fragment extends BaseFragment<CreatePlanActivity,CreatePlanStep1Present> implements CreatePlanStep1Contract.View {


    public static CreatePlanStep1Fragment newInstance() {
        Bundle args = new Bundle();
        CreatePlanStep1Fragment fragment = new CreatePlanStep1Fragment();
        fragment.setArguments(args);
        return fragment;
    }

    /*@OnClick(R.id.btn_loading)
    public void onClick(View view)
    {
        int id = view.getId();
        switch (id)
        {
            case R.id.btn_loading:
            {
                //mActivity.showLoading("fragment job 1 call method in activity");
                //mActivity.finishLoading("load finish",true);
                //mActivity.viewFinishLoading();
                mActivity.goNextScreen(MainActivity.class);
                break;
            }
        }
    }*/

    @Override
    public int contentViewLayout() {
        return R.layout.fragment_create_plan_step1;
    }

    @Override
    public void initializeLayout(View view) {
        mActionListener = new CreatePlanStep1Present(this);
    }
}
