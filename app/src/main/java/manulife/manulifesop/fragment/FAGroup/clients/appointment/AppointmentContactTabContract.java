package manulife.manulifesop.fragment.FAGroup.clients.appointment;

import manulife.manulifesop.base.BaseMVPView;


/**
 * Created by Chick on 10/27/2017.
 */

public interface AppointmentContactTabContract {

    interface View extends BaseMVPView {
        void gotoConactDetail(int id);
        void loadDataContact();
    }

    interface Action {
        void getContact(int period, int status, int page);
    }
}
