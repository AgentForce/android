package manulife.manulifesop.fragment.ManagerGroup.recruiment.personalRecruitment.ContentRecruitment;


import android.content.Context;

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
import manulife.manulifesop.api.ObjectInput.InputIncreaseAgent;
import manulife.manulifesop.api.ObjectInput.InputIncreaseContact;
import manulife.manulifesop.api.ObjectResponse.BaseResponse;
import manulife.manulifesop.api.ObjectResponse.CampaignMonth;
import manulife.manulifesop.api.ObjectResponse.CampaignRecruitMonth;
import manulife.manulifesop.base.BaseFragment;
import manulife.manulifesop.base.BasePresenter;
import manulife.manulifesop.fragment.ManagerGroup.recruiment.personalRecruitment.ContentRecruitment.ContactMonth.RecruitmentContactMonthFragment;
import manulife.manulifesop.fragment.ManagerGroup.recruiment.personalRecruitment.ContentRecruitment.ObjectMonth.RecruitmentObjectMonthFragment;
import manulife.manulifesop.fragment.ManagerGroup.recruiment.personalRecruitment.ContentRecruitment.ObjectWeek.RecruitmentObjectWeekFragment;
import manulife.manulifesop.util.Contants;
import manulife.manulifesop.util.DeviceInfo;
import manulife.manulifesop.util.SOPSharedPreferences;
import manulife.manulifesop.util.Utils;

/**
 * Created by Chick on 10/27/2017.
 */

public class PersonalRecuitmentContentPresent extends BasePresenter<PersonalRecuitmentContentContract.View> implements PersonalRecuitmentContentContract.Action {

    private Context mContext;
    private int mMonth;

    public PersonalRecuitmentContentPresent(PersonalRecuitmentContentContract.View presenterView, Context context) {
        super(presenterView);
        this.mContext = context;
    }

    @Override
    public void getCampaignMonth(int month) {
        this.mMonth = month;
        mPresenterView.showLoading("Lấy thông tin chiến dịch");
        getCompositeDisposable().add(ApiService.getServer().getContactMonthRecruitment(
                SOPSharedPreferences.getInstance(mContext).getAccessToken(),
                Contants.clientID, DeviceInfo.ANDROID_OS_VERSION, BuildConfig.VERSION_NAME,
                DeviceInfo.DEVICE_NAME, DeviceInfo.DEVICEIMEI, month,"",1,10)
                .flatMap(contactMonth -> {
                    ProjectApplication.getInstance().setContactMonth(contactMonth);
                    return ApiService.getServer().campaignRecruiMonth(
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

    private void handleResponseCampaignMonth(CampaignRecruitMonth data) {
        ProjectApplication.getInstance().setCampaignRecruit(data);
        createDataCampaignMonth(data,mMonth);
    }

    //create fragment for show data
    private void createDataCampaignMonth(CampaignRecruitMonth data,int month){
        Observable.just(data).map(CampaignMonth ->
        {
            List<BaseFragment> mListFragment = new ArrayList<>();
            mListFragment.add(RecruitmentObjectMonthFragment.newInstance(CampaignMonth, month));
            mListFragment.add(RecruitmentObjectWeekFragment.newInstance(CampaignMonth, month));
            mListFragment.add(RecruitmentContactMonthFragment.newInstance(month));
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
    public void updateCampaignWeekSM(int month, int contractW1, int contractW2, int contractW3, int contractW4) {
        mPresenterView.showLoading("Xử lý dữ liệu");
        List<Integer> dataList = new ArrayList<>();
        dataList.add(contractW1);
        dataList.add(contractW2);
        dataList.add(contractW3);
        dataList.add(contractW4);

        InputChangeCampaignWeek data = new InputChangeCampaignWeek();
        data.target = dataList;
        String checksum = Utils.getSignature(new Gson().toJson(data));
        getCompositeDisposable().add(ApiService.getServer().changeCampaignSMWeek(
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
    public void increaseAgentCampaignSM(int month, int increaseNumber) {
        mPresenterView.showLoading("Xử lý dữ liệu");
        InputIncreaseAgent data = new InputIncreaseAgent();
        data.incrementNewAgent = increaseNumber;
        String checksum = Utils.getSignature(new Gson().toJson(data));

        getCompositeDisposable().add(ApiService.getServer().increaseUMAgentCampaign(
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
