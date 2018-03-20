package manulife.manulifesop.activity.FAGroup.clients.related.contactDetail;

import android.content.Context;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import manulife.manulifesop.BuildConfig;
import manulife.manulifesop.ProjectApplication;
import manulife.manulifesop.api.ApiService;
import manulife.manulifesop.api.ObjectInput.InputChangeContactStatus;
import manulife.manulifesop.api.ObjectResponse.BaseResponse;
import manulife.manulifesop.api.ObjectResponse.ContactActivity;
import manulife.manulifesop.api.ObjectResponse.ContactDetail;
import manulife.manulifesop.api.ObjectResponse.ContactHistory;
import manulife.manulifesop.base.BasePresenter;
import manulife.manulifesop.util.Contants;
import manulife.manulifesop.util.DeviceInfo;
import manulife.manulifesop.util.SOPSharedPreferences;

/**
 * Created by trinm on 12/01/2018.
 */

public class ContactDetailPresenter extends BasePresenter<ContactDetailContract.View> implements ContactDetailContract.Action {

    private Context mContext;

    public ContactDetailPresenter(ContactDetailContract.View presenterView, Context context) {
        super(presenterView);
        this.mContext = context;
    }

    @Override
    public void getContactDetail(int id) {
        mPresenterView.showLoading("Xử lý dữ liệu");

        getCompositeDisposable().add(ApiService.getServer().getContactDetail(
                SOPSharedPreferences.getInstance(mContext).getAccessToken(),
                Contants.clientID, DeviceInfo.ANDROID_OS_VERSION, BuildConfig.VERSION_NAME, DeviceInfo.DEVICE_NAME, DeviceInfo.DEVICEIMEI,
                id)
                .subscribeOn(Schedulers.computation())
                .flatMap(contactDetail -> {
                    ProjectApplication.getInstance().setContactDetail(contactDetail);
                    return ApiService.getServer().getContactActivity(
                            SOPSharedPreferences.getInstance(mContext).getAccessToken(),
                            Contants.clientID, DeviceInfo.ANDROID_OS_VERSION, BuildConfig.VERSION_NAME, DeviceInfo.DEVICE_NAME, DeviceInfo.DEVICEIMEI,
                            id);
                })
                .flatMap(contactActivity -> {
                    ProjectApplication.getInstance().setContactActivity(contactActivity);
                    return ApiService.getServer().getContactHistory(
                            SOPSharedPreferences.getInstance(mContext).getAccessToken(),
                            Contants.clientID, DeviceInfo.ANDROID_OS_VERSION, BuildConfig.VERSION_NAME, DeviceInfo.DEVICE_NAME, DeviceInfo.DEVICEIMEI,
                            id,1,10);
                })
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(this::handleResponseContactDetail, this::handleError));
    }

    private void handleResponseContactDetail(ContactHistory contactHistory) {
        if(contactHistory.statusCode == 1) {
            ProjectApplication.getInstance().setContactHistory(contactHistory);
            mPresenterView.initViewPager();
            mPresenterView.finishLoading();
        }else
        {
            mPresenterView.finishLoading(contactHistory.msg,false);
        }
    }


    private void handleError(Throwable throwable) {
        mPresenterView.finishLoading(throwable.getMessage(),false);
    }

    @Override
    public void updateStatusProcess(int leadID,boolean isChangeStep, int changeToStatus) {
        mPresenterView.showLoading("Xử lý dữ liệu");

        InputChangeContactStatus data = new InputChangeContactStatus();
        data.nextProcessStep = isChangeStep;
        data.statusProcessStep = changeToStatus;

        getCompositeDisposable().add(ApiService.getServer().changeContactStatus(
                SOPSharedPreferences.getInstance(mContext).getAccessToken(),
                Contants.clientID, DeviceInfo.ANDROID_OS_VERSION, BuildConfig.VERSION_NAME, DeviceInfo.DEVICE_NAME, DeviceInfo.DEVICEIMEI,
                "checksum",leadID,data)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(this::handleResponseChangeStatus, this::handleErrorr));
    }

    private void handleErrorr(Throwable throwable) {
        mPresenterView.finishLoading(throwable.getMessage(),false);
    }

    private void handleResponseChangeStatus(BaseResponse baseResponse) {
        if(baseResponse.statusCode == 1){
            mPresenterView.finishChangeStatus();
            mPresenterView.finishLoading();
        }else {
            mPresenterView.finishLoading(baseResponse.msg,false);
        }
    }
}
