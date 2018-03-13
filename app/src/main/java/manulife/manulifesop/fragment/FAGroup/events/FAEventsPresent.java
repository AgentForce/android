package manulife.manulifesop.fragment.FAGroup.events;


import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import manulife.manulifesop.BuildConfig;
import manulife.manulifesop.ProjectApplication;
import manulife.manulifesop.adapter.ObjectData.EventCalendar;
import manulife.manulifesop.api.ApiService;
import manulife.manulifesop.api.ObjectResponse.DashboardResult;
import manulife.manulifesop.api.ObjectResponse.EventsMonth;
import manulife.manulifesop.api.ObjectResponse.EventsOneDay;
import manulife.manulifesop.base.BasePresenter;
import manulife.manulifesop.util.Contants;
import manulife.manulifesop.util.DeviceInfo;
import manulife.manulifesop.util.Utils;

/**
 * Created by Chick on 10/27/2017.
 */

public class FAEventsPresent extends BasePresenter<FAEventsContract.View> implements FAEventsContract.Action {

    private DashboardResult mDataDashboardWeekMonth;
    private DashboardResult mDataDashboardYear;

    public FAEventsPresent(FAEventsContract.View presenterView) {
        super(presenterView);
    }

    @Override
    public void getAllActivitisInMonth(int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH,1);
        String firstDay = Utils.convertDateToString(calendar.getTime(),"yyyy-MM-dd");
        calendar.set(Calendar.DAY_OF_MONTH,calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        String lastDay = Utils.convertDateToString(calendar.getTime(),"yyyy-MM-dd");

        getCompositeDisposable().add(ApiService.getServer().getEventsMonth(
                Contants.clientID, DeviceInfo.ANDROID_OS_VERSION, BuildConfig.VERSION_NAME, DeviceInfo.DEVICE_NAME, DeviceInfo.DEVICEIMEI,
                firstDay,lastDay)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(this::handleResponseEventsMonth, this::handleError));
    }

    private void handleResponseEventsMonth(EventsMonth data) {
        if(data.statusCode==1){
            List<String> colors;
            for(int i=0;i<data.data.size();i++){
                //mPresenterView.addEventToDate(Utils.convertStringToDate(data.data.get(i).date,"yyyy-MM-dd"));
                colors = new ArrayList<>();
                for(int j=0;j<data.data.get(i).activities.size();j++){
                    colors.add(ProjectApplication.getProcessStepColor(data.data.get(i).activities.get(j).processStep));
                    if(j>3)break;
                }
                mPresenterView.addEventToDate(Utils.convertStringToDate(data.data.get(i).date,"yyyy-MM-dd"),colors);
            }

        }else{
            mPresenterView.finishLoading(data.msg,false);
        }
    }

    @Override
    public void getEventsOneDay(Date date) {
        mPresenterView.showLoading("Lấy dữ liệu");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        getCompositeDisposable().add(ApiService.getServer().getEventsDay(
                Contants.clientID, DeviceInfo.ANDROID_OS_VERSION, BuildConfig.VERSION_NAME, DeviceInfo.DEVICE_NAME, DeviceInfo.DEVICEIMEI,
                df.format(date))
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(this::handleResponseEventsDay, this::handleError));
    }

    private void handleError(Throwable throwable) {
        mPresenterView.finishLoading(throwable.getMessage(),false);
    }

    private void handleResponseEventsDay(EventsOneDay data) {
        if(data.statusCode==1){
            List<EventCalendar> dataList = new ArrayList<>();
            EventCalendar tmp;
            for(int i=0;i<data.data.size();i++)
            {
                tmp = new EventCalendar("",data.data.get(i).manulifeLead.name,
                        data.data.get(i).processStep,
                        Utils.convertStringDateToStringDate(data.data.get(i).startDate,
                                "yyyy-MM-dd'T'HH:mm:ss.sss'Z'","dd-MM-yyyy HH:mm:ss"),
                        "Location");
                dataList.add(tmp);
            }
            mPresenterView.showDataEvents(dataList);
            mPresenterView.finishLoading();
        }else
        {
            mPresenterView.finishLoading(data.msg,false);
        }
    }
}
