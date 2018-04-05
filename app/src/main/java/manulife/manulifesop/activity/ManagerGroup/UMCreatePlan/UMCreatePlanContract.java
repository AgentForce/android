package manulife.manulifesop.activity.ManagerGroup.UMCreatePlan;

import manulife.manulifesop.base.BaseMVPView;

/**
 * Created by trinm on 12/01/2018.
 */

public interface UMCreatePlanContract {
    interface View extends BaseMVPView
    {
        void showNextFragment(String startDate, String endDate);

    }
    interface Action{

    }
}
