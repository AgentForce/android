package manulife.manulifesop.fragment.FAGroup.clients.related.contactDetail.step1Refuse;


import android.content.Context;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import manulife.manulifesop.BuildConfig;
import manulife.manulifesop.api.ApiService;
import manulife.manulifesop.api.ObjectInput.InputChangeContactStatus;
import manulife.manulifesop.api.ObjectResponse.BaseResponse;
import manulife.manulifesop.base.BasePresenter;
import manulife.manulifesop.fragment.FAGroup.clients.related.contactDetail.step1.ContactDetailStep1Contract;
import manulife.manulifesop.util.Contants;
import manulife.manulifesop.util.DeviceInfo;
import manulife.manulifesop.util.SOPSharedPreferences;

/**
 * Created by Chick on 10/27/2017.
 */

public class ContactDetailStep1RefusePresent extends BasePresenter<ContactDetailStep1RefuseContract.View> implements ContactDetailStep1RefuseContract.Action{

    private Context mContext;

    public ContactDetailStep1RefusePresent(ContactDetailStep1RefuseContract.View presenterView, Context context) {
        super(presenterView);
        this.mContext = context;
    }

    @Override
    public void changeStatusToOne(int leadID, boolean isChangeProcessStep, int status) {
        mPresenterView.showLoading("Xử lý dữ liệu");

        InputChangeContactStatus data = new InputChangeContactStatus();
        data.nextProcessStep = isChangeProcessStep;
        data.statusProcessStep = status;

        getCompositeDisposable().add(ApiService.getServer().changeContactStatus(
                SOPSharedPreferences.getInstance(mContext).getAccessToken(),
                Contants.clientID, DeviceInfo.ANDROID_OS_VERSION, BuildConfig.VERSION_NAME, DeviceInfo.DEVICE_NAME, DeviceInfo.DEVICEIMEI,
                "checksum",leadID,data)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleErrorr));
    }

    private void handleErrorr(Throwable throwable) {
        mPresenterView.finishLoading(throwable.getMessage(),false);
    }

    private void handleResponse(BaseResponse baseResponse) {
        if(baseResponse.statusCode == 1){
            mPresenterView.finishUpdateStatus();
            mPresenterView.finishLoading();
        }else{
            mPresenterView.finishLoading(baseResponse.msg,false);
        }
    }
}
