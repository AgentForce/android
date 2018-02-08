package manulife.manulifesop.activity.FAGroup.main;

import manulife.manulifesop.base.BaseMVPView;

/**
 * Created by trinm on 12/01/2018.
 */

public interface MainFAContract {
    interface View extends BaseMVPView
    {
        void showFragmentConfirmCreatePlan();
    }
    interface Action{
        void checkIsGetCampaign(boolean isGetCampaign);
    }
}
