package manulife.manulifesop.fragment.dashboard;


import manulife.manulifesop.base.BasePresenter;
import manulife.manulifesop.fragment.FAGroup.confirmCreatePlan.ConfirmCreatePlanContract;

/**
 * Created by Chick on 10/27/2017.
 */

public class DashboardPresent extends BasePresenter<DashboardContract.View> implements DashboardContract.Action{
    public DashboardPresent(DashboardContract.View presenterView) {
        super(presenterView);
    }

}
