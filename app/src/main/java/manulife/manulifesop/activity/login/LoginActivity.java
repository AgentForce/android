package manulife.manulifesop.activity.login;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.makeramen.roundedimageview.RoundedImageView;

import butterknife.BindView;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import manulife.manulifesop.R;
import manulife.manulifesop.activity.FAGroup.createPlan.CreatePlanActivity;
import manulife.manulifesop.base.BaseActivity;
import manulife.manulifesop.element.callbackInterface.CallBackInformDialog;


/**
 * Created by Chick on 10/27/2017.
 */

public class LoginActivity extends BaseActivity<LoginPresent>
        implements LoginContract.View{

    @BindView(R.id.status_bar)
    View viewStatusBar;

    @BindView(R.id.layout_root)
    LinearLayout layoutRoot;
    @BindView(R.id.layout_user_pass)
    LinearLayout layoutLogin;
    @BindView(R.id.layout_welcome_first_login)
    RelativeLayout layoutWelcome;
    @BindView(R.id.btn_start)
    Button btnStart;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.edt_user_name)
    EditText edtUser;
    @BindView(R.id.edt_user_pass)
    EditText edtPass;

    //views to set data after first login
    @BindView(R.id.txt_actionbar_title)
    TextView txtActionbarTitle;
    @BindView(R.id.img_user_avatar)
    RoundedImageView imgAvatar;
    @BindView(R.id.txt_user_name)
    TextView txtUserName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mActionListener = new LoginPresent(this,this);
        hideKeyboardOutside(layoutRoot,this);
        setupSupportForApp();
        mActionListener.checkPermissionGranted();
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {

            case 2: {

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    /*new DeviceInfo(FirstActivity.this);
                    new TestInternet().execute(5000);*/
                    Toast.makeText(this, "Da cap quyen", Toast.LENGTH_SHORT).show();
                } else {
                    showInform("Thông báo", "Không đủ quyền để chạy chương trình", "OK", SweetAlertDialog.ERROR_TYPE, new CallBackInformDialog() {
                        @Override
                        public void DiaglogPositive() {
                            System.exit(1);
                        }
                    });
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    private void setupSupportForApp()
    {
        int statusBarHeight = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = getResources().getDimensionPixelSize(resourceId);
        }
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) viewStatusBar.getLayoutParams();
        params.height = statusBarHeight;
        viewStatusBar.setLayoutParams(params);
    }

    @OnClick({R.id.btn_start,R.id.btn_login})
    public void onClick(View view)
    {
        int id = view.getId();
        switch (id)
        {
            case R.id.btn_start:{
                goNextScreen(CreatePlanActivity.class);
                break;
            }
            case R.id.btn_login:
                mActionListener.loginApp(edtUser,edtPass);
                break;
        }
    }

    @Override
    public void showWelcomeUser() {

        //show data
        txtActionbarTitle.setText("Welcome");
        imgAvatar.setImageResource(R.drawable.ic_launcher);
        txtUserName.setText("Nguyễn Cao Minh");

        Animation out = AnimationUtils.loadAnimation(this, android.R.anim.fade_out);
        layoutLogin.startAnimation(out);
        layoutLogin.setVisibility(View.GONE);

        Animation in = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
        layoutWelcome.startAnimation(in);
        layoutWelcome.setVisibility(View.VISIBLE);
    }

    @Override
    public void showMainDashboard() {
        Toast.makeText(this, "show main dashboard", Toast.LENGTH_SHORT).show();
    }
}
