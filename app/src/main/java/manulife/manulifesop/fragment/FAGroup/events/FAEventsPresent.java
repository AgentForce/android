package manulife.manulifesop.fragment.FAGroup.events;


import manulife.manulifesop.api.ObjectResponse.DashboardResult;
import manulife.manulifesop.base.BasePresenter;

/**
 * Created by Chick on 10/27/2017.
 */

public class FAEventsPresent extends BasePresenter<FAEventsContract.View> implements FAEventsContract.Action {

    private DashboardResult mDataDashboardWeekMonth;
    private DashboardResult mDataDashboardYear;

    public FAEventsPresent(FAEventsContract.View presenterView) {
        super(presenterView);
    }

}
