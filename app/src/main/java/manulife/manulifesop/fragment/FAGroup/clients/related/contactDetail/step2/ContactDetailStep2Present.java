package manulife.manulifesop.fragment.FAGroup.clients.related.contactDetail.step2;


import android.content.Context;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import manulife.manulifesop.BuildConfig;
import manulife.manulifesop.api.ApiService;
import manulife.manulifesop.api.ObjectResponse.BaseResponse;
import manulife.manulifesop.api.ObjectResponse.ContactActivity;
import manulife.manulifesop.base.BasePresenter;
import manulife.manulifesop.util.Contants;
import manulife.manulifesop.util.DeviceInfo;
import manulife.manulifesop.util.SOPSharedPreferences;
import manulife.manulifesop.util.Utils;

/**
 * Created by Chick on 10/27/2017.
 */

public class ContactDetailStep2Present extends BasePresenter<ContactDetailStep2Contract.View> implements ContactDetailStep2Contract.Action{
    private Context mContext;
    public ContactDetailStep2Present(ContactDetailStep2Contract.View presenterView,Context context) {
        super(presenterView);
        this.mContext = context;
    }

    @Override
    public void getEvents(int leadID) {
        mPresenterView.showLoading("Lấy dữ liệu");
        getCompositeDisposable().add(ApiService.getServer().getContactActivity(
                SOPSharedPreferences.getInstance(mContext).getAccessToken(),
                Contants.clientID, DeviceInfo.ANDROID_OS_VERSION, BuildConfig.VERSION_NAME, DeviceInfo.DEVICE_NAME, DeviceInfo.DEVICEIMEI,
                leadID)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError));
    }

    private void handleError(Throwable throwable) {
        mPresenterView.finishLoading(throwable.getMessage(),false);
    }

    private void handleResponse(ContactActivity contactActivity) {
        if (contactActivity.statusCode == 1){
            mPresenterView.loadContactActivities(contactActivity);
            mPresenterView.finishLoading();
        }else
        {
            mPresenterView.finishLoading(contactActivity.msg,false);
        }
    }

    @Override
    public void updateEventDone(int eventID) {
        mPresenterView.showLoading("Cập nhật sự kiện");
        String checksum = Utils.getSignature("");
        getCompositeDisposable().add(ApiService.getServer().updateEventDone(
                SOPSharedPreferences.getInstance(mContext).getAccessToken(),
                Contants.clientID, DeviceInfo.ANDROID_OS_VERSION, BuildConfig.VERSION_NAME, DeviceInfo.DEVICE_NAME, DeviceInfo.DEVICEIMEI,
                checksum,eventID)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(this::handleResponseUpdateEventDone, this::handleError));
    }

    private void handleResponseUpdateEventDone(BaseResponse rs) {
        if(rs.statusCode == 1){
            mPresenterView.updateData();
            mPresenterView.finishLoading();
        }else{
            mPresenterView.finishLoading(rs.msg,false);
        }
    }
}
