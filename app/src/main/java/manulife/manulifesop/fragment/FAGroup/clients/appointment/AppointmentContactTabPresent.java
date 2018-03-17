package manulife.manulifesop.fragment.FAGroup.clients.appointment;


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
 * Created by Chick on 10/27/2017.
 */

public class AppointmentContactTabPresent extends BasePresenter<AppointmentContactTabContract.View> implements AppointmentContactTabContract.Action{

    private Context mContext;
    private int mStatus;

    public AppointmentContactTabPresent(AppointmentContactTabContract.View presenterView,Context context) {
        super(presenterView);
        this.mContext = context;
    }

    @Override
    public void getContact(int period, int status, int page) {
        this.mStatus = status;
        mPresenterView.showLoading("Lấy dữ liệu");
        getCompositeDisposable().add(ApiService.getServer().getUserList(
                SOPSharedPreferences.getInstance(mContext).getAccessToken(),
                Contants.clientID, DeviceInfo.ANDROID_OS_VERSION, BuildConfig.VERSION_NAME, DeviceInfo.DEVICE_NAME, DeviceInfo.DEVICEIMEI,
                period, 2,status,page,10)
                .subscribeOn(Schedulers.computation())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponse, this::handleErrorr));
    }

    private void handleErrorr(Throwable throwable) {
        mPresenterView.finishLoading(throwable.getMessage(),false);
    }

    private void handleResponse(UsersList usersList) {
        if(usersList.statusCode == 1){
            switch (mStatus){
                case 1:{
                    ProjectApplication.getInstance().setAppointMentNeed(usersList);
                    break;
                }
                case 2:{
                    ProjectApplication.getInstance().setAppointMentRefuse(usersList);
                    break;
                }
                case 3:{
                    ProjectApplication.getInstance().setAppointMentCallLater(usersList);
                    break;
                }
                case 4:{
                    ProjectApplication.getInstance().setAppointMentSeen(usersList);
                    break;
                }
            }
            mPresenterView.loadDataContact();
            mPresenterView.finishLoading();
        }else{
            mPresenterView.finishLoading(usersList.msg,false);
        }
    }
}
