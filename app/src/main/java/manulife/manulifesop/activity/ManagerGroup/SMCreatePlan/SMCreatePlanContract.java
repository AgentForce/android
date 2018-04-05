package manulife.manulifesop.activity.ManagerGroup.SMCreatePlan;

import manulife.manulifesop.base.BaseMVPView;

/**
 * Created by trinm on 12/01/2018.
 */

public interface SMCreatePlanContract {
    interface View extends BaseMVPView
    {
        void showNextFragment(String startDate, String endDate);

    }
    interface Action{

    }
}
