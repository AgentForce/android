package manulife.manulifesop.activity.FAGroup.confirmCreatePlan;

import android.content.Context;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import manulife.manulifesop.BuildConfig;
import manulife.manulifesop.activity.FAGroup.createPlan.CreatePlanContract;
import manulife.manulifesop.api.ApiService;
import manulife.manulifesop.api.ObjectResponse.UserProfile;
import manulife.manulifesop.base.BasePresenter;
import manulife.manulifesop.util.Contants;
import manulife.manulifesop.util.DeviceInfo;
import manulife.manulifesop.util.SOPSharedPreferences;

/**
 * Created by trinm on 12/01/2018.
 */

public class ConfirmCreatePlanPresenter extends BasePresenter<ConfirmCreatePlanContract.View> implements ConfirmCreatePlanContract.Action {

    private Context mContext;

    public ConfirmCreatePlanPresenter(ConfirmCreatePlanContract.View presenterView, Context context) {
        super(presenterView);
        this.mContext = context;
    }

    @Override
    public void getUserProfile(String userName) {
        mPresenterView.showLoading("Lấy dữ liệu");
        getCompositeDisposable().add(ApiService.getServer().getUserProfile(
                SOPSharedPreferences.getInstance(mContext).getAccessToken(),
                Contants.clientID, DeviceInfo.ANDROID_OS_VERSION, BuildConfig.VERSION_NAME,
                DeviceInfo.DEVICE_NAME, DeviceInfo.DEVICEIMEI,userName)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError));
    }

    private void handleError(Throwable throwable) {
        mPresenterView.finishLoading(throwable.getMessage(),false);
    }

    private void handleResponse(UserProfile userProfile) {
        if(userProfile.statusCode == 1){
            mPresenterView.showData(userProfile);
            mPresenterView.finishLoading();
        }else
        {
            mPresenterView.finishLoading(userProfile.msg,false);
        }
    }
}
