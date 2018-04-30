package manulife.manulifesop.activity.main;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationAdapter;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import manulife.manulifesop.ProjectApplication;
import manulife.manulifesop.R;
import manulife.manulifesop.activity.login.LoginActivity;
import manulife.manulifesop.base.BaseActivity;
import manulife.manulifesop.fragment.FAGroup.clients.FACustomerFragment;
import manulife.manulifesop.fragment.FAGroup.confirmCreatePlan.ConfirmCreatePlanFragment;
import manulife.manulifesop.fragment.FAGroup.dashboardv2.FADashBoardFragment;
import manulife.manulifesop.fragment.FAGroup.events.FAEventsFragment;
import manulife.manulifesop.fragment.FAGroup.personal.FAPersonalFragment;
import manulife.manulifesop.fragment.ManagerGroup.manageSale.ManageSaleFragment;
import manulife.manulifesop.fragment.ManagerGroup.recruiment.manageRecruitment.ManageEmployFragment;
import manulife.manulifesop.fragment.ManagerGroup.menuDashBoard.menuEmploy.SMEmployMenuFragment;
import manulife.manulifesop.fragment.ManagerGroup.menuDashBoard.menuSale.SMSaleMenuFragment;
import manulife.manulifesop.fragment.ManagerGroup.recruiment.personalRecruitment.PersonalRecruitmentFragment;
import manulife.manulifesop.fragment.dashboard.DashboardFragment;
import manulife.manulifesop.util.SOPSharedPreferences;
import manulife.manulifesop.util.Utils;

/**
 * Created by Chick on 1/23/2018.
 */

public class MainFAActivity extends BaseActivity<MainFAPresenter> implements MainFAContract.View {

    @BindView(R.id.layout_root)
    RelativeLayout layoutRoot;

    @BindView(R.id.layout_top)
    LinearLayout layoutAllActionbar;

    @BindView(R.id.layout_notification)
    RelativeLayout layoutNotification;
    @BindView(R.id.layout_edit)
    RelativeLayout layoutEdit;

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
    private boolean firstBackPressed = false;
    private boolean mIsFA;

    AHBottomNavigationAdapter mNavigationAdapter;

