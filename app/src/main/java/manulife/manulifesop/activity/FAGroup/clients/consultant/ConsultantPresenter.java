package manulife.manulifesop.activity.FAGroup.clients.consultant;

import android.content.Context;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import manulife.manulifesop.BuildConfig;
import manulife.manulifesop.ProjectApplication;
import manulife.manulifesop.activity.FAGroup.clients.appointment.AppointmentContract;
import manulife.manulifesop.api.ApiService;
import manulife.manulifesop.api.ObjectResponse.UsersList;
import manulife.manulifesop.base.BasePresenter;
import manulife.manulifesop.util.Contants;
import manulife.manulifesop.util.DeviceInfo;
import manulife.manulifesop.util.SOPSharedPreferences;

/**
 * Created by trinm on 12/01/2018.
 */

public class ConsultantPresenter extends BasePresenter<ConsultantContract.View> implements ConsultantContract.Action {

    private Context mContext;

    public ConsultantPresenter(ConsultantContract.View presenterView, Context context) {
        super(presenterView);
        this.mContext = context;
    }

    @Override
    public void getAllData(int period) {
        mPresenterView.showLoading("Xử lý dữ liệu");
        getCompositeDisposable().add(ApiService.getServer().getUserList(
                SOPSharedPreferences.getInstance(mContext).getAccessToken(),
                Contants.clientID, DeviceInfo.ANDROID_OS_VERSION, BuildConfig.VERSION_NAME, DeviceInfo.DEVICE_NAME, DeviceInfo.DEVICEIMEI,
                period, 3,Contants.CONSULTANT_NEED,1,10)//khách hàng cần tư vấn
                .subscribeOn(Schedulers.computation())
                .unsubscribeOn(Schedulers.io())
                .flatMap(usersList -> {
                    ProjectApplication.getInstance().setConsultantNeed(usersList);
                    return ApiService.getServer().getUserList(
                            SOPSharedPreferences.getInstance(mContext).getAccessToken(),
                            Contants.clientID, DeviceInfo.ANDROID_OS_VERSION, BuildConfig.VERSION_NAME, DeviceInfo.DEVICE_NAME, DeviceInfo.DEVICEIMEI,
                            period, 3,Contants.CONSULTANT_REFUSE,1,10);// khách hàng từ chối
                })
                .flatMap(usersList -> {
                    ProjectApplication.getInstance().setConsultantRefuse(usersList);
                    return ApiService.getServer().getUserList(
                            SOPSharedPreferences.getInstance(mContext).getAccessToken(),
                            Contants.clientID, DeviceInfo.ANDROID_OS_VERSION, BuildConfig.VERSION_NAME, DeviceInfo.DEVICE_NAME, DeviceInfo.DEVICEIMEI,
                            period, 3,Contants.CONSULTANT_CALLLATER,1,10);// khách hàng gọi lại
                })
                .flatMap(usersList -> {
                    ProjectApplication.getInstance().setConsultantCallLater(usersList);
                    return ApiService.getServer().getUserList(
                            SOPSharedPreferences.getInstance(mContext).getAccessToken(),
                            Contants.clientID, DeviceInfo.ANDROID_OS_VERSION, BuildConfig.VERSION_NAME, DeviceInfo.DEVICE_NAME, DeviceInfo.DEVICEIMEI,
                            period, 3,Contants.CONSULTANT_SEEN,1,10);// khách hàng đã tư vấn
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponse, this::handleError));
    }

    private void handleError(Throwable throwable) {
        mPresenterView.finishLoading(throwable.getMessage(),false);
    }

    private void handleResponse(UsersList usersList) {
        if(usersList.statusCode == 1){
            ProjectApplication.getInstance().setConsultantSeen(usersList);
            mPresenterView.initViewPager();
            mPresenterView.finishLoading();
        }else{
            mPresenterView.finishLoading(usersList.msg,false);
        }
    }
}
