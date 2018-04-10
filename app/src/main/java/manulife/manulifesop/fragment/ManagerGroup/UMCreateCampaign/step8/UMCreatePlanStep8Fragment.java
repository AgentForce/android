package manulife.manulifesop.fragment.ManagerGroup.UMCreateCampaign.step8;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import chick.indicator.CircleIndicatorPager;
import manulife.manulifesop.R;
import manulife.manulifesop.activity.ManagerGroup.UMCreatePlan.UMCreatePlanActivity;
import manulife.manulifesop.adapter.CustomViewPagerAdapter;
import manulife.manulifesop.base.BaseFragment;
import manulife.manulifesop.element.CustomViewPager;
import manulife.manulifesop.fragment.ManagerGroup.UMCreateCampaign.step7.UMCreatePlanStep7Contract;
import manulife.manulifesop.fragment.ManagerGroup.UMCreateCampaign.step7.UMCreatePlanStep7Present;
import manulife.manulifesop.fragment.ManagerGroup.UMCreateCampaign.step8.screen.screen1.UMCreatePlanStep8Screen1Fragment;
import manulife.manulifesop.fragment.first.FirstFragment;


/**
 * Created by Chick on 10/27/2017.
 */

public class UMCreatePlanStep8Fragment extends BaseFragment<UMCreatePlanActivity, UMCreatePlanStep8Present> implements UMCreatePlanStep8Contract.View {

    @BindView(R.id.view_pager)
    CustomViewPager viewPager;
    @BindView(R.id.circle_indicator_pager)
    CircleIndicatorPager indicator;

    private List<BaseFragment> mListFragment;
    private CustomViewPagerAdapter mAdapter;


    public static UMCreatePlanStep8Fragment newInstance() {
        Bundle args = new Bundle();
        UMCreatePlanStep8Fragment fragment = new UMCreatePlanStep8Fragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int contentViewLayout() {
        return R.layout.fragment_create_plan_um_step8;
    }

    @Override
    public void initializeLayout(View view) {
        mActionListener = new UMCreatePlanStep8Present(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViewpagers();
    }

    @Override
    public void initViewpagers() {
        mListFragment = new ArrayList<>();
        mListFragment.add(UMCreatePlanStep8Screen1Fragment.newInstance());
        mListFragment.add(UMCreatePlanStep8Screen1Fragment.newInstance());

        mAdapter = new CustomViewPagerAdapter(getChildFragmentManager(), mListFragment);
        if (viewPager != null) {
            viewPager.setAdapter(mAdapter);
        }
        if (indicator != null) {
            indicator.setViewPager(viewPager);
        }
    }

    @OnClick(R.id.btn_complete)
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btn_complete: {
                Toast.makeText(mActivity, "gọi api hoàn tất", Toast.LENGTH_SHORT).show();
                break;
            }
        }
    }
}
