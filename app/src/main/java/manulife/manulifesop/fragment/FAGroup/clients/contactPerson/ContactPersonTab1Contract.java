package manulife.manulifesop.fragment.FAGroup.clients.contactPerson;

import manulife.manulifesop.api.ObjectResponse.UsersList;
import manulife.manulifesop.base.BaseMVPView;


/**
 * Created by Chick on 10/27/2017.
 */

public interface ContactPersonTab1Contract {

    interface View extends BaseMVPView {
        void gotoContactDetail(int id);
        void loadContactList(UsersList data);
        void showDialogChooseWeek();
    }

    interface Action {
        void getUserListProcess(int period, int status, int page);
    }
}
