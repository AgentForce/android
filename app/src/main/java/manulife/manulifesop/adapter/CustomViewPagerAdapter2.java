package manulife.manulifesop.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import manulife.manulifesop.base.BaseFragment;


/**
 * Created by trinm on 12/01/2018.
 */
public class CustomViewPagerAdapter2 extends FragmentPagerAdapter {

    private List<BaseFragment> mListFragments;

    public CustomViewPagerAdapter2(FragmentManager fm, List<BaseFragment> fragments) {
        super(fm);
        mListFragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mListFragments.get(position);
    }

    @Override
    public int getCount() {
        return mListFragments.size();
    }

}
