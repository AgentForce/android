package manulife.manulifesop.fragment.ManagerGroup.dashboard;

import manulife.manulifesop.api.ObjectResponse.ActivitiHist;
import manulife.manulifesop.api.ObjectResponse.DashboardResult;
import manulife.manulifesop.api.ObjectResponse.DashboardSMResult;
import manulife.manulifesop.api.ObjectResponse.RecruitHistory;
import manulife.manulifesop.base.BaseMVPView;


/**
 * Created by Chick on 10/27/2017.
 */

public interface SMDashBoardContract {

    interface View extends BaseMVPView {
        void showDataDashboard(DashboardSMResult dataWeekMonth, DashboardSMResult dataYear, RecruitHistory activities);
        void showACtivities(RecruitHistory activities);
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
