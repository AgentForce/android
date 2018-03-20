package manulife.manulifesop.fragment.FAGroup.clients.related.contactDetail.step3;


import android.content.Context;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import manulife.manulifesop.BuildConfig;
import manulife.manulifesop.api.ApiService;
import manulife.manulifesop.api.ObjectResponse.ContactHistory;
import manulife.manulifesop.base.BasePresenter;
import manulife.manulifesop.util.Contants;
import manulife.manulifesop.util.DeviceInfo;
import manulife.manulifesop.util.SOPSharedPreferences;

/**
 * Created by Chick on 10/27/2017.
 */

public class ContactDetailStep3Present extends BasePresenter<ContactDetailStep3Contract.View> implements ContactDetailStep3Contract.Action{
    private Context mContext;
    public ContactDetailStep3Present(ContactDetailStep3Contract.View presenterView, Context context) {
        super(presenterView);
        this.mContext = context;
    }

    @Override
    public void getContactHistory(int leadId, int page) {
        mPresenterView.showLoading("Lấy dữ liệu");
        getCompositeDisposable().add(ApiService.getServer().getContactHistory(
                SOPSharedPreferences.getInstance(mContext).getAccessToken(),
                Contants.clientID, DeviceInfo.ANDROID_OS_VERSION, BuildConfig.VERSION_NAME, DeviceInfo.DEVICE_NAME, DeviceInfo.DEVICEIMEI,
                leadId,page,10)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(this::handleResponseContactHistory, this::handleError));
    }

    private void handleError(Throwable throwable) {
        mPresenterView.finishLoading(throwable.getMessage(),false);
    }

    private void handleResponseContactHistory(ContactHistory contactHistory) {
        if(contactHistory.statusCode == 1)
        {
            mPresenterView.loadData(contactHistory);
            mPresenterView.finishLoading();
        }else{
            mPresenterView.finishLoading(contactHistory.msg,false);
        }
    }
}
