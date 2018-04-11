package manulife.manulifesop.fragment.ManagerGroup.dashboard;

import manulife.manulifesop.api.ObjectResponse.ActivitiHist;
import manulife.manulifesop.api.ObjectResponse.DashboardResult;
import manulife.manulifesop.base.BaseMVPView;


/**
 * Created by Chick on 10/27/2017.
 */

public interface SMDashBoardContract {

    interface View extends BaseMVPView {
        void showDataDashboard(DashboardResult dataWeekMonth, DashboardResult dataYear, ActivitiHist activities);
        void showACtivities(ActivitiHist activities);
        void updateData();
        void showMenuCreateEvent();

        void initViewHeight();
    }

    interface Action {
        void getDataDashboard();
        void getActivities(int page);
        void forwardCampaign();
    }
}
