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
import manulife.manulifesop.util.Utils;

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

    //variable for finish loading for child
    private List<Boolean> mListChildLoaded;

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
        mListChildLoaded = new ArrayList<>();
        if (mIsFA) {
            mListChildLoaded.add(false);
            initViewPagerFA();
        }
        else {
            mListChildLoaded.add(false);
            mListChildLoaded.add(false);
            initViewPagerSM();
        }
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
    public void finishLoadingChild(int positionChild) {
        boolean isAllDone = true;
        //check positionChild is out of range
        if(positionChild >= mListChildLoaded.size()){
            //đang trong trường hợp FA position là 1 mà chỉ load 1 màn hình
            mListChildLoaded.set(0, true);
        }else {
            mListChildLoaded.set(positionChild, true);
        }

        for(int i=0;i<mListChildLoaded.size();i++){
            if(!mListChildLoaded.get(i)){
                isAllDone = false;
                break;
            }
        }
        if(isAllDone) finishLoading();
    }

    @Override
    public void initViewPagerSM() {
        viewPager.setSwipe(false);

        mListFragment = new ArrayList<>();
        mListFragment.add(SMDashBoardFragment.newInstance());
        mListFragment.add(FADashBoardFragment.newInstance());

        mTabTitles = new ArrayList<>();
        mTabTitles.add("Tuyển dụng T" + Utils.getCurrentMonth(getContext()));
        mTabTitles.add("Bán hàng T" + Utils.getCurrentMonth(getContext()));

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
                    ((SMDashBoardFragment)mListFragment.get(0)).initViewHeight();
                }else{
                    ((FADashBoardFragment)mListFragment.get(1)).initViewHeight();
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
        //refresh list boolean loaded
        for(int i=0;i<mListChildLoaded.size();i++){
            mListChildLoaded.set(i,false);
        }
    }
}
