package manulife.manulifesop.activity.FAGroup.clients.related.contactDetail;

import manulife.manulifesop.base.BaseMVPView;

/**
 * Created by trinm on 12/01/2018.
 */

public interface ContactDetailContract {
    interface View extends BaseMVPView
    {
        void showHideMenuAfterCall();
        void showMenuOption();
        void initViewPager();
        void finishChangeStatus();
    }
    interface Action{
        void getContactDetail(int id);
        void getRecruitDetail(int id);
        void updateStatusProcess(int leadID,boolean isChangeStep, int changeToStatus);
        void updateStatusProcessRecruit(int leadID,boolean isChangeStep, int changeToStatus);
    }
}
