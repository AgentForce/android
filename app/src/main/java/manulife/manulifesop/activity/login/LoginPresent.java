package manulife.manulifesop.activity.login;

import android.content.Context;

import com.google.gson.Gson;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import manulife.manulifesop.BuildConfig;
import manulife.manulifesop.ProjectApplication;
import manulife.manulifesop.api.ApiService;
import manulife.manulifesop.api.ObjectInput.InputCreatePass;
import manulife.manulifesop.api.ObjectInput.InputLoginData;
import manulife.manulifesop.api.ObjectResponse.CheckCampaign;
import manulife.manulifesop.api.ObjectResponse.LoginResult;
import manulife.manulifesop.api.ObjectResponse.UserProfile;
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
    private String mUser;

    public LoginPresent(LoginContract.View presenterView, Context context) {
        super(presenterView);
        this.mContext = context;
    }

    @Override
    public void createPass(String user, String pass) {

        this.mUserCreatePass = user;
        this.mPassCreatePass = pass;

        InputCreatePass data = new InputCreatePass();
        data.password = pass;
        data.userName = user;

        String checksum = Utils.getSignature(new Gson().toJson(data));

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

        this.mUser = userName;
        InputLoginData data = new InputLoginData();
        data.setUserName(userName);
        data.setPassword(pass);

        String checksum = Utils.getSignature(new Gson().toJson(data));

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
            SOPSharedPreferences.getInstance(mContext).saveTokenUser("Bearer "+data.getData().getAccessToken(),
                    data.getData().getRefreshToken());

            getUserProfile(mUser);

        } else {
            mPresenterView.clearPass();
            mPresenterView.finishLoading(data.getMsg(), false);

        }
    }

    @Override
    public void getUserProfile(String userName) {
        mPresenterView.showLoading("Lấy dữ liệu");
        getCompositeDisposable().add(ApiService.getServer().getUserProfile(
                SOPSharedPreferences.getInstance(mContext).getAccessToken(),
                Contants.clientID, DeviceInfo.ANDROID_OS_VERSION, BuildConfig.VERSION_NAME,
                DeviceInfo.DEVICE_NAME, DeviceInfo.DEVICEIMEI,userName)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(this::handleResponseGetProfile, this::handleError));
    }

    private void handleResponseGetProfile(UserProfile rs) {
        if(rs.statusCode == 1){
            //rs.data.level = 15;
            SOPSharedPreferences.getInstance(mContext).saveUser(mUser,rs.data.id);
            SOPSharedPreferences.getInstance(mContext).saveIsFA(rs.data.level>15);
            SOPSharedPreferences.getInstance(mContext).saveLevel(rs.data.level);
            ProjectApplication.getInstance().setOnboardDate(rs.data.onboardDate,"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            chekCampaign();
        }else
            mPresenterView.finishLoading(rs.msg, false);
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

    private void handleResponseCheckCampaign(CheckCampaign data) {
        if (data.statusCode == 1) {
            //test
            //data.data.status = false;
            if (data.data.status) {
                //go to main if campaign is created
                SOPSharedPreferences.getInstance(mContext).saveCampaignStartEnd(
                        data.data.data.startDate,data.data.data.endDate,"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                mPresenterView.showMainFAActvity();
            } else {
                mPresenterView.showConfirmCreatePlan();
            }
        } else {
            mPresenterView.finishLoading(data.msg, false);
        }
    }
}
