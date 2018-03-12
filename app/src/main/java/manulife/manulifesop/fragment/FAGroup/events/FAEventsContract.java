package manulife.manulifesop.fragment.FAGroup.events;

import java.util.Date;

import manulife.manulifesop.api.ObjectResponse.ActivitiHist;
import manulife.manulifesop.api.ObjectResponse.DashboardResult;
import manulife.manulifesop.base.BaseMVPView;


/**
 * Created by Chick on 10/27/2017.
 */

public interface FAEventsContract {

    interface View extends BaseMVPView {
        void addEventToDate(Date date);
    }

    interface Action {
        void getAllActivitisInMonth(int month);
    }
}
