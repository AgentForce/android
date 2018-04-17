package manulife.manulifesop.activity.ManagerGroup.Recruitment.grantedCode;

import android.content.Context;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import manulife.manulifesop.BuildConfig;
import manulife.manulifesop.ProjectApplication;
import manulife.manulifesop.api.ApiService;
import manulife.manulifesop.api.ObjectResponse.UsersList;
import manulife.manulifesop.base.BasePresenter;
import manulife.manulifesop.util.Contants;
import manulife.manulifesop.util.DeviceInfo;
import manulife.manulifesop.util.SOPSharedPreferences;

/**
 * Created by trinm on 12/01/2018.
 */

public class GrantedCodePresenter extends BasePresenter<GrantedCodeContract.View> implements GrantedCodeContract.Action {

    private Context mContext;

    public GrantedCodePresenter(GrantedCodeContract.View presenterView, Context context) {
        super(presenterView);
        this.mContext = context;
    }

    @Override
    public void getAllData(int period) {
        mPresenterView.showLoading("Xử lý dữ liệu");
        getCompositeDisposable().add(ApiService.getServer().getUserList(
                SOPSharedPreferences.getInstance(mContext).getAccessToken(),
                Contants.clientID, DeviceInfo.ANDROID_OS_VERSION, BuildConfig.VERSION_NAME, DeviceInfo.DEVICE_NAME, DeviceInfo.DEVICEIMEI,
                period, 4,Contants.SIGNED_NOT_APPLIED,1,10,"")//khách hàng chưa nộp hồ sơ
                .flatMap(usersList -> {
                    ProjectApplication.getInstance().setSignNotApply(usersList);
                    return ApiService.getServer().getUserList(
                            SOPSharedPreferences.getInstance(mContext).getAccessToken(),
                            Contants.clientID, DeviceInfo.ANDROID_OS_VERSION, BuildConfig.VERSION_NAME, DeviceInfo.DEVICE_NAME, DeviceInfo.DEVICEIMEI,
                            period, 4,Contants.SIGNED_BHXH,1,10,"");// khách hàng hoàn tất BHXH
                })
                .flatMap(usersList -> {
                    ProjectApplication.getInstance().setSignBHXH(usersList);
                    return ApiService.getServer().getUserList(
                            SOPSharedPreferences.getInstance(mContext).getAccessToken(),
                            Contants.clientID, DeviceInfo.ANDROID_OS_VERSION, BuildConfig.VERSION_NAME, DeviceInfo.DEVICE_NAME, DeviceInfo.DEVICEIMEI,
                            period, 4,Contants.SIGNED_APPLIED,1,10,"");// khách hàng đã nộp hồ sơ
                })
                .flatMap(usersList -> {
                    ProjectApplication.getInstance().setSignApplied(usersList);
                    return ApiService.getServer().getUserList(
                            SOPSharedPreferences.getInstance(mContext).getAccessToken(),
                            Contants.clientID, DeviceInfo.ANDROID_OS_VERSION, BuildConfig.VERSION_NAME, DeviceInfo.DEVICE_NAME, DeviceInfo.DEVICEIMEI,
                            period, 4,Contants.SIGNED_WAIT_APPROVE,1,10,"");// khách hàng chờ duyệt hồ sơ
                })
                .flatMap(usersList -> {
                    ProjectApplication.getInstance().setSignWaitApprove(usersList);
                    return ApiService.getServer().getUserList(
                            SOPSharedPreferences.getInstance(mContext).getAccessToken(),
                            Contants.clientID, DeviceInfo.ANDROID_OS_VERSION, BuildConfig.VERSION_NAME, DeviceInfo.DEVICE_NAME, DeviceInfo.DEVICEIMEI,
                            period, 4,Contants.SIGNED_SUCCESS,1,10,"");// khách hàng ký thành công
                })
                .subscribeOn(Schedulers.computation())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponse, this::handleError));
    }

    private void handleError(Throwable throwable) {
        mPresenterView.finishLoading(throwable.getMessage(),false);
    }

    private void handleResponse(UsersList usersList) {
        if(usersList.statusCode==1){
            ProjectApplication.getInstance().setSignSuccess(usersList);
            mPresenterView.initViewPager();
            mPresenterView.finishLoading();
        }else{
            mPresenterView.finishLoading(usersList.msg,false);
        }
    }
}
