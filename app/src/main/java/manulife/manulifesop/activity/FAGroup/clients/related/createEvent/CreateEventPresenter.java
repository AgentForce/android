package manulife.manulifesop.activity.FAGroup.clients.related.createEvent;

import android.content.Context;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import manulife.manulifesop.BuildConfig;
import manulife.manulifesop.api.ApiService;
import manulife.manulifesop.api.ObjectInput.InputCreateEvent;
import manulife.manulifesop.api.ObjectResponse.EventsCreate;
import manulife.manulifesop.base.BasePresenter;
import manulife.manulifesop.util.Contants;
import manulife.manulifesop.util.DeviceInfo;
import manulife.manulifesop.util.SOPSharedPreferences;

/**
 * Created by trinm on 12/01/2018.
 */

public class CreateEventPresenter extends BasePresenter<CreateEventContract.View> implements CreateEventContract.Action {

    private Context mContext;

    public CreateEventPresenter(CreateEventContract.View presenterView, Context context) {
        super(presenterView);
        this.mContext = context;
    }

    @Override
    public void createEvent(int leadID, int type, String name, String location, String startDate, String endDate,
                            String description, boolean fullDay, int notification, boolean support) {
        mPresenterView.showLoading("Xử lý dữ liệu");
        InputCreateEvent data = new InputCreateEvent();
        data.leadId = leadID;
        data.type = type;
        data.name = name;
        data.location = location;
        data.startDate = startDate;
        data.endDate = endDate;
        data.description = description;
        data.fullDate = fullDay;
        data.notification = notification;
        data.isSupport = support;

        getCompositeDisposable().add(ApiService.getServer().createActivity(SOPSharedPreferences.getInstance(mContext).getAccessToken(),
                Contants.clientID, DeviceInfo.ANDROID_OS_VERSION, BuildConfig.VERSION_NAME, DeviceInfo.DEVICE_NAME, DeviceInfo.DEVICEIMEI,
                "checksum",data)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(this::handleResponseCreate, this::handleError));
    }

    private void handleError(Throwable throwable) {
        mPresenterView.finishLoading(throwable.getMessage(),false);
    }

    private void handleResponseCreate(EventsCreate eventsCreate) {
        if(eventsCreate.statusCode == 1){
            mPresenterView.finishCreate();
            mPresenterView.finishLoading();
        }else{
            mPresenterView.finishLoading(eventsCreate.msg,false);
        }
    }
}
