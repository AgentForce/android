package manulife.manulifesop.fragment.ManagerGroup.dashboard.SMcampaignPercent;


import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import manulife.manulifesop.BuildConfig;
import manulife.manulifesop.api.ApiService;
import manulife.manulifesop.api.ObjectInput.InputChangeCampaignWeek;
import manulife.manulifesop.api.ObjectInput.InputIncreaseContact;
import manulife.manulifesop.api.ObjectResponse.BaseResponse;
import manulife.manulifesop.base.BasePresenter;
import manulife.manulifesop.fragment.FAGroup.dashboardv2.campaignPercent.FACampaignPercentContract;
import manulife.manulifesop.util.Contants;
import manulife.manulifesop.util.DeviceInfo;
import manulife.manulifesop.util.SOPSharedPreferences;

/**
 * Created by Chick on 10/27/2017.
 */

public class SMCampaignPercentPresent extends BasePresenter<SMCampaignPercentContract.View> implements SMCampaignPercentContract.Action{
    Context mContext;
    public SMCampaignPercentPresent(SMCampaignPercentContract.View presenterView, Context context) {
        super(presenterView);
        this.mContext = context;
    }

    @Override
    public void updateCampaignWeek(int month, int contractW1, int contractW2, int contractW3, int contractW4) {
        mPresenterView.showLoading("Xử lý dữ liệu");
        List<Integer> dataList = new ArrayList<>();
        dataList.add(contractW1);
        dataList.add(contractW2);
        dataList.add(contractW3);
        dataList.add(contractW4);

        InputChangeCampaignWeek data = new InputChangeCampaignWeek();
        data.target = dataList;
        getCompositeDisposable().add(ApiService.getServer().changeCampaignWeek(
                SOPSharedPreferences.getInstance(mContext).getAccessToken(),
                Contants.clientID, DeviceInfo.ANDROID_OS_VERSION, BuildConfig.VERSION_NAME,
                DeviceInfo.DEVICE_NAME, DeviceInfo.DEVICEIMEI, "checksum",
                month, data)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(this::handleResponseChangeCampaignWeek, this::handleError)
        );
    }

    private void handleError(Throwable throwable) {
        mPresenterView.finishLoading(throwable.getMessage(), false);
    }

    private void handleResponseChangeCampaignWeek(BaseResponse baseResponse) {
        if (baseResponse.statusCode == 1) {
            //mPresenterView.finishLoading();
            mPresenterView.updateData();
        } else {
            mPresenterView.finishLoading(baseResponse.msg, false);
        }
    }

    @Override
    public void increaseContactCampaign(int month, int increaseNumber) {
        mPresenterView.showLoading("Xử lý dữ liệu");

        InputIncreaseContact data = new InputIncreaseContact();
        data.incrementContract = increaseNumber;
        getCompositeDisposable().add(ApiService.getServer().increaseContactCampaign(
                SOPSharedPreferences.getInstance(mContext).getAccessToken(),
                Contants.clientID, DeviceInfo.ANDROID_OS_VERSION, BuildConfig.VERSION_NAME,
                DeviceInfo.DEVICE_NAME, DeviceInfo.DEVICEIMEI, "checksum",
                month, data)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(this::handleResponseIncreaseContact, this::handleError)
        );
    }

    private void handleResponseIncreaseContact(BaseResponse baseResponse) {
        if (baseResponse.statusCode == 1) {
            //mPresenterView.finishLoading();
            mPresenterView.updateData();
        } else {
            mPresenterView.finishLoading(baseResponse.msg, false);
        }
    }
}
