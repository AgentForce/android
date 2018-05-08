package manulife.manulifesop.activity.FAGroup.personal.setting.changePass;

import android.content.Context;

import com.google.gson.Gson;

import cn.pedant.SweetAlert.SweetAlertDialog;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import manulife.manulifesop.BuildConfig;
import manulife.manulifesop.activity.FAGroup.personal.setting.SettingContract;
import manulife.manulifesop.api.ApiService;
import manulife.manulifesop.api.ObjectInput.InputChangePass;
import manulife.manulifesop.api.ObjectResponse.BaseResponse;
import manulife.manulifesop.base.BasePresenter;
import manulife.manulifesop.util.Contants;
import manulife.manulifesop.util.DeviceInfo;
import manulife.manulifesop.util.SOPSharedPreferences;
import manulife.manulifesop.util.Utils;

/**
 * Created by trinm on 12/01/2018.
 */

public class ChangePassPresenter extends BasePresenter<ChangePassContract.View> implements ChangePassContract.Action {

    private Context mContext;

    public ChangePassPresenter(ChangePassContract.View presenterView, Context context) {
        super(presenterView);
        this.mContext = context;
    }

    @Override
    public boolean validateData(String oldPass ,String pass, String passConfirm) {

        if(oldPass.length() < 1){
            mPresenterView.showMessage("Thông báo","Nhập mật khẩu cũ!", SweetAlertDialog.WARNING_TYPE);
            return false;
        }
        if(oldPass.length() < 6){
            mPresenterView.showMessage("Thông báo","Mật khẩu cũ phải đủ 6 số!", SweetAlertDialog.WARNING_TYPE);
            return false;
        }

        if(pass.length() < 1){
            mPresenterView.showMessage("Thông báo","Nhập mật khẩu mới!", SweetAlertDialog.WARNING_TYPE);
            return false;
        }
        if(pass.length() < 6){
            mPresenterView.showMessage("Thông báo","Mật khẩu mới phải đủ 6 số!", SweetAlertDialog.WARNING_TYPE);
            return false;
        }
        if(passConfirm.length() < 1){
            mPresenterView.showMessage("Thông báo","Nhập lại mật khẩu mới!", SweetAlertDialog.WARNING_TYPE);
            return false;
        }
        if(passConfirm.length() < 6){
            mPresenterView.showMessage("Thông báo","Mật khẩu nhập lại phải đủ 6 số!", SweetAlertDialog.WARNING_TYPE);
            return false;
        }
        if(!pass.equals(passConfirm)){
            mPresenterView.showMessage("Thông báo","Mật khẩu nhập lại không trùng khớp!", SweetAlertDialog.WARNING_TYPE);
            return false;
        }
        return true;
    }

    @Override
    public void changePass(String oldPass, String newPass) {
        mPresenterView.showLoading("Xử lý dữ liệu!");
        InputChangePass data = new InputChangePass();
        data.oldPassword = oldPass;
        data.newPassword = newPass;

        String checksum = Utils.getSignature(new Gson().toJson(data));

        getCompositeDisposable().add(ApiService.getServer().changePass(
                SOPSharedPreferences.getInstance(mContext).getAccessToken(),
                Contants.clientID, DeviceInfo.ANDROID_OS_VERSION, BuildConfig.VERSION_NAME,
                DeviceInfo.DEVICE_NAME, DeviceInfo.DEVICEIMEI, checksum, data)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(this::handleResponseChangePass, this::handleError));
    }

    private void handleError(Throwable throwable) {
        mPresenterView.finishLoading(throwable.getMessage(),false);
    }

    private void handleResponseChangePass(BaseResponse rs) {
        if(rs.statusCode == 1){
            mPresenterView.finishChangePass();
            mPresenterView.finishLoading();
        }else
            mPresenterView.finishLoading(rs.msg,false);
    }
}
