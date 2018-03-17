package manulife.manulifesop.fragment.FAGroup.clients.contactIntroduce;

import manulife.manulifesop.api.ObjectResponse.UsersList;
import manulife.manulifesop.base.BaseMVPView;


/**
 * Created by Chick on 10/27/2017.
 */

public interface IntroduceContactTabContract {

    interface View extends BaseMVPView {
        void showDialogChooseWeek(boolean isChangeContact);
        void loadContactList(UsersList data);
        void reloadData();
    }

    interface Action {
        void getUserListProcess(int period, int page);
        void addIntroduceContact(String phone, String name, int campaignID);
    }
}
