package manulife.manulifesop.activity.FAGroup.clients.contact;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import manulife.manulifesop.BuildConfig;
import manulife.manulifesop.adapter.ObjectData.ActiveHistFA;
import manulife.manulifesop.api.ApiService;
import manulife.manulifesop.api.ObjectResponse.UsersList;
import manulife.manulifesop.base.BasePresenter;
import manulife.manulifesop.util.Contants;
import manulife.manulifesop.util.DeviceInfo;

/**
 * Created by trinm on 12/01/2018.
 */

public class ContactPersonPresenter extends BasePresenter<ContactPersonContract.View> implements ContactPersonContract.Action {

    private Context mContext;

    private UsersList mContact;

    public ContactPersonPresenter(ContactPersonContract.View presenterView, Context context) {
        super(presenterView);
        this.mContext = context;
    }

    @Override
    public void getAllContactPerson(int period, final int page) {
        mPresenterView.showLoading("Xử lý dữ liệu");
        getCompositeDisposable().add(ApiService.getServer().getUserList(
                Contants.clientID, DeviceInfo.ANDROID_OS_VERSION, BuildConfig.VERSION_NAME, DeviceInfo.DEVICE_NAME, DeviceInfo.DEVICEIMEI,
                period, 1,1,page,10)//khách hàng liên hệ
                .subscribeOn(Schedulers.computation())
                .unsubscribeOn(Schedulers.io())
                .flatMap(usersList -> {
                    this.mContact = usersList;
                    return ApiService.getServer().getUserList(
                            Contants.clientID, DeviceInfo.ANDROID_OS_VERSION, BuildConfig.VERSION_NAME, DeviceInfo.DEVICE_NAME, DeviceInfo.DEVICEIMEI,
                            period, 1,2,page,10);// khách hàng gọi lại sau
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponse, this::handleError));
    }

    private void handleError(Throwable throwable) {
        mPresenterView.finishLoading(throwable.getMessage(),false);
    }

    private void handleResponse(UsersList usersList) {
        if(usersList.statusCode == 1){
            mPresenterView.initViewPager(mContact,usersList);
            mPresenterView.finishLoading();
        }else{
            mPresenterView.finishLoading(usersList.msg,false);
        }
    }
}
