package manulife.manulifesop.fragment.FAGroup.events;

import android.graphics.Color;

import java.util.Date;
import java.util.List;

import manulife.manulifesop.adapter.ObjectData.EventCalendar;
import manulife.manulifesop.api.ObjectResponse.ActivitiHist;
import manulife.manulifesop.api.ObjectResponse.DashboardResult;
import manulife.manulifesop.base.BaseMVPView;


/**
 * Created by Chick on 10/27/2017.
 */

public interface FAEventsContract {

    interface View extends BaseMVPView {
        void addEventToDate(Date date, List<String> colors);
        void showDataEvents(List<EventCalendar> data);
        void updateData();
    }

    interface Action {
        void getAllActivitisInMonth(int month,Date loadDate);
        void getEventsOneDay(Date date);
        void updateEventDone(int eventID);
    }
}
