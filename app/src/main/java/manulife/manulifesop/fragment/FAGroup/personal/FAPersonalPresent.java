package manulife.manulifesop.fragment.FAGroup.personal;


import android.content.Context;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import manulife.manulifesop.BuildConfig;
import manulife.manulifesop.api.ApiService;
import manulife.manulifesop.api.ObjectResponse.UserProfile;
import manulife.manulifesop.base.BasePresenter;
import manulife.manulifesop.util.Contants;
import manulife.manulifesop.util.DeviceInfo;
import manulife.manulifesop.util.SOPSharedPreferences;

/**
 * Created by Chick on 10/27/2017.
 */

public class FAPersonalPresent extends BasePresenter<FAPersonalContract.View> implements FAPersonalContract.Action{
    private Context mContext;
    public FAPersonalPresent(FAPersonalContract.View presenterView,Context context) {
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
                .subscribe(this::handleResponseGetProfile, this::handleError));
    }

    private void handleError(Throwable throwable) {
        mPresenterView.finishLoading(throwable.getMessage(),false);
    }

    private void handleResponseGetProfile(UserProfile rs) {
        if(rs.statusCode == 1){
            mPresenterView.showData(rs);
            mPresenterView.finishLoading();
        }else
            mPresenterView.finishLoading(rs.msg,false);
    }
}
