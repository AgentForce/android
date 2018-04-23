package manulife.manulifesop.activity.FAGroup.createPlan;

import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;

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
import manulife.manulifesop.api.ObjectInput.InputGetForcastTarget;
import manulife.manulifesop.api.ObjectResponse.CampaignForcastTarget;
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

        InputCreateCampaign data = new InputCreateCampaign();

        data.setStartDate(Utils.convertStringDateToStringDate(startDate, "dd/MM/yyyy", "yyyy-MM-dd"));
        data.setEndDate(Utils.convertStringDateToStringDate(endDate, "dd/MM/yyyy", "yyyy-MM-dd"));
        data.setCaseSize(contractPrice * 1000000);
        data.setCommissionRate(profit);
        data.setIncomeMonthly(inCome * 1000000);

        String checksum = Utils.getSignature(new Gson().toJson(data));

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
        if (rs.statusCode == 1) {
            mPresenterView.showSuccessView();
            mPresenterView.finishLoading();
        } else {
            mPresenterView.finishLoading(rs.msg, false);
        }
    }

    @Override
    public void getCampaignForcast(int income, int profit, int contractPrice,String startDate, String endDate) {
        mPresenterView.showLoading("Xử lý dữ liệu");
        InputGetForcastTarget data = new InputGetForcastTarget();
        data.fyc = income * 1000000;
        data.rate = profit;
        data.caseSize = contractPrice * 1000000;
        data.startDate = Utils.convertStringDateToStringDate(startDate, "dd/MM/yyyy", "yyyy-MM-dd");
        data.endDate = Utils.convertStringDateToStringDate(endDate, "dd/MM/yyyy", "yyyy-MM-dd");

        String checksum = Utils.getSignature(new Gson().toJson(data));

        getCompositeDisposable().add(ApiService.getServer().getCampaignForcast(
                SOPSharedPreferences.getInstance(mContext).getAccessToken(),
                Contants.clientID, DeviceInfo.ANDROID_OS_VERSION, BuildConfig.VERSION_NAME, DeviceInfo.DEVICE_NAME, DeviceInfo.DEVICEIMEI, checksum, data)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(this::handleResponseGetForcast, this::handleError));
    }

    private void handleResponseGetForcast(CampaignForcastTarget rs) {
        if(rs.statusCode == 1){
            mPresenterView.showForcast(rs);
            mPresenterView.finishLoading();
        }else{
            mPresenterView.finishLoading(rs.msg,false);
        }
    }
}
