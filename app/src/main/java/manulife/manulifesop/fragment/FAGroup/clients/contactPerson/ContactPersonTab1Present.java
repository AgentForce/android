package manulife.manulifesop.fragment.FAGroup.clients.contactPerson;


import cn.pedant.SweetAlert.SweetAlertDialog;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import manulife.manulifesop.BuildConfig;
import manulife.manulifesop.api.ApiService;
import manulife.manulifesop.api.ObjectResponse.UsersList;
import manulife.manulifesop.base.BasePresenter;
import manulife.manulifesop.util.Contants;
import manulife.manulifesop.util.DeviceInfo;

/**
 * Created by Chick on 10/27/2017.
 */

public class ContactPersonTab1Present extends BasePresenter<ContactPersonTab1Contract.View> implements ContactPersonTab1Contract.Action{
    public ContactPersonTab1Present(ContactPersonTab1Contract.View presenterView) {
        super(presenterView);
    }

    @Override
    public void getUserListProcess(int period, int status, int page) {
        mPresenterView.showLoading("Lấy dữ liệu");
        getCompositeDisposable().add(ApiService.getServer().getUserList(
                Contants.clientID, DeviceInfo.ANDROID_OS_VERSION, BuildConfig.VERSION_NAME, DeviceInfo.DEVICE_NAME, DeviceInfo.DEVICEIMEI,
                period, 1,1,page,10)//khách hàng liên hệ
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
            mPresenterView.loadContactList(usersList);
        }else{
            mPresenterView.finishLoading(usersList.msg,false);
        }
    }
}
