package manulife.manulifesop.fragment.FAGroup.dashboardv2;

import manulife.manulifesop.api.ObjectResponse.ActivitiHist;
import manulife.manulifesop.api.ObjectResponse.DashboardResult;
import manulife.manulifesop.base.BaseMVPView;


/**
 * Created by Chick on 10/27/2017.
 */

public interface FADashBoardContract {

    interface View extends BaseMVPView {
        void showDataDashboard(DashboardResult dataWeekMonth, DashboardResult dataYear, ActivitiHist activities);
        void showACtivities(ActivitiHist activities);
        void updateData();
        void showMenuCreateEvent();

        void initViewHeight();

        void finishLoadingMulti();
    }

    interface Action {
        void getDataDashboard();
        void getActivities(int page);
        void forwardCampaign();
    }
}
