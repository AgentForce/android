package manulife.manulifesop.activity.FAGroup.clients.related.signedSuccess;

import android.content.Context;

import com.google.gson.Gson;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import manulife.manulifesop.BuildConfig;
import manulife.manulifesop.activity.FAGroup.clients.related.eventDetail.EventDetailContract;
import manulife.manulifesop.api.ApiService;
import manulife.manulifesop.api.ObjectInput.InputChangeContactStatus;
import manulife.manulifesop.api.ObjectInput.InputSubmitContract;
import manulife.manulifesop.api.ObjectResponse.BaseResponse;
import manulife.manulifesop.base.BasePresenter;
import manulife.manulifesop.util.Contants;
import manulife.manulifesop.util.DeviceInfo;
import manulife.manulifesop.util.SOPSharedPreferences;
import manulife.manulifesop.util.Utils;

/**
 * Created by trinm on 12/01/2018.
 */

public class SignedSuccessPresenter extends BasePresenter<SignedSuccessContract.View> implements SignedSuccessContract.Action {

    private Context mContext;

    public SignedSuccessPresenter(SignedSuccessContract.View presenterView, Context context) {
        super(presenterView);
        this.mContext = context;
    }

    @Override
    public void submitContract(int leadID, int commissionRate, int revenue, int numContract, int productType) {
        mPresenterView.showLoading("Xử lý dữ liệu");

        InputSubmitContract data = new InputSubmitContract();
        data.leadId = leadID;
        data.commissionRate = commissionRate;
        data.revenue = revenue * 1000000;
        data.numContract = numContract;
        data.productType = productType;

        String checksum = Utils.getSignature(new Gson().toJson(data));

        getCompositeDisposable().add(ApiService.getServer().submitContract(
                SOPSharedPreferences.getInstance(mContext).getAccessToken(),
                Contants.clientID, DeviceInfo.ANDROID_OS_VERSION, BuildConfig.VERSION_NAME, DeviceInfo.DEVICE_NAME, DeviceInfo.DEVICEIMEI,
                checksum,data)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleErrorr));
    }

    private void handleErrorr(Throwable throwable) {
        mPresenterView.finishLoading(throwable.getMessage(),false);
    }

    private void handleResponse(BaseResponse baseResponse) {
        if(baseResponse.statusCode == 1){
            mPresenterView.finishLoading();
            mPresenterView.finishSubmit();
        }else{
            mPresenterView.finishLoading(baseResponse.msg,false);
        }
    }
}
