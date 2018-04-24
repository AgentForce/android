package manulife.manulifesop.activity.FAGroup.clients.related.createEvent;

import android.app.Activity;
import android.content.Context;

import manulife.manulifesop.api.ObjectResponse.ActivityDetail;
import manulife.manulifesop.base.BaseMVPView;

/**
 * Created by trinm on 12/01/2018.
 */

public interface CreateEventContract {
    interface View extends BaseMVPView {
        void finishCreate();
        void showMenuChooseEvent();
        void showMenuChooseEventUM();
        void showMenuChooseTimeRemind();
        void showUpdateViews(ActivityDetail data);
    }

    interface Action {
        void getActivityDetail(int id);
        void createEvent(int leadID,int type,String name, String location, String startDate
        ,String endDate, String description, boolean fullDay, int notification, boolean support);
        void updateEvent(int eventID, int type,String oldName,String newName, String location, String startDate
                ,String endDate, String description, boolean fullDay, int notification, boolean support);
    }
}
