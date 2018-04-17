package manulife.manulifesop.activity.ManagerGroup.Recruitment.survey;

import manulife.manulifesop.api.ObjectResponse.UsersList;
import manulife.manulifesop.base.BaseMVPView;

/**
 * Created by trinm on 12/01/2018.
 */

public interface SurveyContract {
    interface View extends BaseMVPView
    {
        void initViewPager(UsersList contact, UsersList callLater, UsersList refuse);
        void reloadData();
        int getSelectedType();
    }
    interface Action{
        void getAllContactPerson(int period, int page);
    }
}
