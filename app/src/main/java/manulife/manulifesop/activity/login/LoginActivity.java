package manulife.manulifesop.activity.login;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import manulife.manulifesop.R;
import manulife.manulifesop.activity.FAGroup.confirmCreatePlan.ConfirmCreatePlanActivity;
import manulife.manulifesop.activity.FAGroup.main.MainFAActivity;
import manulife.manulifesop.adapter.CustomViewPagerAdapter;
import manulife.manulifesop.adapter.CustomViewPagerAdapter2;
import manulife.manulifesop.base.BaseActivity;
import manulife.manulifesop.base.BaseFragment;
import manulife.manulifesop.element.CustomViewPager;
import manulife.manulifesop.element.callbackInterface.CallBackInformDialog;
import manulife.manulifesop.fragment.login.confirmPassInput.ConfirmPassFragment;
import manulife.manulifesop.fragment.login.createPassInput.CreatePassFragment;
import manulife.manulifesop.fragment.login.otpInput.OTPFragment;
import manulife.manulifesop.fragment.login.agencyInput.AgencyFragment;
import manulife.manulifesop.fragment.login.passInput.PassFragment;
import manulife.manulifesop.fragment.login.phoneInput.PhoneFragment;


/**
 * Created by Chick on 10/27/2017.
 */

