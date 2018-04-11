package manulife.manulifesop.fragment.ManagerGroup.manageEmploy;


import manulife.manulifesop.base.BasePresenter;
import manulife.manulifesop.fragment.dashboard.DashboardContract;

/**
 * Created by Chick on 10/27/2017.
 */

public class ManageEmployPresent extends BasePresenter<ManageEmployContract.View> implements ManageEmployContract.Action{
    public ManageEmployPresent(ManageEmployContract.View presenterView) {
        super(presenterView);
    }

}
