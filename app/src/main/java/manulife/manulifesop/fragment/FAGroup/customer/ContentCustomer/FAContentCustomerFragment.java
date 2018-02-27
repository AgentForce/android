package manulife.manulifesop.fragment.FAGroup.customer.ContentCustomer;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import manulife.manulifesop.R;
import manulife.manulifesop.activity.FAGroup.main.MainFAActivity;
import manulife.manulifesop.adapter.CustomViewPagerAdapter;
import manulife.manulifesop.base.BaseFragment;
import manulife.manulifesop.element.CustomViewPager;
import manulife.manulifesop.fragment.FAGroup.createPlane.step2.CreatePlanStep2Fragment;
import manulife.manulifesop.fragment.FAGroup.customer.ContentCustomer.ObjectMonth.FAObjectMonthFragment;
import manulife.manulifesop.fragment.FAGroup.customer.ContentCustomer.ObjectWeek.FAObjectWeekFragment;
import manulife.manulifesop.fragment.FAGroup.customer.FACustomerContract;
import manulife.manulifesop.fragment.FAGroup.customer.FACustomerPresent;
import manulife.manulifesop.fragment.FAGroup.dashboard.FADashBoardFragment;

/**
 * Created by Chick on 10/27/2017.
 */

public class FAContentCustomerFragment extends BaseFragment<MainFAActivity, FAContentCustomerPresent> implements FAContentCustomerContract.View {

    @BindView(R.id.tabs_menu_options)
    TabLayout tabLayoutOptions;
    @BindView(R.id.view_pager)
    CustomViewPager viewPager;

    private CustomViewPagerAdapter mAdapter;

    public static FAContentCustomerFragment newInstance() {
        Bundle args = new Bundle();
        FAContentCustomerFragment fragment = new FAContentCustomerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int contentViewLayout() {
        return R.layout.fragment_fa_customer_content;
    }

    @Override
    public void initializeLayout(View view) {
        mActionListener = new FAContentCustomerPresent(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
    }

    private void initViews() {
        List<String> tabTitles = new ArrayList<>();
        tabTitles.add("Mục tiêu tháng");
        tabTitles.add("Mục tiêu theo tuần");

        List<BaseFragment> mListFragment = new ArrayList<>();
        mListFragment.add(FAObjectMonthFragment.newInstance());
        mListFragment.add(FAObjectWeekFragment.newInstance());

        mAdapter = new CustomViewPagerAdapter(getChildFragmentManager(),
                mListFragment, tabTitles);

        if (viewPager != null) {
            viewPager.setAdapter(mAdapter);
        }

        tabLayoutOptions.setupWithViewPager(viewPager);
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
