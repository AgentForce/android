package manulife.manulifesop.fragment.ManagerGroup.recruiment.personalRecruitment.COP;

import manulife.manulifesop.api.ObjectResponse.UsersList;
import manulife.manulifesop.base.BaseMVPView;


/**
 * Created by Chick on 10/27/2017.
 */

public interface COPTabContract {

    interface View extends BaseMVPView {
        void gotoConactDetail(int id);
        void loadDataContact(UsersList data);

        void addTextChangeListener();
    }

    interface Action {
        void getContact(int period, int status, int page, String search);
    }
}