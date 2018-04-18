package manulife.manulifesop.fragment.FAGroup.clients.ContentClient.ContactMonth;


import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import manulife.manulifesop.BuildConfig;
import manulife.manulifesop.ProjectApplication;
import manulife.manulifesop.adapter.ObjectData.ContactAllFA;
import manulife.manulifesop.api.ApiService;
import manulife.manulifesop.api.ObjectResponse.ContactMonth;
import manulife.manulifesop.base.BasePresenter;
import manulife.manulifesop.fragment.FAGroup.clients.ContentClient.ObjectMonth.FAObjectMonthContract;
import manulife.manulifesop.util.Contants;
import manulife.manulifesop.util.DeviceInfo;
import manulife.manulifesop.util.SOPSharedPreferences;
import manulife.manulifesop.util.Utils;

/**
 * Created by Chick on 10/27/2017.
 */

public class FAContactMonthPresent extends BasePresenter<FAContactMonthContract.View> implements FAContactMonthContract.Action{

    private Context mContext;
    int currentPage,lastPage;

    public FAContactMonthPresent(FAContactMonthContract.View presenterView,Context context) {
        super(presenterView);
        this.mContext = context;
    }

    @Override
    public void getContactMonth(int month, String search, int page) {
        mPresenterView.showLoading("Lấy dữ liệu");
        getCompositeDisposable().add(ApiService.getServer().getContactMonth(
                SOPSharedPreferences.getInstance(mContext).getAccessToken(),
                Contants.clientID, DeviceInfo.ANDROID_OS_VERSION, BuildConfig.VERSION_NAME,
                DeviceInfo.DEVICE_NAME, DeviceInfo.DEVICEIMEI, month,search,page,10)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(this::handleResponseContactMonth, this::handleError)
        );
    }

    private void handleError(Throwable throwable) {
        mPresenterView.finishLoading(throwable.getMessage(),false);
    }

    private void handleResponseContactMonth(ContactMonth contactMonth) {
        if(contactMonth.statusCode == 1){

            Observable.just(contactMonth)
                    .map(data ->
                    {
                        currentPage = data.data.page;
                        lastPage = Utils.genLastPage(data.data.count,Integer.valueOf(data.data.limit));
                        List<ContactAllFA> datatmp = new ArrayList<>();
                        if (data.statusCode == 1 && data.data.count > 0) {
                            ContactAllFA tmp;
                            for (int i = 0; i < data.data.rows.size(); i++) {
                                tmp = new ContactAllFA();
                                tmp.setId(data.data.rows.get(i).id);
                                tmp.setCampaignID(data.data.rows.get(i).campId);
                                tmp.setTitle(data.data.rows.get(i).name);
                                tmp.setContent(data.data.rows.get(i).phone);
                                tmp.setPhone(data.data.rows.get(i).phone);
                                tmp.setProcessStep(data.data.rows.get(i).processStep);
                                tmp.setStatusStep(data.data.rows.get(i).statusProcessStep);

                                datatmp.add(tmp);
                            }
                        }
                        return datatmp;

                    }).subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(contactAllFAS -> {
                        mPresenterView.loadContactMonth(contactAllFAS,currentPage,lastPage);
                        mPresenterView.finishLoading();
                    });
        }else
            mPresenterView.finishLoading(contactMonth.msg,false);
    }
}