public class LoginActivity extends BaseActivity<LoginPresent>
        implements LoginContract.View {

    @BindView(R.id.status_bar)
    View viewStatusBar;
    @BindView(R.id.txt_actionbar_title)
    TextView txtActionbarTitle;
    @BindView(R.id.layout_btn_back)
    LinearLayout btnBack;
    @BindView(R.id.actionbar_custom_all)
    RelativeLayout actionBarAll;

    @BindView(R.id.layout_root)
    LinearLayout layoutRoot;

    @BindView(R.id.view_pager_login)
    CustomViewPager viewPager;


    @BindView(R.id.layout_top_actionbar)
    View layoutTopActionbar;
    @BindView(R.id.layout_login_top)
    LinearLayout layoutLoginTop;
    @BindView(R.id.txt_actionbar_title_2)
    TextView txtActionbarTitle2;

    //variables to process
    private String mAgencyID;
    private String mPhone = "";
    private String mCreatePass;

    private boolean mIsNotActive = false;

    //variable title action bar
    private List<String> mTitleActionBar;

    private boolean isInPassInput = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_new);
        mActionListener = new LoginPresent(this, this);
        hideKeyboardOutside(layoutRoot, this);
        mTitleActionBar = Arrays.asList(getResources().getStringArray(R.array.loin_actionbar_array));
        setupSupportForApp();
        //show agency fragment in first
        //showFragment(AgencyFragment.newInstance(),true);
        initViewPager();

        mActionListener.checkPermissionGranted();
    }

    private void setupSupportForApp() {
        int statusBarHeight = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = getResources().getDimensionPixelSize(resourceId);
        }
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) viewStatusBar.getLayoutParams();
        params.height = statusBarHeight;
        viewStatusBar.setLayoutParams(params);

        //set title
        txtActionbarTitle.setText(mTitleActionBar.get(0));
    }

    private void initViewPager() {
        viewPager.setSwipe(false);

        List<BaseFragment> mListFragment = new ArrayList<>();
        mListFragment.add(AgencyFragment.newInstance());
        mListFragment.add(PhoneFragment.newInstance());
        mListFragment.add(PassFragment.newInstance());//input pass login
        mListFragment.add(OTPFragment.newInstance());
        mListFragment.add(CreatePassFragment.newInstance());
        mListFragment.add(ConfirmPassFragment.newInstance());

        CustomViewPagerAdapter2 mAdapter = new CustomViewPagerAdapter2(getSupportFragmentManager(), mListFragment);
        viewPager.setAdapter(mAdapter);

    }

    @Override
    public void showFragment(BaseFragment fragment, boolean isNext) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        ft.replace(R.id.frame_container, fragment);
        ft.commit();
    }

    @Override
    public void showFragmentPhoneInput(String agencyID) {
        this.mAgencyID = agencyID;

        Animation in = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        Animation out = AnimationUtils.loadAnimation(this, R.anim.fade_out);

        actionBarAll.startAnimation(out);
        actionBarAll.setVisibility(View.GONE);
        //set title input phone
        txtActionbarTitle.setText(mTitleActionBar.get(1));
        btnBack.setVisibility(View.VISIBLE);
        actionBarAll.startAnimation(in);
        actionBarAll.setVisibility(View.VISIBLE);
        //showFragment(PhoneFragment.newInstance(),true);
        viewPager.setCurrentItem(1, true);
    }

    @Override
    public void showFragmentPassInput() {

        this.isInPassInput = true;

        Animation in = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        Animation out = AnimationUtils.loadAnimation(this, R.anim.fade_out);

        layoutTopActionbar.startAnimation(out);
        layoutTopActionbar.setVisibility(View.GONE);
        //set title input phone
        txtActionbarTitle2.setText(mTitleActionBar.get(2));
        btnBack.setVisibility(View.GONE);
        layoutLoginTop.startAnimation(in);
        layoutLoginTop.setVisibility(View.VISIBLE);
        //showFragment(PhoneFragment.newInstance(),true);
        viewPager.setCurrentItem(2, true);
    }

    @Override
    public void showFragmentOTPInput() {
        this.mIsNotActive = true;
        //viewPager.setFitsSystemWindows(false);

        Animation in = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        Animation out = AnimationUtils.loadAnimation(this, R.anim.fade_out);

        actionBarAll.startAnimation(out);
        actionBarAll.setVisibility(View.GONE);
        //set title input phone
        txtActionbarTitle.setText(mTitleActionBar.get(3));
        btnBack.setVisibility(View.VISIBLE);
        actionBarAll.startAnimation(in);
        actionBarAll.setVisibility(View.VISIBLE);
        //showFragment(PhoneFragment.newInstance(),true);
        viewPager.setCurrentItem(3, true);
    }

    @Override
    public void showFragmentCreatePass() {
        Animation in = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        Animation out = AnimationUtils.loadAnimation(this, R.anim.fade_out);

        actionBarAll.startAnimation(out);
        actionBarAll.setVisibility(View.GONE);
        //set title input phone
        txtActionbarTitle.setText(mTitleActionBar.get(4));
        btnBack.setVisibility(View.VISIBLE);
        actionBarAll.startAnimation(in);
        actionBarAll.setVisibility(View.VISIBLE);
        //showFragment(PhoneFragment.newInstance(),true);
        viewPager.setCurrentItem(4, true);
    }

    @Override
    public void showFragmentConfirmPass(String createPass) {
        this.mCreatePass = createPass;
        Animation in = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        Animation out = AnimationUtils.loadAnimation(this, R.anim.fade_out);

        actionBarAll.startAnimation(out);
        actionBarAll.setVisibility(View.GONE);
        //set title input phone
        txtActionbarTitle.setText(mTitleActionBar.get(5));
        btnBack.setVisibility(View.VISIBLE);
        actionBarAll.startAnimation(in);
        actionBarAll.setVisibility(View.VISIBLE);
        //showFragment(PhoneFragment.newInstance(),true);
        viewPager.setCurrentItem(5, true);
    }

    @Override
    public void showCreatePass(String pass) {
        showLoading("Thiết lập mật khẩu!");
        mActionListener.createPass(mAgencyID,pass);
    }

    @Override
    public void showLogin(String pass) {
        showLoading("Đăng nhập tài khoản");
        mActionListener.login(mAgencyID,pass);
    }

    @Override
    public void showMainFAActvity() {
        goNextScreen(MainFAActivity.class);
    }

    @Override
    public void showConfirmCreatePlan() {
        goNextScreen(ConfirmCreatePlanActivity.class);
    }

    @OnClick({R.id.layout_btn_back})
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.layout_btn_back: {
                int backPosition = viewPager.getCurrentItem() - 1;
                if (viewPager.getCurrentItem() == 3) {
                    backPosition = 1;
                }
                //hide back button
                Animation in = AnimationUtils.loadAnimation(this, R.anim.fade_in);
                Animation out = AnimationUtils.loadAnimation(this, R.anim.fade_out);

                actionBarAll.startAnimation(out);
                actionBarAll.setVisibility(View.GONE);
                if (backPosition == 0)
                    btnBack.setVisibility(View.GONE);
                //set title
                txtActionbarTitle.setText(mTitleActionBar.get(backPosition));
                txtActionbarTitle.setVisibility(View.VISIBLE);

                actionBarAll.startAnimation(in);
                actionBarAll.setVisibility(View.VISIBLE);

                //showFragment(AgencyFragment.newInstance(),false);
                viewPager.setCurrentItem(backPosition, true);

                break;
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (viewPager.getCurrentItem() > 0 && !isInPassInput) {
            int backPosition = viewPager.getCurrentItem() - 1;
            if (viewPager.getCurrentItem() == 3)
                backPosition = 1;

            //hide back button
            Animation in = AnimationUtils.loadAnimation(this, R.anim.fade_in);
            Animation out = AnimationUtils.loadAnimation(this, R.anim.fade_out);

            actionBarAll.startAnimation(out);
            actionBarAll.setVisibility(View.GONE);
            if (backPosition == 0)
                btnBack.setVisibility(View.GONE);

            //set title
            txtActionbarTitle.setText(mTitleActionBar.get(backPosition));
            txtActionbarTitle.setVisibility(View.VISIBLE);

            actionBarAll.startAnimation(in);
            actionBarAll.setVisibility(View.VISIBLE);

            //showFragment(AgencyFragment.newInstance(),false);
            viewPager.setCurrentItem(backPosition, true);
        } else {
            super.onBackPressed();
        }
    }

    public void setPhoneInputed(String phone)
    {
        this.mPhone = phone;
    }
    public String getPhoneInputed()
    {
        return mPhone;
    }

    public String getCreatePass()
    {
        return mCreatePass;
    }
    public String getmAgencyID()
    {
        return mAgencyID;
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {

            case 2: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mActionListener.getDeviceInfo(this);
                } else {
                    showInform("Thông báo", "Không đủ quyền để chạy chương trình", "OK", SweetAlertDialog.ERROR_TYPE, new CallBackInformDialog() {
                        @Override
                        public void DiaglogPositive() {
                            Intent startMain = new Intent(Intent.ACTION_MAIN);
                            startMain.addCategory(Intent.CATEGORY_HOME);
                            startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(startMain);
                            System.exit(1);
                        }
                    });
                }
                return;
            }
        }
    }
}
