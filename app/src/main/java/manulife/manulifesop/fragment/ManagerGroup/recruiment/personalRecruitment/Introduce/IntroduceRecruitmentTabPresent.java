package manulife.manulifesop.fragment.ManagerGroup.recruiment.personalRecruitment.Introduce;


import android.content.Context;

import com.google.gson.Gson;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import manulife.manulifesop.BuildConfig;
import manulife.manulifesop.api.ApiService;
import manulife.manulifesop.api.ObjectInput.InputIntroduceContact;
import manulife.manulifesop.api.ObjectResponse.BaseResponse;
import manulife.manulifesop.api.ObjectResponse.UsersList;
import manulife.manulifesop.base.BasePresenter;
import manulife.manulifesop.util.Contants;
import manulife.manulifesop.util.DeviceInfo;
import manulife.manulifesop.util.SOPSharedPreferences;
import manulife.manulifesop.util.Utils;

/**
 * Created by Chick on 10/27/2017.
 */

public class IntroduceRecruitmentTabPresent extends BasePresenter<IntroduceRecruitmentTabContract.View> implements IntroduceRecruitmentTabContract.Action {
    private Context mContext;

    public IntroduceRecruitmentTabPresent(IntroduceRecruitmentTabContract.View presenterView, Context context) {
        super(presenterView);
        this.mContext = context;
    }

    @Override
    public void getUserListProcess(String search,int period, int page) {
        mPresenterView.showLoading("Lấy dữ liệu");
        getCompositeDisposable().add(ApiService.getServer().getIntorduceUserList(
                SOPSharedPreferences.getInstance(mContext).getAccessToken(),
                Contants.clientID, DeviceInfo.ANDROID_OS_VERSION, BuildConfig.VERSION_NAME, DeviceInfo.DEVICE_NAME, DeviceInfo.DEVICEIMEI,
                period,page, 10,search)//khách hàng giới thiệu
                .subscribeOn(Schedulers.computation())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponse, this::handleError));
    }

    private void handleError(Throwable throwable) {
        mPresenterView.finishLoading(throwable.getMessage(), false);
    }

    private void handleResponse(UsersList usersList) {
        if (usersList.statusCode == 1) {
            mPresenterView.loadContactList(usersList);
            mPresenterView.finishLoading();
        } else {
            mPresenterView.finishLoading(usersList.msg, false);
        }
    }

    @Override
    public void addIntroduceRecruit(String phone, String name, int campaignID) {
        mPresenterView.showLoading("Xử lý dữ liệu");

        InputIntroduceContact data = new InputIntroduceContact(phone, name, campaignID);

        String checksum = Utils.getSignature(new Gson().toJson(data));

        getCompositeDisposable().add(ApiService.getServer().addIntroduceRecruit(
                SOPSharedPreferences.getInstance(mContext).getAccessToken(),
                Contants.clientID, DeviceInfo.ANDROID_OS_VERSION, BuildConfig.VERSION_NAME, DeviceInfo.DEVICE_NAME, DeviceInfo.DEVICEIMEI,
                checksum, data)
                .subscribeOn(Schedulers.computation())
                .unsubscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponseAddIntroduce, this::handleError));
    }

    private void handleResponseAddIntroduce(BaseResponse baseResponse) {
        if (baseResponse.statusCode == 1) {
            mPresenterView.finishLoading();
            mPresenterView.reloadData();
        } else {
            mPresenterView.finishLoading(baseResponse.msg, false);
        }
    }
}
