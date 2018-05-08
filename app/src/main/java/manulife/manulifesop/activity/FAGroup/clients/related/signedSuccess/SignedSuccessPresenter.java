package manulife.manulifesop.activity.FAGroup.clients.related.signedSuccess;

import android.content.Context;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import manulife.manulifesop.BuildConfig;
import manulife.manulifesop.activity.FAGroup.clients.related.eventDetail.EventDetailContract;
import manulife.manulifesop.adapter.ObjectData.SpinnerObject;
import manulife.manulifesop.api.ApiService;
import manulife.manulifesop.api.ObjectInput.InputChangeContactStatus;
import manulife.manulifesop.api.ObjectInput.InputSubmitContract;
import manulife.manulifesop.api.ObjectResponse.BaseResponse;
import manulife.manulifesop.api.ObjectResponse.ProductsSign;
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

    private ArrayList<SpinnerObject> dataMainProduct;
    private ArrayList<SpinnerObject> dataSubProduct;

    public SignedSuccessPresenter(SignedSuccessContract.View presenterView, Context context) {
        super(presenterView);
        this.mContext = context;
    }

    @Override
    public void getProductsSign() {
        mPresenterView.showLoading("Lấy dữ liệu");

        getCompositeDisposable().add(ApiService.getServer().getProductsSign(
                SOPSharedPreferences.getInstance(mContext).getAccessToken(),
                Contants.clientID, DeviceInfo.ANDROID_OS_VERSION, BuildConfig.VERSION_NAME,
                DeviceInfo.DEVICE_NAME, DeviceInfo.DEVICEIMEI)
                .map(data -> {
                    if(data.statusCode == 1){
                        SpinnerObject tmp;
                        dataMainProduct = new ArrayList<>();
                        tmp = new SpinnerObject("0","Chọn sản phẩm chính");
                        dataMainProduct.add(tmp);
                        for(int i=0;i<data.data.main.size();i++){
                            tmp = new SpinnerObject(
                                    String.valueOf(data.data.main.get(i).id),
                                    data.data.main.get(i).title
                            );
                            dataMainProduct.add(tmp);
                        }

                        dataSubProduct = new ArrayList<>();
                        tmp = new SpinnerObject("0","Chọn sản phẩm phụ");
                        dataSubProduct.add(tmp);
                        for(int i=0;i<data.data.sub.size();i++){
                            tmp = new SpinnerObject(
                                    String.valueOf(data.data.sub.get(i).id),
                                    data.data.sub.get(i).title
                            );
                            dataSubProduct.add(tmp);
                        }

                    }
                    return data;
                })
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(this::handleResponseProducts, this::handleErrorr));
    }

    private void handleResponseProducts(ProductsSign rs) {
        if(rs.statusCode == 1){
            mPresenterView.initProducts(dataMainProduct,dataSubProduct);
            mPresenterView.finishLoading();
        }else{
            mPresenterView.finishLoading(rs.msg,false);
        }
    }

    @Override
    public void submitContract(int leadID, int commissionRate, int revenue,
                               int numContract, String chooseMainProduct,
                               String chooseSubProduct, String dateSign) {
        mPresenterView.showLoading("Xử lý dữ liệu");

        InputSubmitContract data = new InputSubmitContract();
        data.leadId = leadID;
        data.commissionRate = commissionRate;
        data.revenue = revenue;
        data.numContract = numContract;

        List<Integer> chooseProductType = new ArrayList<>();
        //main product
        String temp[] = chooseMainProduct.split(",");
        for(int i=0;i<temp.length;i++){
            chooseProductType.add(Integer.valueOf(temp[i]));
        }
        //sub product
        String temp2[] = chooseSubProduct.split(",");
        for(int i=0;i<temp2.length;i++){
            chooseProductType.add(Integer.valueOf(temp2[i]));
        }
        data.productId = chooseProductType;

        //convert date
        data.dateContract = Utils.convertStringDateToStringDate(dateSign,
                "dd/MM/yyyy","yyyy-MM-dd");

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
