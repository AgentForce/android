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
        getCompositeDisposable().add(ApiService.getServer().getUserListRecruit(
                SOPSharedPreferences.getInstance(mContext).getAccessToken(),
                Contants.clientID, DeviceInfo.ANDROID_OS_VERSION, BuildConfig.VERSION_NAME, DeviceInfo.DEVICE_NAME, DeviceInfo.DEVICEIMEI,
                period, 4,Contants.CODE_CHANGED_TO_APPLY,1,10,"")//đã chuyển sang nộp hờ sơ đại lý
                .flatMap(usersList -> {
                    ProjectApplication.getInstance().setCodeAdded(usersList);
                    return ApiService.getServer().getUserListRecruit(
                            SOPSharedPreferences.getInstance(mContext).getAccessToken(),
                            Contants.clientID, DeviceInfo.ANDROID_OS_VERSION, BuildConfig.VERSION_NAME, DeviceInfo.DEVICE_NAME, DeviceInfo.DEVICEIMEI,
                            period, 4,Contants.CODE_APPLIED_DOCUMENT_AGENT,1,10,"");// hoàn tất nộp hồ sơ đại lý
                })
                .flatMap(usersList -> {
                    ProjectApplication.getInstance().setCodeAppliedAgent(usersList);
                    return ApiService.getServer().getUserListRecruit(
                            SOPSharedPreferences.getInstance(mContext).getAccessToken(),
                            Contants.clientID, DeviceInfo.ANDROID_OS_VERSION, BuildConfig.VERSION_NAME, DeviceInfo.DEVICE_NAME, DeviceInfo.DEVICEIMEI,
                            period, 4,Contants.CODE_APPLIED_DONE,1,10,"");// đã nộp hồ sơ
                })
                .flatMap(usersList -> {
                    ProjectApplication.getInstance().setCodeAppliedDone(usersList);
                    return ApiService.getServer().getUserListRecruit(
                            SOPSharedPreferences.getInstance(mContext).getAccessToken(),
                            Contants.clientID, DeviceInfo.ANDROID_OS_VERSION, BuildConfig.VERSION_NAME, DeviceInfo.DEVICE_NAME, DeviceInfo.DEVICEIMEI,
                            period, 4,Contants.CODE_WAITING_APPROVE,1,10,"");// Chờ duyệt hồ sơ
                })
                .flatMap(usersList -> {
                    ProjectApplication.getInstance().setCodeWaitApprove(usersList);
                    return ApiService.getServer().getUserListRecruit(
                            SOPSharedPreferences.getInstance(mContext).getAccessToken(),
                            Contants.clientID, DeviceInfo.ANDROID_OS_VERSION, BuildConfig.VERSION_NAME, DeviceInfo.DEVICE_NAME, DeviceInfo.DEVICEIMEI,
                            period, 4,Contants.CODE_GRANTED_CODE,1,10,"");// cấp mã thành công
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
            ProjectApplication.getInstance().setCodeGranted(usersList);
            mPresenterView.initViewPager();
            mPresenterView.finishLoading();
        }else{
            mPresenterView.finishLoading(usersList.msg,false);
        }
    }
}
