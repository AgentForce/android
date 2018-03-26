package manulife.manulifesop.fragment.FAGroup.clients.ContentClient;


import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import manulife.manulifesop.BuildConfig;
import manulife.manulifesop.ProjectApplication;
import manulife.manulifesop.api.ApiService;
import manulife.manulifesop.api.ObjectInput.InputChangeCampaignWeek;
import manulife.manulifesop.api.ObjectResponse.BaseResponse;
import manulife.manulifesop.api.ObjectResponse.CampaignMonth;
import manulife.manulifesop.base.BasePresenter;
import manulife.manulifesop.util.Contants;
import manulife.manulifesop.util.DeviceInfo;
import manulife.manulifesop.util.SOPSharedPreferences;

/**
 * Created by Chick on 10/27/2017.
 */

public class FAContentCustomerPresent extends BasePresenter<FAContentCustomerContract.View> implements FAContentCustomerContract.Action {

    private Context mContext;

    public FAContentCustomerPresent(FAContentCustomerContract.View presenterView, Context context) {
        super(presenterView);
        this.mContext = context;
    }

    @Override
    public void getCampaignMonth(int month) {
        mPresenterView.showLoading("Lấy dữ liệu");
        getCompositeDisposable().add(ApiService.getServer().campaignMonth(
                SOPSharedPreferences.getInstance(mContext).getAccessToken(),
                Contants.clientID, DeviceInfo.ANDROID_OS_VERSION, BuildConfig.VERSION_NAME,
                DeviceInfo.DEVICE_NAME, DeviceInfo.DEVICEIMEI, month)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(this::handleResponseCampaignMonth, this::handleError)
        );
    }

    private void handleError(Throwable throwable) {
        mPresenterView.finishLoading(throwable.getMessage(), false);
    }

    private void handleResponseCampaignMonth(CampaignMonth data) {
        if (data.statusCode == 1) {
            ProjectApplication.getInstance().setCampaign(data);
            mPresenterView.showCampaignsMonth(data);
            mPresenterView.finishLoading();
        } else {
            if (data.statusCode == 2) {
                mPresenterView.showConfirmAcvitveCampaign();
            } else
                mPresenterView.finishLoading(data.msg, false);
        }
    }

    @Override
    public void updateCampaignWeek(int month, int contractW1, int contractW2, int contractW3, int contractW4) {
        mPresenterView.showLoading("Xử lý dữ liệu");
        List<Integer> dataList = new ArrayList<>();
        dataList.add(contractW1);
        dataList.add(contractW2);
        dataList.add(contractW3);
        dataList.add(contractW4);

        InputChangeCampaignWeek data = new InputChangeCampaignWeek();
        data.target =dataList;
        getCompositeDisposable().add(ApiService.getServer().changeCampaignWeek(
                SOPSharedPreferences.getInstance(mContext).getAccessToken(),
                Contants.clientID, DeviceInfo.ANDROID_OS_VERSION, BuildConfig.VERSION_NAME,
                DeviceInfo.DEVICE_NAME, DeviceInfo.DEVICEIMEI, "checksum",
                month,data)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(this::handleResponseChangeCampaignWeek, this::handleError)
        );
    }

    private void handleResponseChangeCampaignWeek(BaseResponse baseResponse) {
        if(baseResponse.statusCode == 1)
        {
            mPresenterView.finishLoading();
            mPresenterView.updateData();
        }else{
            mPresenterView.finishLoading(baseResponse.msg,false);
        }
    }
}
