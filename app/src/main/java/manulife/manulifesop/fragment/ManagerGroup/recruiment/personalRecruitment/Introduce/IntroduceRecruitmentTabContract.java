package manulife.manulifesop.fragment.ManagerGroup.recruiment.personalRecruitment.Introduce;

import manulife.manulifesop.api.ObjectResponse.UsersList;
import manulife.manulifesop.base.BaseMVPView;


/**
 * Created by Chick on 10/27/2017.
 */

public interface IntroduceRecruitmentTabContract {

    interface View extends BaseMVPView {
        void showDialogChooseWeek(boolean isChangeContact);
        void loadContactList(UsersList data);
        void reloadData();
    }

    interface Action {
        void getUserListProcess(String search, int period, int page);
        void addIntroduceRecruit(String phone, String name, int campaignID);
    }
}
