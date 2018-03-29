package manulife.manulifesop.activity.login;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import manulife.manulifesop.BuildConfig;
import manulife.manulifesop.api.ApiService;
import manulife.manulifesop.api.ObjectInput.InputCreatePass;
import manulife.manulifesop.api.ObjectInput.InputLoginData;
import manulife.manulifesop.api.ObjectResponse.CheckUser;
import manulife.manulifesop.api.ObjectResponse.LoginResult;
import manulife.manulifesop.api.ObjectResponse.VerifyOTP;
import manulife.manulifesop.base.BasePresenter;
import manulife.manulifesop.util.Contants;
import manulife.manulifesop.util.DeviceInfo;
import manulife.manulifesop.util.SOPSharedPreferences;
import manulife.manulifesop.util.Utils;

/**
 * Created by Chick on 10/27/2017.
 */

public class LoginPresent extends BasePresenter<LoginContract.View> implements LoginContract.Action {
    private Context mContext;

    private String mUserCreatePass;
    private String mPassCreatePass;

    public LoginPresent(LoginContract.View presenterView, Context context) {
        super(presenterView);
        this.mContext = context;
    }

    @Override
    public void createPass(String user, String pass) {

        this.mUserCreatePass = user;
        this.mPassCreatePass = pass;

        String checksum = "fadfadf";
        InputCreatePass data = new InputCreatePass();
        data.password = pass;
        data.userName = user;

        getCompositeDisposable().add(ApiService.getServer().setPassword(
                SOPSharedPreferences.getInstance(mContext).getAccessToken(),
                Contants.clientID, DeviceInfo.ANDROID_OS_VERSION, BuildConfig.VERSION_NAME,
                DeviceInfo.DEVICE_NAME, DeviceInfo.DEVICEIMEI, checksum, data)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(this::handleResponseCreatePass, this::handleError));

        //goi login
        //login(user, pass);
    }

    private void handleError(Throwable throwable) {
        mPresenterView.finishLoading(throwable.getMessage(), false);
    }

    private void handleResponseCreatePass(VerifyOTP data) {
        if (data.statusCode == 1) {
            login(mUserCreatePass, mPassCreatePass);
        } else {
            mPresenterView.finishLoading(data.msg, false);
        }
    }

    @Override
    public void login(String userName, String pass) {

        String checksum = "fadfadf";
        InputLoginData data = new InputLoginData();
        data.setUserName(userName);
        data.setPassword(pass);

        getCompositeDisposable().add(ApiService.getServer().login(
                Contants.clientID, DeviceInfo.ANDROID_OS_VERSION, BuildConfig.VERSION_NAME,
                DeviceInfo.DEVICE_NAME, DeviceInfo.DEVICEIMEI, checksum, data)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(this::handleResponseLogin, this::handleError));
    }

    private void handleResponseLogin(LoginResult data) {
        if (data.getStatus() == 1) {
            SOPSharedPreferences.getInstance(mContext).saveToken("Bearer "+data.getData().getAccessToken(),
                    data.getData().getRefreshToken());
            chekCampaign();

        } else {
            mPresenterView.finishLoading(data.getMsg(), false);
        }
    }

    @Override
    public void chekCampaign() {

        getCompositeDisposable().add(ApiService.getServer().checkCampaign(
                SOPSharedPreferences.getInstance(mContext).getAccessToken(),
                Contants.clientID, DeviceInfo.ANDROID_OS_VERSION, BuildConfig.VERSION_NAME,
                DeviceInfo.DEVICE_NAME, DeviceInfo.DEVICEIMEI)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(this::handleResponseCheckCampaign, this::handleError));

    }

    private void handleResponseCheckCampaign(VerifyOTP data) {
        if (data.statusCode == 1) {
            mPresenterView.finishLoading();
            //test
            //data.data.status = false;
            if (data.data.status) {
                //go to main if campaign is created
                mPresenterView.showMainFAActvity();
                //mPresenterView.showConfirmCreatePlan();
            } else {
                mPresenterView.showConfirmCreatePlan();
            }
        } else {
            mPresenterView.finishLoading(data.msg, false);
        }
    }

}
