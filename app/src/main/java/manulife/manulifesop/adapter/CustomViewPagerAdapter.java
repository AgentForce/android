package manulife.manulifesop.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import manulife.manulifesop.base.BaseFragment;


/**
 * Created by trinm on 12/01/2018.
 */
public class CustomViewPagerAdapter extends FragmentStatePagerAdapter {
    //FragmentPagerAdapter

    private List<BaseFragment> mListFragments;
    private List<String> mTabTitles;

    private FragmentManager fm;

    public CustomViewPagerAdapter(FragmentManager fm, List<BaseFragment> fragments) {
        super(fm);
        this.fm = fm;
        mListFragments = fragments;
        mTabTitles = null;
    }

    public CustomViewPagerAdapter(FragmentManager fm, List<BaseFragment> fragments, List<String> tabTitles) {
        super(fm);
        mListFragments = fragments;
        mTabTitles = tabTitles;
    }

    @Override
    public Fragment getItem(int position) {
        return mListFragments.get(position);
    }

    @Override
    public int getCount() {
        return mListFragments.size();
    }

    //title for tablayout
    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        //return "Tháng " + (position+1);
        if (mTabTitles != null) {
            return mTabTitles.get(position);
        }
        return "";
    }
}
