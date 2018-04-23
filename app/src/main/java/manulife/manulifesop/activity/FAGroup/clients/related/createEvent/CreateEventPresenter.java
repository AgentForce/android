package manulife.manulifesop.activity.FAGroup.clients.related.createEvent;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.google.gson.Gson;

import java.util.Calendar;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import manulife.manulifesop.BuildConfig;
import manulife.manulifesop.api.ApiService;
import manulife.manulifesop.api.ObjectInput.InputCreateEvent;
import manulife.manulifesop.api.ObjectInput.InputUpdateEvent;
import manulife.manulifesop.api.ObjectResponse.ActivityDetail;
import manulife.manulifesop.api.ObjectResponse.BaseResponse;
import manulife.manulifesop.api.ObjectResponse.EventsCreate;
import manulife.manulifesop.base.BasePresenter;
import manulife.manulifesop.util.CalendarEvents;
import manulife.manulifesop.util.Contants;
import manulife.manulifesop.util.DeviceInfo;
import manulife.manulifesop.util.SOPSharedPreferences;
import manulife.manulifesop.util.Utils;

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

        String checksum = Utils.getSignature(new Gson().toJson(data));

        getCompositeDisposable().add(ApiService.getServer().createActivity(SOPSharedPreferences.getInstance(mContext).getAccessToken(),
                Contants.clientID, DeviceInfo.ANDROID_OS_VERSION, BuildConfig.VERSION_NAME, DeviceInfo.DEVICE_NAME, DeviceInfo.DEVICEIMEI,
                checksum, data)
                .map(eventsCreate -> {
                    if(eventsCreate.statusCode == 1) {
                        long startDateAdd = Utils.convertStringToDate(data.startDate, "yyyy-MM-dd HH:mm").getTime();
                        long endDateAdd = Utils.convertStringToDate(data.endDate, "yyyy-MM-dd HH:mm").getTime();
                        if(data.fullDate){
                            Calendar calendar = Calendar.getInstance();
                            calendar.setTime(Utils.convertStringToDate(data.startDate,"yyyy-MM-dd"));
                            calendar.add(Calendar.DAY_OF_YEAR,1);

                            startDateAdd = calendar.getTimeInMillis();
                            endDateAdd = calendar.getTimeInMillis();
                        }

                        CalendarEvents.pushEventToLocalCalendar(mContext, eventsCreate.data.id,data.name, data.description, data.location,
                                1,startDateAdd,endDateAdd,data.fullDate,true, data.notification);
                    }
                    return eventsCreate;
                })
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(this::handleResponseCreate, this::handleError));
    }

    private void handleError(Throwable throwable) {
        mPresenterView.finishLoading(throwable.getMessage(), false);
    }

    private void handleResponseCreate(EventsCreate eventsCreate) {
        if (eventsCreate.statusCode == 1) {
            mPresenterView.finishCreate();
            mPresenterView.finishLoading();
        } else {
            mPresenterView.finishLoading(eventsCreate.msg, false);
        }
    }

    @Override
    public void updateEvent(int eventID, int type, String oldName,String newName, String location, String startDate, String endDate, String description, boolean fullDay, int notification, boolean support) {
        mPresenterView.showLoading("Cập nhật sự kiện");
        InputUpdateEvent data = new InputUpdateEvent();
        data.type = type;
        data.name = newName;
        data.location = location;
        data.startDate = startDate;
        data.endDate = endDate;
        data.description = description;
        data.fullDate = fullDay;
        data.notification = notification;
        data.isSupport = support;

        String checksum = Utils.getSignature(new Gson().toJson(data));

        getCompositeDisposable().add(ApiService.getServer().updateEvent(SOPSharedPreferences.getInstance(mContext).getAccessToken(),
                Contants.clientID, DeviceInfo.ANDROID_OS_VERSION, BuildConfig.VERSION_NAME, DeviceInfo.DEVICE_NAME, DeviceInfo.DEVICEIMEI,
                checksum, eventID, data)
                .map(baseResponse -> {
                    long startDateAdd = Utils.convertStringToDate(data.startDate, "yyyy-MM-dd HH:mm").getTime();
                    long endDateAdd = Utils.convertStringToDate(data.endDate, "yyyy-MM-dd HH:mm").getTime();
                    if(data.fullDate){
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(Utils.convertStringToDate(data.startDate,"yyyy-MM-dd"));
                        calendar.add(Calendar.DAY_OF_YEAR,1);

                        startDateAdd = calendar.getTimeInMillis();
                        endDateAdd = calendar.getTimeInMillis();
                    }
                    if(baseResponse.statusCode == 1){
                        CalendarEvents.updateEvent(mContext,eventID,oldName,data.name,data.description,
                                data.location,1,startDateAdd,endDateAdd,data.fullDate,
                                true,data.notification);
                    }
                    return baseResponse;
                })
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(this::handleResponseUpdate, this::handleError));
    }

    private void handleResponseUpdate(BaseResponse baseResponse) {
        if (baseResponse.statusCode == 1) {
            mPresenterView.finishCreate();
            mPresenterView.finishLoading();
        } else {
            mPresenterView.finishLoading(baseResponse.msg, false);
        }
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

    private void handleResponseActivityDetail(ActivityDetail activityDetail) {
        if (activityDetail.statusCode == 1) {
            mPresenterView.showUpdateViews(activityDetail);
            mPresenterView.finishLoading();
        } else {
            mPresenterView.finishLoading(activityDetail.msg, false);
        }
    }
}
