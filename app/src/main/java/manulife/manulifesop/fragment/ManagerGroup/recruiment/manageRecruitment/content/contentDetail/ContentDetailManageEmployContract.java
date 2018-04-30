package manulife.manulifesop.fragment.ManagerGroup.recruiment.manageRecruitment.content.contentDetail;

import java.util.List;

import manulife.manulifesop.adapter.ObjectData.RecruitmentDirectlyData;
import manulife.manulifesop.api.ObjectResponse.RecruitmentDashboard;
import manulife.manulifesop.base.BaseMVPView;


/**
 * Created by Chick on 10/27/2017.
 */

public interface ContentDetailManageEmployContract {

    interface View extends BaseMVPView {
        void initviewsHeight();
        void initHeightViaSelected();

        void showRecruitDashboard(RecruitmentDashboard data);
        void showRecruitDashboardDirectly(List<RecruitmentDirectlyData> data, int currentPage, int lastPage);

        int getUserIDProcessing();
    }

    interface Action {
        void getRecruitDashBoard(int userID, int month, int week);
        void getRecruitDirectlyDashboard(int userID, int month, int week, int page);
    }
}
