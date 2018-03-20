package manulife.manulifesop.fragment.FAGroup.clients.related.contactDetail.step2;

import manulife.manulifesop.api.ObjectResponse.ContactActivity;
import manulife.manulifesop.base.BaseMVPView;


/**
 * Created by Chick on 10/27/2017.
 */

public interface ContactDetailStep2Contract {

    interface View extends BaseMVPView {
        void showMenuCreateEvent();
        void loadContactActivities(ContactActivity data);
    }

    interface Action {
        void getEvents(int leadID);
    }
}
