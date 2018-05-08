package manulife.manulifesop.activity.first;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import chick.indicator.CircleIndicatorPager;
import cn.pedant.SweetAlert.SweetAlertDialog;
import manulife.manulifesop.R;
import manulife.manulifesop.activity.FAGroup.clients.related.signedSuccess.SignedSuccessActivity;
import manulife.manulifesop.activity.main.MainFAActivity;
import manulife.manulifesop.activity.login.LoginActivity;
import manulife.manulifesop.adapter.CustomViewPagerAdapter;
import manulife.manulifesop.base.BaseActivity;
import manulife.manulifesop.base.BaseFragment;
import manulife.manulifesop.element.CustomViewPager;
import manulife.manulifesop.element.callbackInterface.CallBackInformDialog;
import manulife.manulifesop.fragment.FAGroup.createPlane.step1.CreatePlanStep1Fragment;
import manulife.manulifesop.fragment.FAGroup.createPlane.step2.CreatePlanStep2Fragment;
import manulife.manulifesop.fragment.first.FirstFragment;
import manulife.manulifesop.util.Contants;


public class FirstActivity extends BaseActivity<FirstPresenter> implements FirstContract.View {

    @BindView(R.id.layout_welcome)
    RelativeLayout layoutWelcome;

    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.circle_indicator_pager)
    CircleIndicatorPager indicator;

    private List<BaseFragment> mListFragment;
    private CustomViewPagerAdapter mAdapter;

    boolean mIsFromRequestPermission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        mActionListener = new FirstPresenter(this, this);
        //new code
        mIsFromRequestPermission = false;
        initViewpagerWelcome();
    }

    @Override
    public void initViewpagerWelcome() {
        mListFragment = new ArrayList<>();
        mListFragment.add(FirstFragment.newInstance(1));
        mListFragment.add(FirstFragment.newInstance(2));
        mListFragment.add(FirstFragment.newInstance(3));

        mAdapter = new CustomViewPagerAdapter(getSupportFragmentManager(), mListFragment);
        if (viewPager != null) {
            viewPager.setAdapter(mAdapter);
        }
        if (indicator != null) {
            indicator.setViewPager(viewPager);
        }
    }

    @Override
    public void showWelcome() {
        Animation in = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
        layoutWelcome.startAnimation(in);
        layoutWelcome.setVisibility(View.VISIBLE);
    }

    @Override
    public void showLogin() {
        goNextScreen(LoginActivity.class);
    }

    @Override
    public void showFaMainBoard() {
        goNextScreen(MainFAActivity.class);
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {

            case 2: {
                boolean isAllAllowed = true;
                for(int i=0;i<grantResults.length;i++){
                    if(grantResults[i] == PackageManager.PERMISSION_DENIED){
                        isAllAllowed = false;
                        break;
                    }
                }
                if(isAllAllowed){
                    mIsFromRequestPermission = true;
                    mActionListener.clickAgreeButton();
                }else{
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

    @Override
    public void fragmentCheckPermission() {
        mActionListener.checkPermissionGranted();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!mIsFromRequestPermission) {
            mActionListener.checkInternetViaPingServer();
        }else{
            mIsFromRequestPermission = false;
        }
    }
}
