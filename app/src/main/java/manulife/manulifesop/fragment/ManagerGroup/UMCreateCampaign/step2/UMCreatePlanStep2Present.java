package manulife.manulifesop.fragment.ManagerGroup.UMCreateCampaign.step2;


import android.content.Context;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import manulife.manulifesop.BuildConfig;
import manulife.manulifesop.api.ApiService;
import manulife.manulifesop.api.ObjectResponse.UMForcastRecruit;
import manulife.manulifesop.base.BasePresenter;
import manulife.manulifesop.fragment.ManagerGroup.SMCreateCampaign.step2.SMCreatePlanStep2Contract;
import manulife.manulifesop.util.Contants;
import manulife.manulifesop.util.DeviceInfo;
import manulife.manulifesop.util.SOPSharedPreferences;

/**
 * Created by Chick on 10/27/2017.
 */

public class UMCreatePlanStep2Present extends BasePresenter<UMCreatePlanStep2Contract.View> implements UMCreatePlanStep2Contract.Action{
    private Context mContext;
    public UMCreatePlanStep2Present(UMCreatePlanStep2Contract.View presenterView,Context context) {
        super(presenterView);
        this.mContext = context;
    }

    @Override
    public void getUmForcastRecruit() {
        mPresenterView.showLoading("Lấy dữ liệu");
        getCompositeDisposable().add(ApiService.getServer().getUmForcastRecruit(
                SOPSharedPreferences.getInstance(mContext).getAccessToken(),
                Contants.clientID, DeviceInfo.ANDROID_OS_VERSION, BuildConfig.VERSION_NAME,
                DeviceInfo.DEVICE_NAME, DeviceInfo.DEVICEIMEI)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError));
    }

    private void handleError(Throwable throwable) {
        mPresenterView.finishLoading(throwable.getMessage(),false);
    }

    private void handleResponse(UMForcastRecruit rs) {
        if(rs.statusCode == 1){
            mPresenterView.showData(rs);
            mPresenterView.finishLoading();
        }else{
            mPresenterView.finishLoading(rs.msg,false);
        }
    }
}
