package manulife.manulifesop.activity.FAGroup.clients.appointment;

import android.content.Context;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import manulife.manulifesop.BuildConfig;
import manulife.manulifesop.ProjectApplication;
import manulife.manulifesop.api.ApiService;
import manulife.manulifesop.api.ObjectResponse.UsersList;
import manulife.manulifesop.base.BasePresenter;
import manulife.manulifesop.util.Contants;
import manulife.manulifesop.util.DeviceInfo;
import manulife.manulifesop.util.SOPSharedPreferences;

/**
 * Created by trinm on 12/01/2018.
 */

public class AppointmentPresenter extends BasePresenter<AppointmentContract.View> implements AppointmentContract.Action {

    private Context mContext;

    public AppointmentPresenter(AppointmentContract.View presenterView, Context context) {
        super(presenterView);
        this.mContext = context;
    }

    @Override
    public void loadAllData(int period) {
        mPresenterView.showLoading("Xử lý dữ liệu");
        getCompositeDisposable().add(ApiService.getServer().getUserList(
                SOPSharedPreferences.getInstance(mContext).getAccessToken(),
                Contants.clientID, DeviceInfo.ANDROID_OS_VERSION, BuildConfig.VERSION_NAME, DeviceInfo.DEVICE_NAME, DeviceInfo.DEVICEIMEI,
                period, 2,Contants.APPOINTMENT_NEED,1,10,"")//khách hàng cần hẹn gặp
                .flatMap(usersList -> {
                    ProjectApplication.getInstance().setAppointMentNeed(usersList);
                    return ApiService.getServer().getUserList(
                            SOPSharedPreferences.getInstance(mContext).getAccessToken(),
                            Contants.clientID, DeviceInfo.ANDROID_OS_VERSION, BuildConfig.VERSION_NAME, DeviceInfo.DEVICE_NAME, DeviceInfo.DEVICEIMEI,
                            period, 2,Contants.APPOINTMENT_REFUSE,1,10,"");// khách hàng từ chối
                })
                .flatMap(usersList -> {
                    ProjectApplication.getInstance().setAppointMentRefuse(usersList);
                    return ApiService.getServer().getUserList(
                            SOPSharedPreferences.getInstance(mContext).getAccessToken(),
                            Contants.clientID, DeviceInfo.ANDROID_OS_VERSION, BuildConfig.VERSION_NAME, DeviceInfo.DEVICE_NAME, DeviceInfo.DEVICEIMEI,
                            period, 2,Contants.APPOINTMENT_CALLLATER,1,10,"");// khách hàng gọi lại sau
                })
                .flatMap(usersList -> {
                    ProjectApplication.getInstance().setAppointMentCallLater(usersList);
                    return ApiService.getServer().getUserList(
                            SOPSharedPreferences.getInstance(mContext).getAccessToken(),
                            Contants.clientID, DeviceInfo.ANDROID_OS_VERSION, BuildConfig.VERSION_NAME, DeviceInfo.DEVICE_NAME, DeviceInfo.DEVICEIMEI,
                            period, 2,Contants.APPOINTMENT_SEEN,1,10,"");// khách hàng đã gặp
                })
                .subscribeOn(Schedulers.computation())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponse, this::handleError));
    }

    private void handleError(Throwable throwable) {
        mPresenterView.finishLoading(throwable.getMessage(),false);
    }

    private void handleResponse(UsersList usersList) {
        if(usersList.statusCode == 1){
            ProjectApplication.getInstance().setAppointMentSeen(usersList);
            mPresenterView.initViewPager();
            mPresenterView.finishLoading();
        }else{
            mPresenterView.finishLoading(usersList.msg,false);
        }
    }
}
