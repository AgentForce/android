package manulife.manulifesop.activity.FAGroup.personal.setting.notificationMethod;

import android.content.Context;
import android.support.v7.widget.SwitchCompat;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import manulife.manulifesop.BuildConfig;
import manulife.manulifesop.activity.FAGroup.personal.setting.SettingContract;
import manulife.manulifesop.api.ApiService;
import manulife.manulifesop.api.ObjectInput.InputChangeCampaignWeek;
import manulife.manulifesop.api.ObjectInput.InputUpdateNotiConfig;
import manulife.manulifesop.api.ObjectResponse.BaseResponse;
import manulife.manulifesop.api.ObjectResponse.NotiConfig;
import manulife.manulifesop.base.BasePresenter;
import manulife.manulifesop.util.Contants;
import manulife.manulifesop.util.DeviceInfo;
import manulife.manulifesop.util.SOPSharedPreferences;
import manulife.manulifesop.util.Utils;

/**
 * Created by trinm on 12/01/2018.
 */

public class NotiMethodPresenter extends BasePresenter<NotiMethodContract.View> implements NotiMethodContract.Action {

    private Context mContext;

    public NotiMethodPresenter(NotiMethodContract.View presenterView, Context context) {
        super(presenterView);
        this.mContext = context;
    }

    @Override
    public void getNotiConfig() {
        mPresenterView.showLoading("Lấy dữ liệu!");
        getCompositeDisposable().add(ApiService.getServer().getNotiConfig(
                SOPSharedPreferences.getInstance(mContext).getAccessToken(),
                Contants.clientID, DeviceInfo.ANDROID_OS_VERSION, BuildConfig.VERSION_NAME,
                DeviceInfo.DEVICE_NAME, DeviceInfo.DEVICEIMEI)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(this::handleResponseGetConfig, this::handleError));
    }

    private void handleError(Throwable throwable) {
        mPresenterView.finishLoading(throwable.getMessage(),false);
    }

    private void handleResponseGetConfig(NotiConfig rs) {
        if(rs.statusCode == 1){
            mPresenterView.showData(rs);
            mPresenterView.finishLoading();
        }else
            mPresenterView.finishLoading(rs.msg,false);
    }

    @Override
    public void updateNotiConfig(boolean sms, boolean email) {
        mPresenterView.showLoading("Xử lý dữ liệu");
        InputUpdateNotiConfig data = new InputUpdateNotiConfig();
        data.isEmail = (email)?1:0;
        data.isSMS = (sms)?1:0;

        String checksum = Utils.getSignature(new Gson().toJson(data));
        getCompositeDisposable().add(ApiService.getServer().updateNotiConfig(
                SOPSharedPreferences.getInstance(mContext).getAccessToken(),
                Contants.clientID, DeviceInfo.ANDROID_OS_VERSION, BuildConfig.VERSION_NAME,
                DeviceInfo.DEVICE_NAME, DeviceInfo.DEVICEIMEI, checksum,
                data)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(this::handleResponseUpdateConfig, this::handleError)
        );
    }

    private void handleResponseUpdateConfig(BaseResponse rs) {
        if(rs.statusCode == 1){
            mPresenterView.finishLoading("Cập nhật thành công",true);
        }else
            mPresenterView.finishLoading(rs.msg,false);
    }
}
