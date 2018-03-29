package manulife.manulifesop.activity.FAGroup.confirmCreatePlan;

import manulife.manulifesop.api.ObjectResponse.UserProfile;
import manulife.manulifesop.base.BaseMVPView;

/**
 * Created by trinm on 12/01/2018.
 */

public interface ConfirmCreatePlanContract {
    interface View extends BaseMVPView
    {
        void showData(UserProfile data);
    }
    interface Action{
        void getUserProfile(String userName);
    }
}
