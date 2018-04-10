package manulife.manulifesop.fragment.ManagerGroup.UMCreateCampaign.step3;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import manulife.manulifesop.R;
import manulife.manulifesop.activity.ManagerGroup.UMCreatePlan.UMCreatePlanActivity;
import manulife.manulifesop.base.BaseFragment;
import manulife.manulifesop.fragment.ManagerGroup.UMCreateCampaign.step2.UMCreatePlanStep2Contract;
import manulife.manulifesop.fragment.ManagerGroup.UMCreateCampaign.step2.UMCreatePlanStep2Present;


/**
 * Created by Chick on 10/27/2017.
 */

public class UMCreatePlanStep3Fragment extends BaseFragment<UMCreatePlanActivity, UMCreatePlanStep3Present> implements UMCreatePlanStep3Contract.View {


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
