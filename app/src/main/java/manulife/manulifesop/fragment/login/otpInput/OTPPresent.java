package manulife.manulifesop.fragment.login.otpInput;


import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import manulife.manulifesop.BuildConfig;
import manulife.manulifesop.api.ApiService;
import manulife.manulifesop.api.ObjectInput.InputRequestOTP;
import manulife.manulifesop.api.ObjectInput.InputVerifyOTP;
import manulife.manulifesop.api.ObjectResponse.RequestOTP;
import manulife.manulifesop.api.ObjectResponse.VerifyOTP;
import manulife.manulifesop.base.BasePresenter;
import manulife.manulifesop.util.Contants;
import manulife.manulifesop.util.DeviceInfo;
import manulife.manulifesop.util.SOPSharedPreferences;
import manulife.manulifesop.util.Utils;

/**
 * Created by Chick on 10/27/2017.
 */

public class OTPPresent extends BasePresenter<OTPContract.View> implements OTPContract.Action {

    private Context mContext;

    public OTPPresent(OTPContract.View presenterView, Context context) {
        super(presenterView);
        this.mContext = context;
    }

    @Override
    public void requestOTP(String user, String phone) {

        mPresenterView.showLoading("Yêu cầu OTP!");

        InputRequestOTP data = new InputRequestOTP();
        data.setPhone(phone);
        data.setUserName(user);

        String checksum = Utils.getSignature(new Gson().toJson(data));

        getCompositeDisposable().add(ApiService.getServer().requestOTP(
                SOPSharedPreferences.getInstance(mContext).getAccessToken(),
                Contants.clientID, DeviceInfo.ANDROID_OS_VERSION, BuildConfig.VERSION_NAME, DeviceInfo.DEVICE_NAME, DeviceInfo.DEVICEIMEI, checksum, data)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(this::handleResponseRequestOTP, this::handleError));
    }

    private void handleResponseRequestOTP(RequestOTP requestOTP) {
        if (requestOTP.statusCode == 1) {
            if (requestOTP.data.status) {
                mPresenterView.disableRequestButton(true);
                mPresenterView.finishLoading();
            } else {
                //mPresenterView.disableRequestButton(false);
                mPresenterView.finishLoading(requestOTP.msg, false);
            }
        } else {
            //mPresenterView.disableRequestButton(false);
            mPresenterView.finishLoading(requestOTP.msg, false);
        }

    }

    @Override
    public void verifyOTP(String user, String phone, String otp) {
        mPresenterView.showLoading("Xác thực OTP!");
        InputVerifyOTP data = new InputVerifyOTP();
        data.setCode(otp);
        data.setPhone(phone);
        data.setUserName(user);

        String checksum = Utils.getSignature(new Gson().toJson(data));

        getCompositeDisposable().add(ApiService.getServer().verifyOTP(
                SOPSharedPreferences.getInstance(mContext).getAccessToken(),
                Contants.clientID, DeviceInfo.ANDROID_OS_VERSION, BuildConfig.VERSION_NAME, DeviceInfo.DEVICE_NAME, DeviceInfo.DEVICEIMEI, checksum, data)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(this::handleResponseVerify, this::handleError));
    }

    private void handleResponseVerify(VerifyOTP verifyOTP) {
        if (verifyOTP.statusCode == 1) {
            mPresenterView.finishLoading();
            mPresenterView.showFragmentCreatePass();
        } else {
            mPresenterView.finishLoading(verifyOTP.msg, false);
        }
    }

    private void handleError(Throwable throwable) {
        mPresenterView.finishLoading(throwable.getMessage(), false);
    }


}
