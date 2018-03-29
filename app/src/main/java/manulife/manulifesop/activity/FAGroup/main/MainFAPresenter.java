package manulife.manulifesop.activity.FAGroup.main;

import android.content.Context;

import cn.pedant.SweetAlert.SweetAlertDialog;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import manulife.manulifesop.BuildConfig;
import manulife.manulifesop.activity.FAGroup.createPlan.CreatePlanContract;
import manulife.manulifesop.api.ApiService;
import manulife.manulifesop.api.ObjectResponse.VerifyOTP;
import manulife.manulifesop.base.BasePresenter;
import manulife.manulifesop.util.Contants;
import manulife.manulifesop.util.DeviceInfo;
import manulife.manulifesop.util.SOPSharedPreferences;

/**
 * Created by trinm on 12/01/2018.
 */

public class MainFAPresenter extends BasePresenter<MainFAContract.View> implements MainFAContract.Action {

    private Context mContext;

    public MainFAPresenter(MainFAContract.View presenterView, Context context) {
        super(presenterView);
        this.mContext = context;
    }

    @Override
    public void chekCampaign() {
        getCompositeDisposable().add(ApiService.getServer().checkCampaign(
                SOPSharedPreferences.getInstance(mContext).getAccessToken(),
                Contants.clientID, DeviceInfo.ANDROID_OS_VERSION, BuildConfig.VERSION_NAME,
                DeviceInfo.DEVICE_NAME, DeviceInfo.DEVICEIMEI)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(this::handleResponseCheckCampaign, this::handleError));
    }

    private void handleResponseCheckCampaign(VerifyOTP data) {
        if (data.statusCode == 1) {
            //test
            //data.data.status = false;
            if (data.data.status) {
                mPresenterView.showDashBoard();
            } else {
                mPresenterView.showFragmentConfirmCreatePlan("Trang chủ");
            }
        }
    }

    private void handleError(Throwable throwable) {
    }
}
