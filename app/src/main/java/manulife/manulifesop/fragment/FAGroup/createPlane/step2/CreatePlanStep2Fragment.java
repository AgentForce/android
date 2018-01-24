package manulife.manulifesop.fragment.FAGroup.createPlane.step2;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import butterknife.BindView;
import manulife.manulifesop.R;
import manulife.manulifesop.activity.FAGroup.createPlan.CreatePlanActivity;
import manulife.manulifesop.base.BaseFragment;


/**
 * Created by Chick on 10/27/2017.
 */

public class CreatePlanStep2Fragment extends BaseFragment<CreatePlanActivity,CreatePlanStep2Present> implements CreatePlanStep2Contract.View {


    public static CreatePlanStep2Fragment newInstance() {
        Bundle args = new Bundle();
        CreatePlanStep2Fragment fragment = new CreatePlanStep2Fragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int contentViewLayout() {
        return R.layout.fragment_create_plan_step2;
    }

    @Override
    public void initializeLayout(View view) {
        mActionListener = new CreatePlanStep2Present(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
}
