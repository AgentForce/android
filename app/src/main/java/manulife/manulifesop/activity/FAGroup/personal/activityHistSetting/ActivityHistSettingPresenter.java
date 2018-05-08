package manulife.manulifesop.activity.FAGroup.personal.activityHistSetting;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import manulife.manulifesop.BuildConfig;
import manulife.manulifesop.adapter.ObjectData.ActiveHistSetting;
import manulife.manulifesop.adapter.ObjectData.SpinnerObject;
import manulife.manulifesop.api.ApiService;
import manulife.manulifesop.base.BasePresenter;
import manulife.manulifesop.util.Contants;
import manulife.manulifesop.util.DeviceInfo;
import manulife.manulifesop.util.SOPSharedPreferences;
import manulife.manulifesop.util.Utils;

/**
 * Created by trinm on 12/01/2018.
 */

public class ActivityHistSettingPresenter extends BasePresenter<ActivityHistSettingContract.View> implements ActivityHistSettingContract.Action {

    private Context mContext;
    private String mErrorMsg;
    private int mCurrentPage = 1;
    private int mLastPage = 1;

    public ActivityHistSettingPresenter(ActivityHistSettingContract.View presenterView, Context context) {
        super(presenterView);
        this.mContext = context;
    }

    @Override
    public void getActivitiHist(int page, int limit) {
        mPresenterView.showLoading("Lấy dữ liệu!");
        getCompositeDisposable().add(ApiService.getServer().getActivitiHistSetting(
                SOPSharedPreferences.getInstance(mContext).getAccessToken(),
                Contants.clientID, DeviceInfo.ANDROID_OS_VERSION, BuildConfig.VERSION_NAME,
                DeviceInfo.DEVICE_NAME, DeviceInfo.DEVICEIMEI,page,limit)
                .map(rs -> {
                    List<ActiveHistSetting> data = new ArrayList<>();
                    if(rs.statusCode == 1){
                        mCurrentPage = rs.data.page;
                        mLastPage = Utils.genLastPage(rs.data.count, rs.data.limit);

                        ActiveHistSetting tmp;
                        for(int i=0;i<rs.data.items.size();i++){
                            tmp = new ActiveHistSetting();
                            tmp.setTitle(rs.data.items.get(i).activityName);
                            tmp.setTime(Utils.convertStringTimeZoneDateToStringDate(
                                    rs.data.items.get(i).updatedAt,
                                    "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
                                    "dd/MM/yyyy HH:mm:ss"
                            ));
                            data.add(tmp);
                        }
                    }else
                        mErrorMsg = rs.msg;
                    return data;
                })
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleErrorr));
    }

    private void handleErrorr(Throwable throwable) {
        mPresenterView.finishLoading(throwable.getMessage(),false);
    }

    private void handleResponse(List<ActiveHistSetting> rs) {
        if(rs.size() > 0){
            mPresenterView.showData(rs,mCurrentPage,mLastPage);
            mPresenterView.finishLoading();
        }else
            mPresenterView.finishLoading(mErrorMsg,false);
    }
}
