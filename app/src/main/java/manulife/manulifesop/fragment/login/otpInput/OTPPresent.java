package manulife.manulifesop.fragment.login.otpInput;


import android.content.Context;

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
import manulife.manulifesop.util.Utils;

/**
 * Created by Chick on 10/27/2017.
 */

public class OTPPresent extends BasePresenter<OTPContract.View> implements OTPContract.Action {

    private Context mContest;

    public OTPPresent(OTPContract.View presenterView, Context context) {
        super(presenterView);
        this.mContest = context;
    }

    @Override
    public void requestOTP(String user, String phone) {

        mPresenterView.showLoading("Yêu cầu OTP!");

        String checksum = Utils.getSignature(phone + user);
        InputRequestOTP data = new InputRequestOTP();
        data.setPhone(phone);
        data.setUserName(user);

        getCompositeDisposable().add(ApiService.getServer().requestOTP(
                Contants.clientID, DeviceInfo.ANDROID_OS_VERSION, BuildConfig.VERSION_NAME, DeviceInfo.DEVICE_NAME, DeviceInfo.DEVICEIMEI, checksum, data)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(this::handleResponseRequestOTP, this::handleError));
    }

    private void handleResponseRequestOTP(RequestOTP requestOTP) {
        if (requestOTP.getStatus() == 200) {
            if (requestOTP.getData().getStatus()) {
                mPresenterView.disableRequestButton(true);
                mPresenterView.finishLoading();
            } else {
                //mPresenterView.disableRequestButton(false);
                mPresenterView.finishLoading(requestOTP.getMsg(), false);
            }
        } else {
            //mPresenterView.disableRequestButton(false);
            mPresenterView.finishLoading(requestOTP.getMsg(), false);
        }

    }

    @Override
    public void verifyOTP(String user, String phone, String otp) {
        mPresenterView.showLoading("Xác thực OTP!");
        String checksum = Utils.getSignature(phone + user);
        InputVerifyOTP data = new InputVerifyOTP();
        data.setCode(otp);
        data.setPhone(phone);
        data.setUserName(user);

        getCompositeDisposable().add(ApiService.getServer().verifyOTP(
                Contants.clientID, DeviceInfo.ANDROID_OS_VERSION, BuildConfig.VERSION_NAME, DeviceInfo.DEVICE_NAME, DeviceInfo.DEVICEIMEI, checksum, data)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(this::handleResponseVerify, this::handleError));
    }

    private void handleResponseVerify(VerifyOTP verifyOTP) {
        if (verifyOTP.getStatusCode() == 200) {
            if (verifyOTP.getData().getStatus()) {
                mPresenterView.finishLoading();
                mPresenterView.showFragmentCreatePass();
            } else {
                mPresenterView.finishLoading(verifyOTP.getMsg(), false);
            }
        } else {
            mPresenterView.finishLoading(verifyOTP.getMsg(), false);
        }
    }

    private void handleError(Throwable throwable) {
        mPresenterView.finishLoading(throwable.getMessage(), false);
    }


}
