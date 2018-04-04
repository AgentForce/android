package manulife.manulifesop.activity.FAGroup.clients.related.eventDetail;

import android.content.Context;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import manulife.manulifesop.BuildConfig;
import manulife.manulifesop.api.ApiService;
import manulife.manulifesop.api.ObjectResponse.ActivityDetail;
import manulife.manulifesop.api.ObjectResponse.BaseResponse;
import manulife.manulifesop.base.BasePresenter;
import manulife.manulifesop.util.CalendarEvents;
import manulife.manulifesop.util.Contants;
import manulife.manulifesop.util.DeviceInfo;
import manulife.manulifesop.util.SOPSharedPreferences;

/**
 * Created by trinm on 12/01/2018.
 */

public class EventDetailPresenter extends BasePresenter<EventDetailContract.View> implements EventDetailContract.Action {

    private Context mContext;

    public EventDetailPresenter(EventDetailContract.View presenterView, Context context) {
        super(presenterView);
        this.mContext = context;
    }

    @Override
    public void getActivityDetail(int id) {
        mPresenterView.showLoading("Lấy dữ liệu");
        getCompositeDisposable().add(ApiService.getServer().getActivityDetail(SOPSharedPreferences.getInstance(mContext).getAccessToken(),
                Contants.clientID, DeviceInfo.ANDROID_OS_VERSION, BuildConfig.VERSION_NAME, DeviceInfo.DEVICE_NAME, DeviceInfo.DEVICEIMEI,
                id)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(this::handleResponseActivityDetail, this::handleError));
    }

    private void handleError(Throwable throwable) {
        mPresenterView.finishLoading(throwable.getMessage(),false);
    }

    private void handleResponseActivityDetail(ActivityDetail activityDetail) {
        if(activityDetail.statusCode == 1){
            mPresenterView.loadData(activityDetail);
            mPresenterView.finishLoading();
        }else{
            mPresenterView.finishLoading(activityDetail.msg,false);
        }
    }

    @Override
    public void deleteEvent(int eventID,String eventName) {
        mPresenterView.showLoading("Lấy dữ liệu");
        getCompositeDisposable().add(ApiService.getServer().deleteEvent(SOPSharedPreferences.getInstance(mContext).getAccessToken(),
                Contants.clientID, DeviceInfo.ANDROID_OS_VERSION, BuildConfig.VERSION_NAME, DeviceInfo.DEVICE_NAME, DeviceInfo.DEVICEIMEI,
                eventID)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .map(baseResponse -> {
                    if(baseResponse.statusCode == 1){
                        CalendarEvents.deleteEvent(mContext,eventID,eventName);
                    }
                    return baseResponse;
                })
                .subscribe(this::handleResponseDelete, this::handleError));
    }

    private void handleResponseDelete(BaseResponse baseResponse) {
        if(baseResponse.statusCode == 1){
            mPresenterView.finishSuccess();
            mPresenterView.finishLoading();
        }else{
            mPresenterView.finishLoading(baseResponse.msg,false);
        }
    }
}
