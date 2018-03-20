package manulife.manulifesop.activity.FAGroup.clients.related.createEvent;

import manulife.manulifesop.base.BaseMVPView;

/**
 * Created by trinm on 12/01/2018.
 */

public interface CreateEventContract {
    interface View extends BaseMVPView {
        void finishCreate();
        void showMenuChooseEvent();
    }

    interface Action {
        void createEvent(int leadID,int type,String name, String location, String startDate
        ,String endDate, String description, boolean fullDay, int notification, boolean support);
    }
}
