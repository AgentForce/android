package manulife.manulifesop.activity.main;

import android.content.Context;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import manulife.manulifesop.BuildConfig;
import manulife.manulifesop.api.ApiService;
import manulife.manulifesop.api.ObjectInput.InputRefreshToken;
import manulife.manulifesop.api.ObjectResponse.RefreshToken;
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
        mPresenterView.showLoading("Lấy dữ liệu");
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
                mPresenterView.finishLoading();
            }
            //mPresenterView.finishLoading();
        }else if(data.statusCode == -1){
            //refresh token
            refreshAccessToken();
        }else{
            mPresenterView.finishLoading(data.msg,false);
        }
    }

    private void handleError(Throwable throwable) {
        mPresenterView.finishLoading(throwable.getMessage(),false);
    }

    @Override
    public void refreshAccessToken() {
        InputRefreshToken data = new InputRefreshToken();
        data.refreshToken = SOPSharedPreferences.getInstance(mContext).getRefreshToken();
        getCompositeDisposable().add(ApiService.getServer().refreshToken(
                Contants.clientID, DeviceInfo.ANDROID_OS_VERSION, BuildConfig.VERSION_NAME,
                DeviceInfo.DEVICE_NAME, DeviceInfo.DEVICEIMEI,"checksum",
                data)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(this::handleResponseRefreshToken, this::handleError));
    }

    private void handleResponseRefreshToken(RefreshToken rs) {
        if(rs.statusCode == 1){
            SOPSharedPreferences.getInstance(mContext).saveTokenUser(rs.data.accessToken,rs.data.refreshToken);
            chekCampaign();
        }else{
            mPresenterView.finishLoading();
            mPresenterView.showLogin();
        }
    }
}
