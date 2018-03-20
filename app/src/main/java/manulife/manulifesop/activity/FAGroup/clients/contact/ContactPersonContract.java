package manulife.manulifesop.activity.FAGroup.clients.contact;

import manulife.manulifesop.api.ObjectResponse.UsersList;
import manulife.manulifesop.base.BaseMVPView;

/**
 * Created by trinm on 12/01/2018.
 */

public interface ContactPersonContract {
    interface View extends BaseMVPView
    {
        void initViewPager(UsersList contact,UsersList callLater,UsersList refuse);
    }
    interface Action{
        void getAllContactPerson(int period, int page);
    }
}
