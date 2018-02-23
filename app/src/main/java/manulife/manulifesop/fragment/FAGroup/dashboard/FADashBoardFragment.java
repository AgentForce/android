package manulife.manulifesop.fragment.FAGroup.dashboard;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import manulife.manulifesop.R;
import manulife.manulifesop.activity.FAGroup.main.MainFAActivity;
import manulife.manulifesop.adapter.ActiveHistAdapter;
import manulife.manulifesop.adapter.CustomViewPagerAdapter;
import manulife.manulifesop.adapter.ObjectData.ActiveHistFA;
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
    @BindView(R.id.rcv_log)
    RecyclerView listActiHist;

    private CustomViewPagerAdapter mAdapterViewPager;
    private List<BaseFragment> mListFragment;
    private List<String> mTabTitles;

    //variable for activity hist
    private ActiveHistAdapter mAdapterActiveHist;
    private List<ActiveHistFA> mDataActiveHist;


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
        initActiHistList();
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

        mAdapterViewPager = new CustomViewPagerAdapter(getChildFragmentManager(), mListFragment,mTabTitles);
        if (viewPager != null) {
            viewPager.setAdapter(mAdapterViewPager);
            tabLayout.setupWithViewPager(viewPager);
        }
    }
    private void initActiHistList()
    {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        listActiHist.setLayoutManager(layoutManager);

        //init data
        mDataActiveHist = new ArrayList<>();

        ActiveHistFA temp = new ActiveHistFA();
        temp.setAvatar("avatar");
        temp.setTitle("title code input");
        temp.setContent("content code input");
        mDataActiveHist.add(temp);

        ActiveHistFA temp2 = new ActiveHistFA();
        temp2.setAvatar("avatar");
        temp2.setTitle("title2 code input");
        temp2.setContent("content2 code input");
        mDataActiveHist.add(temp2);

        mAdapterActiveHist = new ActiveHistAdapter(getContext(),mDataActiveHist);

        listActiHist.setAdapter(mAdapterActiveHist);

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
