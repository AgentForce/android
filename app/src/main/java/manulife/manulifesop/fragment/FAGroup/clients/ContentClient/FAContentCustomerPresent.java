package manulife.manulifesop.fragment.FAGroup.clients.ContentClient;


import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import manulife.manulifesop.BuildConfig;
import manulife.manulifesop.api.ApiService;
import manulife.manulifesop.api.ObjectResponse.CampaignMonth;
import manulife.manulifesop.base.BasePresenter;
import manulife.manulifesop.util.Contants;
import manulife.manulifesop.util.DeviceInfo;

/**
 * Created by Chick on 10/27/2017.
 */

public class FAContentCustomerPresent extends BasePresenter<FAContentCustomerContract.View> implements FAContentCustomerContract.Action{
    public FAContentCustomerPresent(FAContentCustomerContract.View presenterView) {
        super(presenterView);
    }

    @Override
    public void getCampaignMonth(int month) {
        mPresenterView.showLoading("Lấy dữ liệu");
        getCompositeDisposable().add(ApiService.getServer().campaignMonth(Contants.clientID, DeviceInfo.ANDROID_OS_VERSION, BuildConfig.VERSION_NAME,
                DeviceInfo.DEVICE_NAME, DeviceInfo.DEVICEIMEI, month)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(this::handleResponseCampaignMonth, this::handleError)
        );
    }

    private void handleError(Throwable throwable) {
        mPresenterView.finishLoading(throwable.getMessage(), false);
    }

    private void handleResponseCampaignMonth(CampaignMonth data) {
        if(data.statusCode == 1){
            mPresenterView.showCampaignsMonth(data);
            mPresenterView.finishLoading();
        }else{
            mPresenterView.finishLoading(data.msg, false);
        }
    }
}
