package manulife.manulifesop.fragment.FAGroup.createPlane.step1;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import butterknife.OnClick;
import manulife.manulifesop.R;
import manulife.manulifesop.activity.FAGroup.createPlan.CreatePlanActivity;
import manulife.manulifesop.adapter.CustomViewPagerAdapter;
import manulife.manulifesop.base.BaseFragment;
import manulife.manulifesop.element.CustomViewPager;
import manulife.manulifesop.fragment.FAGroup.createPlane.step2.CreatePlanStep2Fragment;

/**
 * Created by Chick on 10/27/2017.
 */

public class CreatePlanStep1Fragment extends BaseFragment<CreatePlanActivity,CreatePlanStep1Present> implements CreatePlanStep1Contract.View {

    /*@BindView(R.id.view_pager_step1)
    CustomViewPager viewPager;*/

    @BindView(R.id.btn_next)
    Button btnNext;

    public static CreatePlanStep1Fragment newInstance() {
        Bundle args = new Bundle();
        CreatePlanStep1Fragment fragment = new CreatePlanStep1Fragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int contentViewLayout() {
        return R.layout.fragment_create_plan_step1;
    }

    @Override
    public void initializeLayout(View view) {
        mActionListener = new CreatePlanStep1Present(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //initTabMenu(view);
    }

    /*private void initTabMenu(View view)
    {
        viewPager.setSwipe(false);
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        *//*for(int i=1;i<=12; i++)
        {
            tabLayout.addTab(tabLayout.newTab().setText("Tháng " + i));
        }*//*

        List<BaseFragment> mListFragment = new ArrayList<>();
        mListFragment.add(CreatePlanStep2Fragment.newInstance());
        mListFragment.add(CreatePlanStep2Fragment.newInstance());
        mListFragment.add(CreatePlanStep2Fragment.newInstance());

        CustomViewPagerAdapter mAdapter = new CustomViewPagerAdapter(getChildFragmentManager() , mListFragment);
        if (viewPager != null) {
            viewPager.setAdapter(mAdapter);
        }

        tabLayout.setupWithViewPager(viewPager);

        for(int i=0; i < tabLayout.getTabCount(); i++) {
            View tab = ((ViewGroup) tabLayout.getChildAt(0)).getChildAt(i);
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) tab.getLayoutParams();
            p.setMargins(10, 0, 10, 0);
            tab.requestLayout();
        }
    }*/

    @OnClick(R.id.btn_next)
    public void onClick(View view)
    {
        int id = view.getId();
        switch (id)
        {
            case R.id.btn_next:
            {
                mActivity.showNextFragment(0);
                break;
            }
        }
    }

}
