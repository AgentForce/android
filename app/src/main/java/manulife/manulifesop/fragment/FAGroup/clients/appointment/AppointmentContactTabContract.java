package manulife.manulifesop.fragment.FAGroup.clients.appointment;

import manulife.manulifesop.api.ObjectResponse.UsersList;
import manulife.manulifesop.base.BaseMVPView;


/**
 * Created by Chick on 10/27/2017.
 */

public interface AppointmentContactTabContract {

    interface View extends BaseMVPView {
        void gotoConactDetail(int id);
        void loadDataContact(UsersList data);

        void addTextChangeListener();
    }

    interface Action {
        void getContact(int period, int status, int page,String search);
    }
}
