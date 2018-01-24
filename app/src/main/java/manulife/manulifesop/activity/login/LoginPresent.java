package manulife.manulifesop.activity.login;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;
import manulife.manulifesop.base.BasePresenter;
import manulife.manulifesop.element.callbackInterface.CallBackInformDialog;
import manulife.manulifesop.util.SOPSharedPreferences;

/**
 * Created by Chick on 10/27/2017.
 */

public class LoginPresent extends BasePresenter<LoginContract.View> implements LoginContract.Action {
    private Context mContest;
    public LoginPresent(LoginContract.View presenterView, Context context) {
        super(presenterView);
        this.mContest = context;
    }

    @Override
    public void loginApp(EditText user, EditText pass) {
        //check null
        if (user.getText().toString().length() < 1)
            mPresenterView.showMessage("Thông báo", "Nhập Email!", SweetAlertDialog.WARNING_TYPE);
        else if (pass.getText().toString().length() < 1)
            mPresenterView.showMessage("Thông báo", "Nhập Password!", SweetAlertDialog.WARNING_TYPE);
        else {
            mPresenterView.showMessage("Thông báo", "Đăng nhập thành công", SweetAlertDialog.SUCCESS_TYPE);
            if (!SOPSharedPreferences.getInstance(mContest).isCreatedPlan()) {
                mPresenterView.showWelcomeUser();
            } else {
                mPresenterView.showMainDashboard();
            }
        }
    }

    @Override
    public void checkPermissionGranted() {
        if(isPermissionGranted())
        {
            Toast.makeText(mContest, "Da cap quyen truoc", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean isPermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            ArrayList<String> temp = new ArrayList<>();
            //READ_PHONE_STATE
            if(mContest.checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED)
            {
                temp.add(Manifest.permission.READ_PHONE_STATE);
            }

            if (temp.size() > 0) {
                String permissions[] = new String[temp.size()];
                for (int i = 0; i < temp.size(); i++) {
                    permissions[i] = temp.get(i);
                }
                ActivityCompat.requestPermissions((Activity) mContest, permissions, 2);
                return false;
            }
        }
        return true;
    }
}
