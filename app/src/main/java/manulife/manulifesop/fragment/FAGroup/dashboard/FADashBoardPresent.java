package manulife.manulifesop.fragment.FAGroup.dashboard;


import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import manulife.manulifesop.BuildConfig;
import manulife.manulifesop.api.ApiService;
import manulife.manulifesop.api.ObjectResponse.ActivitiHist;
import manulife.manulifesop.api.ObjectResponse.DashboardResult;
import manulife.manulifesop.base.BasePresenter;
import manulife.manulifesop.util.Contants;
import manulife.manulifesop.util.DeviceInfo;

/**
 * Created by Chick on 10/27/2017.
 */

public class FADashBoardPresent extends BasePresenter<FADashBoardContract.View> implements FADashBoardContract.Action {

    private DashboardResult mDataDashboardWeekMonth;
    private DashboardResult mDataDashboardYear;

    public FADashBoardPresent(FADashBoardContract.View presenterView) {
        super(presenterView);
    }

    @Override
    public void getDataDashboard() {
        mPresenterView.showLoading("Xử lý dữ liệu!");
        getCompositeDisposable().add(ApiService.getServer().dashBoard(
                Contants.clientID, DeviceInfo.ANDROID_OS_VERSION, BuildConfig.VERSION_NAME,
                DeviceInfo.DEVICE_NAME, DeviceInfo.DEVICEIMEI, "weekmonth")
                .subscribeOn(Schedulers.computation())
                .flatMap(dashboardResult -> {
                    this.mDataDashboardWeekMonth = dashboardResult;
                    return ApiService.getServer().dashBoard(
                            Contants.clientID, DeviceInfo.ANDROID_OS_VERSION, BuildConfig.VERSION_NAME,
                            DeviceInfo.DEVICE_NAME, DeviceInfo.DEVICEIMEI, "year");
                })
                .flatMap(dashboardResult -> {
                    this.mDataDashboardYear = dashboardResult;
                    return ApiService.getServer().activities(
                            Contants.clientID, DeviceInfo.ANDROID_OS_VERSION, BuildConfig.VERSION_NAME,
                            DeviceInfo.DEVICE_NAME, DeviceInfo.DEVICEIMEI, 1, 10);
                })
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(this::handleResponseDashboard, this::handleError));
    }

    private void handleError(Throwable throwable) {
        mPresenterView.finishLoading(throwable.getMessage(), false);
    }

    private void handleResponseDashboard(ActivitiHist data) {
        if (data.statusCode == 1) {
            mPresenterView.showDataDashboard(mDataDashboardWeekMonth, mDataDashboardYear, data);
            mPresenterView.finishLoading();
        } else {
            mPresenterView.finishLoading(data.msg, false);
        }
    }

    @Override
    public void getActivities(int page) {
        getCompositeDisposable().add(ApiService.getServer().activities(Contants.clientID, DeviceInfo.ANDROID_OS_VERSION, BuildConfig.VERSION_NAME,
                DeviceInfo.DEVICE_NAME, DeviceInfo.DEVICEIMEI, page, 10)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(this::handleResponseActivity, this::handleError)
        );
    }

    private void handleResponseActivity(ActivitiHist activitiHist) {
        if (activitiHist.statusCode == 1) {
            mPresenterView.showACtivities(activitiHist);
            mPresenterView.finishLoading();
        }else{
            mPresenterView.finishLoading(activitiHist.msg, false);
        }
    }
}
