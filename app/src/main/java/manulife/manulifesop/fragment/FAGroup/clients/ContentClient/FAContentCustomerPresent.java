package manulife.manulifesop.fragment.FAGroup.clients.ContentClient;


import android.content.Context;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import manulife.manulifesop.BuildConfig;
import manulife.manulifesop.ProjectApplication;
import manulife.manulifesop.api.ApiService;
import manulife.manulifesop.api.ObjectInput.InputChangeCampaignWeek;
import manulife.manulifesop.api.ObjectInput.InputIncreaseContact;
import manulife.manulifesop.api.ObjectResponse.BaseResponse;
import manulife.manulifesop.api.ObjectResponse.CampaignMonth;
import manulife.manulifesop.base.BaseFragment;
import manulife.manulifesop.base.BasePresenter;
import manulife.manulifesop.fragment.FAGroup.clients.ContentClient.ContactMonth.FAContactMonthFragment;
import manulife.manulifesop.fragment.FAGroup.clients.ContentClient.ObjectMonth.FAObjectMonthFragment;
import manulife.manulifesop.fragment.FAGroup.clients.ContentClient.ObjectWeek.FAObjectWeekFragment;
import manulife.manulifesop.util.Contants;
import manulife.manulifesop.util.DeviceInfo;
import manulife.manulifesop.util.SOPSharedPreferences;
import manulife.manulifesop.util.Utils;

/**
 * Created by Chick on 10/27/2017.
 */

public class FAContentCustomerPresent extends BasePresenter<FAContentCustomerContract.View> implements FAContentCustomerContract.Action {

    private Context mContext;
    private int mMonth;

    public FAContentCustomerPresent(FAContentCustomerContract.View presenterView, Context context) {
        super(presenterView);
        this.mContext = context;
    }

    @Override
    public void getCampaignMonth(int month) {
        this.mMonth = month;
        mPresenterView.showLoading("Lấy thông tin chiến dịch");
        getCompositeDisposable().add(ApiService.getServer().getContactMonth(
                SOPSharedPreferences.getInstance(mContext).getAccessToken(),
                Contants.clientID, DeviceInfo.ANDROID_OS_VERSION, BuildConfig.VERSION_NAME,
                DeviceInfo.DEVICE_NAME, DeviceInfo.DEVICEIMEI, month,"",1,10)
                .flatMap(contactMonth -> {
                    ProjectApplication.getInstance().setContactMonth(contactMonth);
                    return ApiService.getServer().campaignMonth(
                            SOPSharedPreferences.getInstance(mContext).getAccessToken(),
                            Contants.clientID, DeviceInfo.ANDROID_OS_VERSION, BuildConfig.VERSION_NAME,
                            DeviceInfo.DEVICE_NAME, DeviceInfo.DEVICEIMEI, month);
                })
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(this::handleResponseCampaignMonth, this::handleError)
        );
    }


    private void handleError(Throwable throwable) {
        mPresenterView.finishLoading(throwable.getMessage(), false);
    }

    private void handleResponseCampaignMonth(CampaignMonth data) {
        ProjectApplication.getInstance().setCampaign(data);
        createDataCampaignMonth(data,mMonth);
    }

    //create fragment for show data
    private void createDataCampaignMonth(CampaignMonth data,int month){
        Observable.just(data).map(CampaignMonth ->
        {
            List<BaseFragment> mListFragment = new ArrayList<>();
            mListFragment.add(FAObjectMonthFragment.newInstance(data, month));
            mListFragment.add(FAObjectWeekFragment.newInstance(data, month));
            mListFragment.add(FAContactMonthFragment.newInstance(month));
            return mListFragment;
        }).subscribeOn(Schedulers.computation())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(baseFragments -> {
           mPresenterView.showCampaignsMonth(data,baseFragments);
           if(data.statusCode == 1){
               mPresenterView.finishLoading();
           }else{
               mPresenterView.finishLoading(data.msg,false);
           }
        });
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
        String checksum = Utils.getSignature(new Gson().toJson(data));

        getCompositeDisposable().add(ApiService.getServer().changeCampaignWeek(
                SOPSharedPreferences.getInstance(mContext).getAccessToken(),
                Contants.clientID, DeviceInfo.ANDROID_OS_VERSION, BuildConfig.VERSION_NAME,
                DeviceInfo.DEVICE_NAME, DeviceInfo.DEVICEIMEI, checksum,
                month, data)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(this::handleResponseChangeCampaignWeek, this::handleError)
        );
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
        String checksum = Utils.getSignature(new Gson().toJson(data));
        getCompositeDisposable().add(ApiService.getServer().increaseContactCampaign(
                SOPSharedPreferences.getInstance(mContext).getAccessToken(),
                Contants.clientID, DeviceInfo.ANDROID_OS_VERSION, BuildConfig.VERSION_NAME,
                DeviceInfo.DEVICE_NAME, DeviceInfo.DEVICEIMEI, checksum,
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
