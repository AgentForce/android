package manulife.manulifesop.activity.first;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import manulife.manulifesop.R;
import manulife.manulifesop.activity.login.LoginActivity;
import manulife.manulifesop.base.BaseActivity;
import manulife.manulifesop.element.callbackInterface.CallBackInformDialog;


public class FirstActivity extends BaseActivity<FirstPresenter> implements FirstContract.View {

    @BindView(R.id.layout_welcome)
    RelativeLayout layoutWelcome;
    @BindView(R.id.btn_agree)
    Button btnAgree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        mActionListener = new FirstPresenter(this,this);
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

    @OnClick({R.id.btn_agree})
    public void onClick(View view)
    {
        int id = view.getId();
        switch (id)
        {
            case R.id.btn_agree:{
                //mActionListener.clickAgreeButton();
                mActionListener.checkPermissionGranted();
                break;
            }
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {

            case 2: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mActionListener.clickAgreeButton();
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

    @Override
    protected void onResume() {
        super.onResume();
        mActionListener.checkInternetViaPingServer();
    }
}
