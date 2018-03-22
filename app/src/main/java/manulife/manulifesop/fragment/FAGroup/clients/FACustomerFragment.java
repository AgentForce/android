package manulife.manulifesop.fragment.FAGroup.clients;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.util.Calendar;

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

    private int mCurrentMonth;

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
        mActivity.showHideActionbar(true);
        mActivity.updateActionbarTitle("Khách hàng");
        mCurrentMonth = Calendar.getInstance().get(Calendar.MONTH);
        initTabMenu();
        //showContentCustomer(mCurrentMonth);
        scrollToTabAfterLayout(mCurrentMonth);
    }

    private void scrollToTabAfterLayout(final int tabIndex) {
        if (getView() != null) {
            final ViewTreeObserver observer = tabLayout.getViewTreeObserver();

            if (observer.isAlive()) {
                observer.dispatchOnGlobalLayout(); // In case a previous call is waiting when this call is made
                observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            observer.removeOnGlobalLayoutListener(this);
                        } else {
                            //noinspection deprecation
                            observer.removeGlobalOnLayoutListener(this);
                        }

                        tabLayout.getTabAt(tabIndex).select();

                    }
                });
            }
        }
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

    private void showContentCustomer(int month) {
        mFragmentTran = getChildFragmentManager().beginTransaction();
        mFragmentTran.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        mFragmentTran.replace(R.id.frame_container_customer, FAContentCustomerFragment.newInstance(month));
        mFragmentTran.commit();
    }

    @Override
    public void showDialogEditCampaign() {
        Fragment CurrentFragment = getChildFragmentManager().findFragmentById(R.id.frame_container_customer);
        if (CurrentFragment instanceof FAContentCustomerFragment)
            ((FAContentCustomerFragment) CurrentFragment).showDialogEditCampaign();
    }
}
