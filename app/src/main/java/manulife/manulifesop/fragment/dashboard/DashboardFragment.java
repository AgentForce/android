package manulife.manulifesop.fragment.dashboard;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import manulife.manulifesop.R;
import manulife.manulifesop.activity.main.MainFAActivity;
import manulife.manulifesop.adapter.CustomViewPagerAdapter;
import manulife.manulifesop.base.BaseFragment;
import manulife.manulifesop.element.CustomViewPager;
import manulife.manulifesop.fragment.FAGroup.dashboardv2.FADashBoardFragment;
import manulife.manulifesop.fragment.ManagerGroup.dashboard.SMDashBoardFragment;
import manulife.manulifesop.fragment.ManagerGroup.manageEmploy.content.contentDetail.ContentDetailManageEmployFragment;

/**
 * Created by Chick on 10/27/2017.
 */

public class DashboardFragment extends BaseFragment<MainFAActivity, DashboardPresent> implements DashboardContract.View {


    @BindView(R.id.tabs_menu_options)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    CustomViewPager viewPager;

    private CustomViewPagerAdapter mAdapterViewPager;
    private List<BaseFragment> mListFragment;
    private List<String> mTabTitles;

    private boolean mIsFA;

    public static DashboardFragment newInstance(boolean isFA) {
        Bundle args = new Bundle();
        args.putBoolean("isFA", isFA);
        DashboardFragment fragment = new DashboardFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int contentViewLayout() {
        return R.layout.fragment_dashboard;
    }

    @Override
    public void initializeLayout(View view) {
        mActionListener = new DashboardPresent(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mActivity.showHideActionbar(true);
        mActivity.updateActionbarTitle("Trang chủ");
        mIsFA = getArguments().getBoolean("isFA", false);
        if (mIsFA)
            initViewPagerFA();
        else
            initViewPagerSM();
    }

    @Override
    public void initViewPagerFA() {
        viewPager.setSwipe(false);
        tabLayout.setVisibility(View.GONE);

        mListFragment = new ArrayList<>();
        mListFragment.add(FADashBoardFragment.newInstance());

        mTabTitles = new ArrayList<>();
        mTabTitles.add("Dashboard FA");

        mAdapterViewPager = new CustomViewPagerAdapter(getChildFragmentManager(), mListFragment, mTabTitles);
        viewPager.setAdapter(mAdapterViewPager);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void initViewPagerSM() {
        viewPager.setSwipe(false);

        mListFragment = new ArrayList<>();
        mListFragment.add(FADashBoardFragment.newInstance());
        mListFragment.add(SMDashBoardFragment.newInstance());

        mTabTitles = new ArrayList<>();
        mTabTitles.add("Dashboard FA");
        mTabTitles.add("Dashboard SM");

        mAdapterViewPager = new CustomViewPagerAdapter(getChildFragmentManager(), mListFragment, mTabTitles);
        viewPager.setAdapter(mAdapterViewPager);
        tabLayout.setupWithViewPager(viewPager);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position == 0){
                    ((FADashBoardFragment)mListFragment.get(0)).initViewHeight();
                }else{
                    ((SMDashBoardFragment)mListFragment.get(1)).initViewHeight();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public int getSelectedPage() {
        return viewPager.getCurrentItem();
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
