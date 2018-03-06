package manulife.manulifesop.fragment.FAGroup.clients;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;

import butterknife.BindView;
import manulife.manulifesop.R;
import manulife.manulifesop.activity.FAGroup.main.MainFAActivity;
import manulife.manulifesop.adapter.CustomViewPagerAdapter;
import manulife.manulifesop.base.BaseFragment;
import manulife.manulifesop.fragment.FAGroup.clients.ContentClient.FAContentCustomerFragment;

/**
 * Created by Chick on 10/27/2017.
 */

public class FACustomerFragment extends BaseFragment<MainFAActivity, FACustomerPresent> implements FACustomerContract.View {


    @BindView(R.id.tabs_menu)
    TabLayout tabLayout;

    @BindView(R.id.frame_container_customer)
    FrameLayout mFrameLayout;

    private FragmentTransaction mFragmentTran;

    private CustomViewPagerAdapter mAdapter;

    public static FACustomerFragment newInstance() {
        Bundle args = new Bundle();
        FACustomerFragment fragment = new FACustomerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int contentViewLayout() {
        return R.layout.fragment_fa_customer;
    }

    @Override
    public void initializeLayout(View view) {
        mActionListener = new FACustomerPresent(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initTabMenu();
        showContentCustomer(1);
    }

    private void initTabMenu() {

        for (int i = 1; i <= 12; i++) {
            tabLayout.addTab(tabLayout.newTab().setText("Tháng " + i).setTag(i));
        }

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                showContentCustomer((Integer) tab.getTag());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void showContentCustomer(int month){
        mFragmentTran = getChildFragmentManager().beginTransaction();
        mFragmentTran.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        mFragmentTran.replace(R.id.frame_container_customer, FAContentCustomerFragment.newInstance(month));
        mFragmentTran.commit();
    }

}
