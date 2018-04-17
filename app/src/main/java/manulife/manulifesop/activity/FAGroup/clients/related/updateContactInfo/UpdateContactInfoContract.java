package manulife.manulifesop.activity.FAGroup.clients.related.updateContactInfo;

import manulife.manulifesop.base.BaseMVPView;

/**
 * Created by trinm on 12/01/2018.
 */

public interface UpdateContactInfoContract {
    interface View extends BaseMVPView
    {
        boolean validateInput();
        void loadNextContact(int position);

        void finishChangeToContact();
    }
    interface Action{
        void addContactInfo(int position, String name, String phone,int age, int gender,
                               int income, int marital, int relationship,
                               int source, String description);
        void changeReleadToContact(int releadID, int campaignID, int age, int gender,
                                   int income, int marital, int relationship,
                                   int source, String description);
        void updateContactInfo(int releadID, String name, int age, int gender,
                               int income, int marital, int relationship,
                               int source, String description);
        //__________sm recruit__________
        void addRecruitInfo(int position, String name, String phone,int age, int gender,
                            int income, int marital, int relationship,
                            int source, String description);
        void changeReleadToRecruit(int releadID, int campaignID, int age, int gender,
                                   int income, int marital, int relationship,
                                   int source, String description);
        void updateRecruitInfo(int releadID, String name, int age, int gender,
                               int income, int marital, int relationship,
                               int source, String description);

    }
}
