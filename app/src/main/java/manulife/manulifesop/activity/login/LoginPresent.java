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
import manulife.manulifesop.api.ObjectResponse.CheckUser;
import manulife.manulifesop.base.BasePresenter;
import manulife.manulifesop.util.Contants;
import manulife.manulifesop.util.DeviceInfo;
import manulife.manulifesop.util.SOPSharedPreferences;
import manulife.manulifesop.util.Utils;

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
    public void checkPermissionGranted() {
        if (isPermissionGranted()) {
            getDeviceInfo(mContest);
        }
    }

    public boolean isPermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            ArrayList<String> temp = new ArrayList<>();
            //READ_PHONE_STATE
            if (mContest.checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
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

    @Override
    public void getDeviceInfo(Context context) {
        new DeviceInfo(context);
    }

    @Override
    public void checkUserIsActive(String agencyID, String phone) {

        String checksum = Utils.getSignature(phone + agencyID);

        getCompositeDisposable().add(ApiService.getServer().checkUser(
                Contants.clientID, DeviceInfo.ANDROID_OS_VERSION, BuildConfig.VERSION_NAME, DeviceInfo.DEVICE_NAME, DeviceInfo.DEVICEIMEI, checksum, phone, agencyID
        ).subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError));

        //truong hop nguoi dung chua active
        //mPresenterView.finishLoading();
        //mPresenterView.showFragmentOTPInput();
        //mPresenterView.finishLoading();
        //mPresenterView.showFragmentPassInput();

    }

    private void handleError(Throwable throwable) {
        mPresenterView.finishLoading(throwable.getMessage(), false);
    }

    private void handleResponse(CheckUser checkUser) {
        if (checkUser.statusCode == 200)//thành công
        {
            switch (checkUser.data.status){
                case 1: {//user chưa active
                    mPresenterView.finishLoading();
                    mPresenterView.showFragmentOTPInput();
                    break;
                }
                case 5:{//user đã active
                    mPresenterView.finishLoading();
                    mPresenterView.showFragmentPassInput();
                    break;
                }
                default:{
                    mPresenterView.finishLoading(checkUser.msg,false);
                }
            }

        } else {
            mPresenterView.finishLoading(checkUser.msg, false);
        }

    }

    @Override
    public void checkOTP(String otp, String user, String phone) {
        mPresenterView.finishLoading();
        mPresenterView.showFragmentCreatePass();
    }

    @Override
    public void createPass(String user, String pass) {
        //goi login
        login(user, pass);
    }

    @Override
    public void login(String userName, String pass) {
        //mPresenterView.finishLoading();
        //mPresenterView.showMainFAActvity();

        //goi check compaign
        checkCampaign();
    }

    private void checkCampaign() {
        mPresenterView.finishLoading();
        //go to main if campaign is created
        //mPresenterView.showMainFAActvity();

        //go to confirm create campaign
        mPresenterView.showConfirmCreatePlan();
    }
}
