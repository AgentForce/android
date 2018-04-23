package manulife.manulifesop.activity.FAGroup.personal.personalInfo;

import manulife.manulifesop.api.ObjectResponse.UserProfile;
import manulife.manulifesop.base.BaseMVPView;

/**
 * Created by trinm on 12/01/2018.
 */

public interface PersonalInfoContract {
    interface View extends BaseMVPView {
        void loadData();
    }

    interface Action {

    }
}
