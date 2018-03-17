package manulife.manulifesop.activity.FAGroup.createPlan;

import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;

import cn.pedant.SweetAlert.SweetAlertDialog;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import manulife.manulifesop.BuildConfig;
import manulife.manulifesop.api.ApiService;
import manulife.manulifesop.api.ObjectInput.InputCreateCampaign;
import manulife.manulifesop.api.ObjectResponse.VerifyOTP;
import manulife.manulifesop.base.BasePresenter;
import manulife.manulifesop.element.callbackInterface.CallBackInformDialog;
import manulife.manulifesop.util.Contants;
import manulife.manulifesop.util.DeviceInfo;
import manulife.manulifesop.util.SOPSharedPreferences;
import manulife.manulifesop.util.Utils;

import static java.lang.Thread.sleep;

/**
 * Created by trinm on 12/01/2018.
 */

public class CreatePlanPresenter extends BasePresenter<CreatePlanContract.View> implements CreatePlanContract.Action {

    private Context mContext;

    public CreatePlanPresenter(CreatePlanContract.View presenterView, Context context) {
        super(presenterView);
        this.mContext = context;
    }

    @Override
    public void createCampaign(String startDate, String endDate, long profit, long contractPrice, long inCome) {
        String checksum = "";
        InputCreateCampaign data = new InputCreateCampaign();

        data.setStartDate(Utils.convertStringDateToStringDate(startDate, "dd/MM/yyyy", "yyyy-MM-dd"));
        data.setCaseSize(contractPrice * 1000000);
        data.setCommissionRate(profit);
        data.setIncomeMonthly(inCome * 1000000);

        getCompositeDisposable().add(ApiService.getServer().createCampaign(
                SOPSharedPreferences.getInstance(mContext).getAccessToken(),
                Contants.clientID, DeviceInfo.ANDROID_OS_VERSION, BuildConfig.VERSION_NAME, DeviceInfo.DEVICE_NAME, DeviceInfo.DEVICEIMEI, checksum, data)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError));
    }

    private void handleError(Throwable throwable) {
        mPresenterView.finishLoading(throwable.getMessage(), false);
    }

    private void handleResponse(VerifyOTP rs) {
        if (rs.getStatusCode() == 1) {
            checkCampaign();
        } else {
            mPresenterView.finishLoading(rs.getMsg(), false);
        }
    }

    @Override
    public void checkCampaign() {
        getCompositeDisposable().add(ApiService.getServer().checkCampaign(
                SOPSharedPreferences.getInstance(mContext).getAccessToken(),
                Contants.clientID, DeviceInfo.ANDROID_OS_VERSION, BuildConfig.VERSION_NAME,
                DeviceInfo.DEVICE_NAME, DeviceInfo.DEVICEIMEI)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(this::handleResponseCheckCampaign, this::handleError));
    }

    private void handleResponseCheckCampaign(VerifyOTP rs) {
        mPresenterView.finishLoading();
        if (rs.getStatusCode() == 1) {
            mPresenterView.showSuccessView(true);
        } else {
            mPresenterView.showSuccessView(false);
        }
    }
}
