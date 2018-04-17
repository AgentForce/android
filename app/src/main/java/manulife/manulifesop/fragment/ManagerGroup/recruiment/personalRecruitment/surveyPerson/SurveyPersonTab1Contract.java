package manulife.manulifesop.fragment.ManagerGroup.recruiment.personalRecruitment.surveyPerson;

import manulife.manulifesop.api.ObjectResponse.UsersList;
import manulife.manulifesop.base.BaseMVPView;


/**
 * Created by Chick on 10/27/2017.
 */

public interface SurveyPersonTab1Contract {

    interface View extends BaseMVPView {
        void gotoContactDetail(int id);
        void loadContactList(UsersList data);
        void showDialogChooseWeek();

        void addTextChangeListener();
    }

    interface Action {
        void getUserListProcess(int period, int status, int page, String search);
    }
}
