package manulife.manulifesop.activity.FAGroup.clients.related.eventDetail;

import manulife.manulifesop.api.ObjectResponse.ActivityDetail;
import manulife.manulifesop.base.BaseMVPView;

/**
 * Created by trinm on 12/01/2018.
 */

public interface EventDetailContract {
    interface View extends BaseMVPView {
        void loadData(ActivityDetail data);
        void finishSuccess();
    }

    interface Action {
        void getActivityDetail(int id);
        void deleteEvent(int eventID,String eventName);
    }
}
