package manulife.manulifesop.fragment.ManagerGroup.recruiment.manageRecruitment.content;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import manulife.manulifesop.R;
import manulife.manulifesop.activity.main.MainFAActivity;
import manulife.manulifesop.base.BaseFragment;
import manulife.manulifesop.fragment.ManagerGroup.recruiment.manageRecruitment.ManageEmployFragment;
import manulife.manulifesop.fragment.ManagerGroup.recruiment.manageRecruitment.content.contentDetail.ContentDetailManageEmployFragment;
import manulife.manulifesop.util.SOPSharedPreferences;
import manulife.manulifesop.util.Utils;

/**
 * Created by Chick on 10/27/2017.
 */

public class ContentManageEmployFragment extends BaseFragment<MainFAActivity, ContentManageEmployPresent> implements ContentManageEmployContract.View {

    @BindView(R.id.layout_root)
    LinearLayout layoutRoot;

    @BindView(R.id.tabs_menu)
    TabLayout tabLayout;
    @BindView(R.id.frame_container_customer)
    FrameLayout mFrameLayout;

    @BindView(R.id.txt_manager_name)
    TextView txtManagerName;

    private FragmentTransaction mFragmentTran;

    private String mType;

    //variable for

    public static ContentManageEmployFragment newInstance(String type) {
        Bundle args = new Bundle();
        args.putString("type", type);
        ContentManageEmployFragment fragment = new ContentManageEmployFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int contentViewLayout() {
        return R.layout.fragment_sm_customer;
    }

    @Override
    public void initializeLayout(View view) {
        mActionListener = new ContentManageEmployPresent(this, getContext());
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        hideKeyboardOutside(layoutRoot);
        mType = getArguments().getString("type", "");
        initTabMenu();
        if (!mType.equals("campaign"))
            scrollToTabAfterLayout();
    }

    @Override
    public void initTabMenu() {
        if (mType.equals("month")) {
            tabLayout.setVisibility(View.VISIBLE);
            for (int i = 1; i <= 4; i++) {
                tabLayout.addTab(tabLayout.newTab().setText("Giai đoạn " + i).setTag(i));
            }
        } else if (mType.equals("year")) {
            tabLayout.setVisibility(View.VISIBLE);

            String startDate = SOPSharedPreferences.getInstance(getContext()).getCampaignStart();
            int monthStart = Utils.getMonthFromStringDate(startDate, "dd/MM/yyyy")+1;
            String endDate = SOPSharedPreferences.getInstance(getContext()).getCampaignEnd();
            int monthEnd = Utils.getMonthFromStringDate(endDate, "dd/MM/yyyy")+1;

            for (int i = monthStart; i <= monthEnd; i++) {
                tabLayout.addTab(tabLayout.newTab().setText("Tháng " + i).setTag(i));
            }
        } else {
            tabLayout.setVisibility(View.GONE);
            showContentDetail(1, mType);
        }

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                showContentDetail((Integer) tab.getTag(), mType);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void scrollToTabAfterLayout() {
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
                        if(tabLayout!=null) {
                            tabLayout.getTabAt(0).select();
                            showContentDetail((Integer) tabLayout.getTabAt(0).getTag(), mType);
                        }
                    }
                });
            }
        }
    }

    @Override
    public void showContentDetail(int processStep, String type) {
        mFragmentTran = getChildFragmentManager().beginTransaction();
        mFragmentTran.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        mFragmentTran.replace(R.id.frame_container_customer, ContentDetailManageEmployFragment.newInstance(processStep, type));
        mFragmentTran.commit();
    }

    @Override
    public int getParrentSelectedPage() {
        return ((ManageEmployFragment) getParentFragment()).getSeletedPage();
    }

    @Override
    public void setViewsHeight() {
        /*((ContentDetailManageSaleFragment)getChildFragmentManager()
                        .findFragmentById(R.id.frame_container_customer)).initviewsHeight();*/
        ContentDetailManageEmployFragment fragment = (ContentDetailManageEmployFragment) getChildFragmentManager()
                .findFragmentById(R.id.frame_container_customer);
        if (fragment != null) {
            fragment.initviewsHeight();
        }
    }

    @Override
    public void setManagerName(String name) {
        txtManagerName.setText(name);
    }

    @Override
    public int getUserIDProcessing() {
        int userID = -1;
        ContentDetailManageEmployFragment fragment = (ContentDetailManageEmployFragment) getChildFragmentManager()
                .findFragmentById(R.id.frame_container_customer);
        if (fragment != null) {
            userID = fragment.getUserIDProcessing();
        }
        return userID;
    }
}
