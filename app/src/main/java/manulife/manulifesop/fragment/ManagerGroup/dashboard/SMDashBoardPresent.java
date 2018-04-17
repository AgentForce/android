package manulife.manulifesop.fragment.ManagerGroup.dashboard;


import android.content.Context;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import manulife.manulifesop.BuildConfig;
import manulife.manulifesop.ProjectApplication;
import manulife.manulifesop.api.ApiService;
import manulife.manulifesop.api.ObjectResponse.ActivitiHist;
import manulife.manulifesop.api.ObjectResponse.BaseResponse;
import manulife.manulifesop.api.ObjectResponse.DashboardResult;
import manulife.manulifesop.api.ObjectResponse.DashboardSMResult;
import manulife.manulifesop.api.ObjectResponse.RecruitHistory;
import manulife.manulifesop.base.BasePresenter;
import manulife.manulifesop.fragment.FAGroup.dashboardv2.FADashBoardContract;
import manulife.manulifesop.util.Contants;
import manulife.manulifesop.util.DeviceInfo;
import manulife.manulifesop.util.SOPSharedPreferences;
import manulife.manulifesop.util.Utils;

/**
 * Created by Chick on 10/27/2017.
 */

public class SMDashBoardPresent extends BasePresenter<SMDashBoardContract.View> implements SMDashBoardContract.Action {

    private DashboardSMResult mDataDashboardWeekMonth;
    private DashboardSMResult mDataDashboardYear;
    private Context mContext;

    public SMDashBoardPresent(SMDashBoardContract.View presenterView, Context context) {
        super(presenterView);
        this.mContext = context;
    }

    @Override
    public void getDataDashboard() {
        mPresenterView.showLoading("Lấy dữ liệu");
        getCompositeDisposable().add(ApiService.getServer().dashBoardSM(
                SOPSharedPreferences.getInstance(mContext).getAccessToken(),
                Contants.clientID, DeviceInfo.ANDROID_OS_VERSION, BuildConfig.VERSION_NAME,
                DeviceInfo.DEVICE_NAME, DeviceInfo.DEVICEIMEI, "weekmonth")
                .flatMap(dashboardResult -> {
                    this.mDataDashboardWeekMonth = dashboardResult;
                    return ApiService.getServer().dashBoardSM(
                            SOPSharedPreferences.getInstance(mContext).getAccessToken(),
                            Contants.clientID, DeviceInfo.ANDROID_OS_VERSION, BuildConfig.VERSION_NAME,
                            DeviceInfo.DEVICE_NAME, DeviceInfo.DEVICEIMEI, "year");
                })
                .flatMap(dashboardResult -> {
                    this.mDataDashboardYear = dashboardResult;
                    return ApiService.getServer().campaignRecruiMonth(
                            SOPSharedPreferences.getInstance(mContext).getAccessToken(),
                            Contants.clientID, DeviceInfo.ANDROID_OS_VERSION, BuildConfig.VERSION_NAME,
                            DeviceInfo.DEVICE_NAME, DeviceInfo.DEVICEIMEI, Utils.getCurrentMonth(mContext));
                })
                .flatMap(campaignMonth -> {
                    ProjectApplication.getInstance().setCampaignRecruit(campaignMonth);
                    return ApiService.getServer().recruitHistory(
                            SOPSharedPreferences.getInstance(mContext).getAccessToken(),
                            Contants.clientID, DeviceInfo.ANDROID_OS_VERSION, BuildConfig.VERSION_NAME,
                            DeviceInfo.DEVICE_NAME, DeviceInfo.DEVICEIMEI, 1, 10);
                })
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(this::handleResponseDashboard, this::handleError));
    }

    private void handleError(Throwable throwable) {
        mPresenterView.finishLoading(throwable.getMessage(), false);
    }

    private void handleResponseDashboard(RecruitHistory data) {
        if (data.statusCode == 1) {
            mPresenterView.finishLoadingMulti();
            mPresenterView.showDataDashboard(mDataDashboardWeekMonth, mDataDashboardYear, data);
        } else {
            mPresenterView.finishLoading(data.msg, false);
        }
    }

    @Override
    public void getActivities(int page) {
        getCompositeDisposable().add(ApiService.getServer().recruitHistory(
                SOPSharedPreferences.getInstance(mContext).getAccessToken(),
                Contants.clientID, DeviceInfo.ANDROID_OS_VERSION, BuildConfig.VERSION_NAME,
                DeviceInfo.DEVICE_NAME, DeviceInfo.DEVICEIMEI, page, 10)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(this::handleResponseActivity, this::handleError)
        );
    }

    private void handleResponseActivity(RecruitHistory data) {
        if (data.statusCode == 1) {
            mPresenterView.showACtivities(data);
            mPresenterView.finishLoading();
        }else{
            mPresenterView.finishLoading(data.msg, false);
        }
    }

    @Override
    public void forwardCampaign() {
        mPresenterView.showLoading("Xử lý dữ liệu!");
        getCompositeDisposable().add(ApiService.getServer().forwardTarget(
                SOPSharedPreferences.getInstance(mContext).getAccessToken(),
                Contants.clientID, DeviceInfo.ANDROID_OS_VERSION, BuildConfig.VERSION_NAME,
                DeviceInfo.DEVICE_NAME, DeviceInfo.DEVICEIMEI)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(this::handleResponseForwardStarget, this::handleError)
        );
    }

    private void handleResponseForwardStarget(BaseResponse baseResponse) {
        if(baseResponse.statusCode == 1){
            mPresenterView.finishLoading("Chuyển mục tiêu thành công",true);
        }else{
            mPresenterView.finishLoading(baseResponse.msg,false);
        }
    }
}
