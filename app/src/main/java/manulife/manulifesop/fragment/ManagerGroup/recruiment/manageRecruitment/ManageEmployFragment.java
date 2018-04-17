package manulife.manulifesop.fragment.ManagerGroup.recruiment.manageRecruitment;

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
import manulife.manulifesop.fragment.ManagerGroup.recruiment.manageRecruitment.content.ContentManageEmployFragment;
import manulife.manulifesop.util.Utils;

/**
 * Created by Chick on 10/27/2017.
 */

public class ManageEmployFragment extends BaseFragment<MainFAActivity, ManageEmployPresent> implements ManageEmployContract.View {


    @BindView(R.id.tabs_menu_options)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    CustomViewPager viewPager;

    private CustomViewPagerAdapter mAdapterViewPager;
    private List<BaseFragment> mListFragment;
    private List<String> mTabTitles;

    private int mCurrentMonth;
    private int mCurrentYear;


    public static ManageEmployFragment newInstance() {
        Bundle args = new Bundle();
        ManageEmployFragment fragment = new ManageEmployFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int contentViewLayout() {
        return R.layout.fragment_dashboard;
    }

    @Override
    public void initializeLayout(View view) {
        mActionListener = new ManageEmployPresent(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mActivity.showHideActionbar(true);
        mActivity.updateActionbarTitle("Quản lý tuyển dụng");
        mCurrentMonth = Utils.getCurrentMonth(getContext());
        mCurrentYear = Utils.getCurrentYear(getContext());
        initViewPager();
    }

    @Override
    public void initViewPager() {
        mListFragment = new ArrayList<>();
        mListFragment.add(ContentManageEmployFragment.newInstance("month"));
        mListFragment.add(ContentManageEmployFragment.newInstance("year"));
        mListFragment.add(ContentManageEmployFragment.newInstance("campaign"));

        mTabTitles = new ArrayList<>();
        mTabTitles.add("Tháng " + mCurrentMonth);
        mTabTitles.add("Năm " + mCurrentYear);
        mTabTitles.add("Chiến dịch");

        mAdapterViewPager = new CustomViewPagerAdapter(getChildFragmentManager(), mListFragment, mTabTitles);
        viewPager.setAdapter(mAdapterViewPager);
        tabLayout.setupWithViewPager(viewPager);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                ((ContentManageEmployFragment)mListFragment
                        .get(position)).setViewsHeight();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public int getSeletedPage() {
        return viewPager.getCurrentItem();
    }
}
