package manulife.manulifesop.fragment.FAGroup.personal;

import android.os.Bundle;
import android.view.View;

import manulife.manulifesop.R;
import manulife.manulifesop.activity.FAGroup.main.MainFAActivity;
import manulife.manulifesop.base.BaseFragment;

/**
 * Created by Chick on 10/27/2017.
 */

public class FAPersonalFragment extends BaseFragment<MainFAActivity, FAPersonalPresent> implements FAPersonalContract.View {


    public static FAPersonalFragment newInstance() {
        Bundle args = new Bundle();
        FAPersonalFragment fragment = new FAPersonalFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int contentViewLayout() {
        return R.layout.fragment_personal;
    }

    @Override
    public void initializeLayout(View view) {
        mActionListener = new FAPersonalPresent(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mActivity.showHideActionbar(false);
    }

    /*@OnClick(R.id.btn_start)
    public void onClick(View view)
    {
        int id = view.getId();
        switch (id)
        {
            case R.id.btn_start:
            {
                mActivity.goNextScreen(CreatePlanActivity.class);
                break;
            }
        }
    }*/

}
