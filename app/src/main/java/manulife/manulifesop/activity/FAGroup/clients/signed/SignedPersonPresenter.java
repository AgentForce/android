package manulife.manulifesop.activity.FAGroup.clients.signed;

import android.content.Context;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import manulife.manulifesop.BuildConfig;
import manulife.manulifesop.ProjectApplication;
import manulife.manulifesop.activity.FAGroup.clients.contact.ContactPersonContract;
import manulife.manulifesop.api.ApiService;
import manulife.manulifesop.api.ObjectResponse.UsersList;
import manulife.manulifesop.base.BasePresenter;
import manulife.manulifesop.util.Contants;
import manulife.manulifesop.util.DeviceInfo;
import manulife.manulifesop.util.SOPSharedPreferences;

/**
 * Created by trinm on 12/01/2018.
 */

public class SignedPersonPresenter extends BasePresenter<SignedPersonContract.View> implements SignedPersonContract.Action {

    private Context mContext;

    public SignedPersonPresenter(SignedPersonContract.View presenterView, Context context) {
        super(presenterView);
        this.mContext = context;
    }

    @Override
    public void getAllData(int period) {
        mPresenterView.showLoading("Xử lý dữ liệu");
        getCompositeDisposable().add(ApiService.getServer().getUserList(
                SOPSharedPreferences.getInstance(mContext).getAccessToken(),
                Contants.clientID, DeviceInfo.ANDROID_OS_VERSION, BuildConfig.VERSION_NAME, DeviceInfo.DEVICE_NAME, DeviceInfo.DEVICEIMEI,
                period, 4,1,1,10)//khách hàng chưa nộp hồ sơ
                .subscribeOn(Schedulers.computation())
                .unsubscribeOn(Schedulers.io())
                .flatMap(usersList -> {
                    ProjectApplication.getInstance().setSignNotApply(usersList);
                    return ApiService.getServer().getUserList(
                            SOPSharedPreferences.getInstance(mContext).getAccessToken(),
                            Contants.clientID, DeviceInfo.ANDROID_OS_VERSION, BuildConfig.VERSION_NAME, DeviceInfo.DEVICE_NAME, DeviceInfo.DEVICEIMEI,
                            period, 4,2,1,10);// khách hàng hoàn tất BHXH
                })
                .flatMap(usersList -> {
                    ProjectApplication.getInstance().setSignBHXH(usersList);
                    return ApiService.getServer().getUserList(
                            SOPSharedPreferences.getInstance(mContext).getAccessToken(),
                            Contants.clientID, DeviceInfo.ANDROID_OS_VERSION, BuildConfig.VERSION_NAME, DeviceInfo.DEVICE_NAME, DeviceInfo.DEVICEIMEI,
                            period, 4,3,1,10);// khách hàng đã nộp hồ sơ
                })
                .flatMap(usersList -> {
                    ProjectApplication.getInstance().setSignApplied(usersList);
                    return ApiService.getServer().getUserList(
                            SOPSharedPreferences.getInstance(mContext).getAccessToken(),
                            Contants.clientID, DeviceInfo.ANDROID_OS_VERSION, BuildConfig.VERSION_NAME, DeviceInfo.DEVICE_NAME, DeviceInfo.DEVICEIMEI,
                            period, 4,4,1,10);// khách hàng chờ duyệt hồ sơ
                })
                .flatMap(usersList -> {
                    ProjectApplication.getInstance().setSignWaitApprove(usersList);
                    return ApiService.getServer().getUserList(
                            SOPSharedPreferences.getInstance(mContext).getAccessToken(),
                            Contants.clientID, DeviceInfo.ANDROID_OS_VERSION, BuildConfig.VERSION_NAME, DeviceInfo.DEVICE_NAME, DeviceInfo.DEVICEIMEI,
                            period, 4,5,1,10);// khách hàng ký thành công
                })
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
