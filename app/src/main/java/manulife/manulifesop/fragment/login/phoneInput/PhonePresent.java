package manulife.manulifesop.fragment.login.phoneInput;


import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import manulife.manulifesop.BuildConfig;
import manulife.manulifesop.api.ApiService;
import manulife.manulifesop.api.ObjectResponse.CheckUser;
import manulife.manulifesop.base.BasePresenter;
import manulife.manulifesop.fragment.login.agencyInput.AgencyContract;
import manulife.manulifesop.util.Contants;
import manulife.manulifesop.util.DeviceInfo;
import manulife.manulifesop.util.Utils;

/**
 * Created by Chick on 10/27/2017.
 */

public class PhonePresent extends BasePresenter<PhoneContract.View> implements PhoneContract.Action {
    public PhonePresent(PhoneContract.View presenterView) {
        super(presenterView);
    }

    @Override
    public void checkUser(String user, String phone) {

        mPresenterView.showLoading("Kiểm tra trạng thái user!");

        String checksum = Utils.getSignature(phone + user);

        getCompositeDisposable().add(ApiService.getServer().checkUser(
                Contants.clientID, DeviceInfo.ANDROID_OS_VERSION, BuildConfig.VERSION_NAME, DeviceInfo.DEVICE_NAME, DeviceInfo.DEVICEIMEI,
                checksum, phone, user)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(this::handleResponseCheckUser, this::handleError));
    }

    private void handleError(Throwable throwable) {
        mPresenterView.finishLoading(throwable.getMessage(),false);
    }

    private void handleResponseCheckUser(CheckUser checkUser) {
        if (checkUser.statusCode == 200)//thành công
        {
            switch (checkUser.data.status){
                case 1: {//user chưa active
                    mPresenterView.finishLoading();
                    mPresenterView.showFragmentOTPInput();
                    break;
                }
                case 5:{//user đã active
                    mPresenterView.finishLoading();
                    mPresenterView.showFragmentPassInput();
                    break;
                }
                default:{
                    mPresenterView.finishLoading(checkUser.msg,false);
                }
            }

        } else {
            mPresenterView.finishLoading(checkUser.msg, false);
        }
    }
}
