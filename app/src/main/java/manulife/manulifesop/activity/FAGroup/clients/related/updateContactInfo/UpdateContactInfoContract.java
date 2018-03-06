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
    }
    interface Action{
        void updateContactInfo(int position);

    }
}
