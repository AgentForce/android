package manulife.manulifesop.fragment.FAGroup.dashboard;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import manulife.manulifesop.R;
import manulife.manulifesop.activity.FAGroup.main.MainFAActivity;
import manulife.manulifesop.adapter.CustomViewPagerAdapter;
import manulife.manulifesop.base.BaseFragment;
import manulife.manulifesop.element.CustomViewPager;
import manulife.manulifesop.fragment.FAGroup.createPlane.step1.CreatePlanStep1Fragment;
import manulife.manulifesop.fragment.FAGroup.createPlane.step2.CreatePlanStep2Fragment;
import manulife.manulifesop.fragment.FAGroup.createPlane.step3.CreatePlanStep3Fragment;
import manulife.manulifesop.fragment.FAGroup.createPlane.step4.CreatePlanStep4Fragment;
import manulife.manulifesop.fragment.FAGroup.dashboard.campaignPercent.CampaignPercentFragment;

/**
 * Created by Chick on 10/27/2017.
 */

public class FADashBoardFragment extends BaseFragment<MainFAActivity,FADashBoardPresent> implements FADashBoardContract.View {

    @BindView(R.id.sliding_tabs)
    TabLayout tabLayout;

    @BindView(R.id.view_pager)
    CustomViewPager viewPager;

    private CustomViewPagerAdapter mAdapter;
    private List<BaseFragment> mListFragment;
    private List<String> mTabTitles;


    public static FADashBoardFragment newInstance() {
        Bundle args = new Bundle();
        FADashBoardFragment fragment = new FADashBoardFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int contentViewLayout() {
        return R.layout.fragment_dashboard_fa;
    }

    @Override
    public void initializeLayout(View view) {
        mActionListener = new FADashBoardPresent(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViewPager();
    }

    private void initViewPager()
    {
        viewPager.setSwipe(false);

        mListFragment = new ArrayList<>();
        mListFragment.add(CampaignPercentFragment.newInstance());
        mListFragment.add(CampaignPercentFragment.newInstance());
        mListFragment.add(CampaignPercentFragment.newInstance());

        mTabTitles = new ArrayList<>();
        mTabTitles.add("Tuần này");
        mTabTitles.add("Tháng 12");
        mTabTitles.add("Năm 2017");

        mAdapter = new CustomViewPagerAdapter(getChildFragmentManager(), mListFragment,mTabTitles);
        if (viewPager != null) {
            viewPager.setAdapter(mAdapter);
            tabLayout.setupWithViewPager(viewPager);
        }

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
