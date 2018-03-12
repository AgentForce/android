package manulife.manulifesop.fragment.FAGroup.events;


import java.util.Calendar;

import manulife.manulifesop.api.ObjectResponse.DashboardResult;
import manulife.manulifesop.base.BasePresenter;
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


    }
}
