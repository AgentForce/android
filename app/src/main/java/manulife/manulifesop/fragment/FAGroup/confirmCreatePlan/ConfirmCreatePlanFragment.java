package manulife.manulifesop.fragment.FAGroup.confirmCreatePlan;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import manulife.manulifesop.R;
import manulife.manulifesop.activity.FAGroup.createPlan.CreatePlanActivity;
import manulife.manulifesop.activity.FAGroup.main.MainFAActivity;
import manulife.manulifesop.adapter.CustomViewPagerAdapter;
import manulife.manulifesop.base.BaseFragment;
import manulife.manulifesop.element.CustomViewPager;
import manulife.manulifesop.fragment.FAGroup.createPlane.step1.CreatePlanStep1Contract;
import manulife.manulifesop.fragment.FAGroup.createPlane.step1.CreatePlanStep1Present;
import manulife.manulifesop.fragment.FAGroup.createPlane.step2.CreatePlanStep2Fragment;

/**
 * Created by Chick on 10/27/2017.
 */

public class ConfirmCreatePlanFragment extends BaseFragment<MainFAActivity,ConfirmCreatePlanPresent> implements ConfirmCreatePlanContract.View {

    @BindView(R.id.btn_start)
    Button btnStart;

    public static ConfirmCreatePlanFragment newInstance(String title) {
        Bundle args = new Bundle();
        args.putString("title",title);
        ConfirmCreatePlanFragment fragment = new ConfirmCreatePlanFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int contentViewLayout() {
        return R.layout.fragment_show_create_plane;
    }

    @Override
    public void initializeLayout(View view) {
        mActionListener = new ConfirmCreatePlanPresent(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mActivity.showHideActionbar(true);
        mActivity.updateActionbarTitle(getArguments().getString("title","Trang chủ"));
    }

    @OnClick(R.id.btn_start)
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
    }

}
