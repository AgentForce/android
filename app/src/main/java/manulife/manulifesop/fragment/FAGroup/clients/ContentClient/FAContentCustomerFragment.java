package manulife.manulifesop.fragment.FAGroup.clients.ContentClient;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import manulife.manulifesop.R;
import manulife.manulifesop.activity.FAGroup.main.MainFAActivity;
import manulife.manulifesop.adapter.CustomViewPagerAdapter;
import manulife.manulifesop.api.ObjectResponse.CampaignMonth;
import manulife.manulifesop.base.BaseFragment;
import manulife.manulifesop.element.CustomViewPager;
import manulife.manulifesop.fragment.FAGroup.clients.ContentClient.ObjectMonth.FAObjectMonthFragment;
import manulife.manulifesop.fragment.FAGroup.clients.ContentClient.ObjectWeek.FAObjectWeekFragment;

/**
 * Created by Chick on 10/27/2017.
 */

public class FAContentCustomerFragment extends BaseFragment<MainFAActivity, FAContentCustomerPresent> implements FAContentCustomerContract.View {

    @BindView(R.id.tabs_menu_options)
    TabLayout tabLayoutOptions;
    @BindView(R.id.view_pager)
    CustomViewPager viewPager;

    private CustomViewPagerAdapter mAdapter;

    public static FAContentCustomerFragment newInstance(int month) {
        Bundle args = new Bundle();
        args.putInt("month",month);
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

        int month = getArguments().getInt("month",0);
        Toast.makeText(mActivity, String.valueOf(month), Toast.LENGTH_SHORT).show();
        mActionListener.getCampaignMonth(month);
        //initViews();
    }

    @Override
    public void showCampaignsMonth(CampaignMonth data) {
        List<String> tabTitles = new ArrayList<>();
        tabTitles.add("Mục tiêu tháng");
        tabTitles.add("Mục tiêu theo tuần");

        List<BaseFragment> mListFragment = new ArrayList<>();
        mListFragment.add(FAObjectMonthFragment.newInstance(data));
        mListFragment.add(FAObjectWeekFragment.newInstance(data));

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