    private FragmentTransaction mFragmentTran;
    private Fragment mCurrentFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_fa);
        hideKeyboardOutside(layoutRoot);
        mActionListener = new MainFAPresenter(this, this);
        mIsFA = SOPSharedPreferences.getInstance(this).getIsFA();
        setupSupportForApp();
        if (mIsFA)
            setupMenuBotFA();
        else
            setupMenuBotSM();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!mIsgetCampaign) {
            mActionListener.chekCampaign();
        }
    }

    private void setupSupportForApp() {
        layoutNotification.setVisibility(View.VISIBLE);
        layoutEdit.setVisibility(View.GONE);

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

    private void setupMenuBotFA() {
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
                        if (mIsgetCampaign)
                            showDashBoard();
                        else
                            showFragmentConfirmCreatePlan("Trang chủ");

                        break;
                    }
                    case 1: {

                        if (mIsgetCampaign)
                            showCustomer("Khách hàng");
                        else
                            showFragmentConfirmCreatePlan("Khách hàng");
                        break;
                    }
                    case 2: {
                        if (mIsgetCampaign)
                            showEvents();
                        else
                            showFragmentConfirmCreatePlan("Tháng " + Utils.getCurrentMonth(getApplicationContext()));

                        break;
                    }
                    case 3: {
                        showPersonal();
                        break;
                    }
                }
                return true;
            }
        });
    }

    private void setupMenuBotSM() {
        int[] tabColors = getApplicationContext().getResources().getIntArray(R.array.tab_colors);
        mNavigationAdapter = new AHBottomNavigationAdapter(this, R.menu.menu_bot_sm);

        mNavigationAdapter.setupWithBottomNavigation(bottomNavigation, tabColors);
        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
        bottomNavigation.setAccentColor(getResources().getColor(R.color.colorPrimary));
        bottomNavigation.setInactiveColor(getResources().getColor(R.color.botMenuDisable));

        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                switch (position) {
                    case 0: {
                        if (mIsgetCampaign)
                            showDashBoard();
                        else
                            showFragmentConfirmCreatePlan("Trang chủ");

                        break;
                    }
                    case 1: {

                        if (mIsgetCampaign)
                            showMenuEmploy();
                        else
                            showFragmentConfirmCreatePlan("Tuyển dụng");

                        break;
                    }
                    case 2: {

                        if (mIsgetCampaign)
                            showMenuSale();
                        else
                            showFragmentConfirmCreatePlan("Bán hàng");
                        break;
                    }
                    case 3: {
                        if (mIsgetCampaign)
                            showEvents();
                        else
                            showFragmentConfirmCreatePlan("Tháng " + Utils.getCurrentMonth(getApplicationContext()));

                        break;
                    }
                    case 4: {
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
        if (isShow) {
            if (layoutAllActionbar.getVisibility() == View.GONE) {
                layoutAllActionbar.setVisibility(View.VISIBLE);
            }
        } else {
            if (layoutAllActionbar.getVisibility() == View.VISIBLE) {
                layoutAllActionbar.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void showFragmentConfirmCreatePlan(String title) {
        mIsgetCampaign = false;
        layoutNotification.setVisibility(View.GONE);
        layoutEdit.setVisibility(View.GONE);
        mFragmentTran = getSupportFragmentManager().beginTransaction();
        //ft.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        mFragmentTran.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        mFragmentTran.replace(R.id.frame_container, ConfirmCreatePlanFragment.newInstance(title));
        mFragmentTran.commit();
    }

    @Override
    public void showDashBoard() {
        mCurrentFragment = getSupportFragmentManager().findFragmentById(R.id.frame_container);
        if (!(mCurrentFragment instanceof DashboardFragment)) {
            mIsgetCampaign = true;
            layoutNotification.setVisibility(View.VISIBLE);
            layoutEdit.setVisibility(View.GONE);

            mFragmentTran = getSupportFragmentManager().beginTransaction();
            mFragmentTran.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
            mFragmentTran.replace(R.id.frame_container, DashboardFragment.newInstance(mIsFA));
            mFragmentTran.commit();
        }
    }

    @Override
    public void showCustomer(String title) {
        mCurrentFragment = getSupportFragmentManager().findFragmentById(R.id.frame_container);
        if (!(mCurrentFragment instanceof FACustomerFragment)) {
            layoutNotification.setVisibility(View.GONE);
            layoutEdit.setVisibility(View.VISIBLE);

            mFragmentTran = getSupportFragmentManager().beginTransaction();
            mFragmentTran.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
            mFragmentTran.replace(R.id.frame_container, FACustomerFragment.newInstance(title));
            mFragmentTran.commit();
        }
    }

    @Override
    public void showPersonalRecruiment() {
        mCurrentFragment = getSupportFragmentManager().findFragmentById(R.id.frame_container);
        if (!(mCurrentFragment instanceof PersonalRecruitmentFragment)) {
            layoutNotification.setVisibility(View.GONE);
            layoutEdit.setVisibility(View.VISIBLE);

            mFragmentTran = getSupportFragmentManager().beginTransaction();
            mFragmentTran.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
            mFragmentTran.replace(R.id.frame_container, PersonalRecruitmentFragment.newInstance());
            mFragmentTran.commit();
        }
    }

    @Override
    public void showEvents() {
        mCurrentFragment = getSupportFragmentManager().findFragmentById(R.id.frame_container);
        if (!(mCurrentFragment instanceof FAEventsFragment)) {
            layoutNotification.setVisibility(View.GONE);
            layoutEdit.setVisibility(View.GONE);

            mFragmentTran = getSupportFragmentManager().beginTransaction();
            mFragmentTran.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
            mFragmentTran.replace(R.id.frame_container, FAEventsFragment.newInstance());

            mFragmentTran.commit();
        }
    }

    @Override
    public void showPersonal() {
        mFragmentTran = getSupportFragmentManager().beginTransaction();
        mFragmentTran.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        mFragmentTran.replace(R.id.frame_container, FAPersonalFragment.newInstance());
        mFragmentTran.commit();
    }

    @Override
    public void showMenuSale() {
        mCurrentFragment = getSupportFragmentManager().findFragmentById(R.id.frame_container);
        if (!(mCurrentFragment instanceof SMSaleMenuFragment)) {
            layoutNotification.setVisibility(View.VISIBLE);
            layoutEdit.setVisibility(View.GONE);

            mFragmentTran = getSupportFragmentManager().beginTransaction();
            mFragmentTran.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
            mFragmentTran.replace(R.id.frame_container, SMSaleMenuFragment.newInstance());
            mFragmentTran.commit();
        }
    }

    @Override
    public void showMenuEmploy() {
        mCurrentFragment = getSupportFragmentManager().findFragmentById(R.id.frame_container);
        if (!(mCurrentFragment instanceof SMEmployMenuFragment)) {
            layoutNotification.setVisibility(View.VISIBLE);
            layoutEdit.setVisibility(View.GONE);

            mFragmentTran = getSupportFragmentManager().beginTransaction();
            mFragmentTran.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
            mFragmentTran.replace(R.id.frame_container, SMEmployMenuFragment.newInstance());
            mFragmentTran.commit();
        }
    }

    @Override
    public void showManageEmploy() {
        mCurrentFragment = getSupportFragmentManager().findFragmentById(R.id.frame_container);
        if (!(mCurrentFragment instanceof ManageEmployFragment)) {
            layoutNotification.setVisibility(View.VISIBLE);
            layoutEdit.setVisibility(View.GONE);

            mFragmentTran = getSupportFragmentManager().beginTransaction();
            mFragmentTran.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
            mFragmentTran.replace(R.id.frame_container, ManageEmployFragment.newInstance());
            mFragmentTran.commit();
        }
    }

    @Override
    public void showManageSale() {
        mCurrentFragment = getSupportFragmentManager().findFragmentById(R.id.frame_container);
        if (!(mCurrentFragment instanceof ManageSaleFragment)) {
            layoutNotification.setVisibility(View.VISIBLE);
            layoutEdit.setVisibility(View.GONE);

            mFragmentTran = getSupportFragmentManager().beginTransaction();
            mFragmentTran.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
            mFragmentTran.replace(R.id.frame_container, ManageSaleFragment.newInstance());
            mFragmentTran.commit();
        }
    }

    @OnClick({R.id.layout_notification, R.id.layout_edit, R.id.layout_add})
    void onClickViews(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.layout_notification: {
                Toast.makeText(this, "notification", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.layout_edit: {
                mCurrentFragment = getSupportFragmentManager().findFragmentById(R.id.frame_container);
                if (mCurrentFragment instanceof FACustomerFragment)
                    ((FACustomerFragment) mCurrentFragment).showDialogEditCampaign();
                else if (mCurrentFragment instanceof PersonalRecruitmentFragment)
                    ((PersonalRecruitmentFragment) mCurrentFragment).showDialogEditCampaign();

                break;
            }
        }
    }

    @Override
    public void onBackPressed() {
        //check if is in ManageSaleFragment
        mCurrentFragment = getSupportFragmentManager().findFragmentById(R.id.frame_container);
        if (mCurrentFragment instanceof ManageEmployFragment) {
            if (SOPSharedPreferences.getInstance(getApplicationContext()).getUserID()
                    != ((ManageEmployFragment) mCurrentFragment).getUserIDProcessing())
                EventBus.getDefault().post("back to report to in ContentDetailManageEmployFragment");
            else
                processBackPressed();
        } else if (mCurrentFragment instanceof ManageSaleFragment) {
            if (SOPSharedPreferences.getInstance(getApplicationContext()).getUserID()
                    != ((ManageSaleFragment) mCurrentFragment).getUserIDProcessing())
                EventBus.getDefault().post("back to report to in ContentDetailManageSaleFragment");
            else
                processBackPressed();
        } else
            processBackPressed();

    }

    private void processBackPressed() {
        if (!firstBackPressed) {
            firstBackPressed = true;
            SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE);
            sweetAlertDialog.setTitleText("Xác nhận");
            sweetAlertDialog.setContentText("Nhấn lần nửa để thoát ứng dụng");
            sweetAlertDialog.setCanceledOnTouchOutside(true);
            sweetAlertDialog.setOnCancelListener(dialog -> {
                firstBackPressed = false;
            });
            sweetAlertDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        dialog.dismiss();
                        moveTaskToBack(true);
                        MainFAActivity.super.onBackPressed();
                    }
                    return false;
                }
            });
            sweetAlertDialog.show();
        }
    }

    @Override
    public void showLogin() {
        goNextScreen(LoginActivity.class);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

}
