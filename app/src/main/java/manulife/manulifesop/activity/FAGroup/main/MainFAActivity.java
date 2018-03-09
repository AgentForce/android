package manulife.manulifesop.activity.FAGroup.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationAdapter;

import butterknife.BindView;
import manulife.manulifesop.R;
import manulife.manulifesop.base.BaseActivity;
import manulife.manulifesop.fragment.FAGroup.confirmCreatePlan.ConfirmCreatePlanFragment;
import manulife.manulifesop.fragment.FAGroup.clients.FACustomerFragment;
import manulife.manulifesop.fragment.FAGroup.dashboard.FADashBoardFragment;
import manulife.manulifesop.fragment.FAGroup.events.FAEventsFragment;
import manulife.manulifesop.fragment.FAGroup.personal.FAPersonalFragment;

/**
 * Created by Chick on 1/23/2018.
 */

public class MainFAActivity extends BaseActivity<MainFAPresenter> implements MainFAContract.View {

    @BindView(R.id.layout_top)
    LinearLayout layoutAllActionbar;

    @BindView(R.id.layout_notification)
    RelativeLayout layoutNotification;
    @BindView(R.id.txt_actionbar_title)
    TextView txtActionbarTitle;
    @BindView(R.id.status_bar)
    View viewStatusBar;
    @BindView(R.id.layout_btn_back)
    LinearLayout layoutBackButton;

    @BindView(R.id.bottom_navigation)
    AHBottomNavigation bottomNavigation;

    @BindView(R.id.frame_container)
    FrameLayout frameLayout;

    private boolean mIsgetCampaign = false;

    AHBottomNavigationAdapter mNavigationAdapter;

    private FragmentTransaction mFragmentTran;
    private Fragment mCurrentFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_fa);
        mActionListener = new MainFAPresenter(this, this);
        setupSupportForApp();
        setupMenuBot();
        mIsgetCampaign = getIntent().getExtras()
                .getBoolean("isGetCampaign", false);
        mActionListener.checkIsGetCampaign(mIsgetCampaign);

    }

    private void setupSupportForApp() {
        layoutNotification.setVisibility(View.VISIBLE);

        txtActionbarTitle.setText(getResources().getString(R.string.activity_main_fa_dashboard));
        layoutBackButton.setVisibility(View.GONE);

        int statusBarHeight = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = getResources().getDimensionPixelSize(resourceId);
        }
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) viewStatusBar.getLayoutParams();
        params.height = statusBarHeight;
        viewStatusBar.setLayoutParams(params);
    }

    private void setupMenuBot() {
        int[] tabColors = getApplicationContext().getResources().getIntArray(R.array.tab_colors);
        mNavigationAdapter = new AHBottomNavigationAdapter(this, R.menu.menu_bot);
        mNavigationAdapter.setupWithBottomNavigation(bottomNavigation, tabColors);
        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
        bottomNavigation.setAccentColor(getResources().getColor(R.color.colorPrimary));
        bottomNavigation.setInactiveColor(getResources().getColor(R.color.botMenuDisable));

        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                switch (position) {
                    case 0: {
                        mCurrentFragment = getSupportFragmentManager().findFragmentById(R.id.frame_container);
                        if (!(mCurrentFragment instanceof FADashBoardFragment)
                                && mIsgetCampaign) {
                            showDashBoard();
                        }
                        break;
                    }
                    case 1: {
                        mCurrentFragment = getSupportFragmentManager().findFragmentById(R.id.frame_container);
                        if (!(mCurrentFragment instanceof FACustomerFragment)
                                && mIsgetCampaign) {
                            showCustomer();
                        }
                        break;
                    }
                    case 2: {
                        //Toast.makeText(MainFAActivity.this, "position 2", Toast.LENGTH_SHORT).show();
                        mCurrentFragment = getSupportFragmentManager().findFragmentById(R.id.frame_container);
                        if (!(mCurrentFragment instanceof FAEventsFragment)
                                && mIsgetCampaign) {
                            showEvents();
                        }
                        break;
                    }
                    case 3: {
                        //Toast.makeText(MainFAActivity.this, "position 3", Toast.LENGTH_SHORT).show();
                        showPersonal();
                        break;
                    }
                }
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_notify, menu);
        return true;
    }

    @Override
    public void updateActionbarTitle(String title) {
        txtActionbarTitle.setText(title);
    }

    @Override
    public void showHideActionbar(boolean isShow) {
        if(isShow) {
            if (layoutAllActionbar.getVisibility() == View.GONE){
                layoutAllActionbar.setVisibility(View.VISIBLE);
            }
        }else{
            if (layoutAllActionbar.getVisibility() == View.VISIBLE){
                layoutAllActionbar.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void showFragmentConfirmCreatePlan() {
        mFragmentTran = getSupportFragmentManager().beginTransaction();
        //ft.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        mFragmentTran.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        mFragmentTran.replace(R.id.frame_container, ConfirmCreatePlanFragment.newInstance());
        mFragmentTran.commit();
    }

    @Override
    public void showDashBoard() {
        mFragmentTran = getSupportFragmentManager().beginTransaction();
        mFragmentTran.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        mFragmentTran.replace(R.id.frame_container, FADashBoardFragment.newInstance());
        mFragmentTran.commit();
    }

    @Override
    public void showCustomer() {
        mFragmentTran = getSupportFragmentManager().beginTransaction();
        mFragmentTran.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        mFragmentTran.replace(R.id.frame_container, FACustomerFragment.newInstance());
        mFragmentTran.commit();
    }

    @Override
    public void showEvents() {
        mFragmentTran = getSupportFragmentManager().beginTransaction();
        mFragmentTran.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        mFragmentTran.replace(R.id.frame_container, FAEventsFragment.newInstance());
        mFragmentTran.commit();
    }

    @Override
    public void showPersonal() {
        mFragmentTran = getSupportFragmentManager().beginTransaction();
        mFragmentTran.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        mFragmentTran.replace(R.id.frame_container, FAPersonalFragment.newInstance());
        mFragmentTran.commit();
    }

}
