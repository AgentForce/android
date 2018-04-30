package manulife.manulifesop.fragment.ManagerGroup.manageSale.content.contentDetail;

import java.util.List;

import manulife.manulifesop.adapter.ObjectData.RecruitmentDirectlyData;
import manulife.manulifesop.api.ObjectResponse.RecruitmentDashboard;
import manulife.manulifesop.api.ObjectResponse.SaleDashboard;
import manulife.manulifesop.base.BaseMVPView;


/**
 * Created by Chick on 10/27/2017.
 */

public interface ContentDetailManageSaleContract {

    interface View extends BaseMVPView {
        void initviewsHeight();
        void initHeightViaSelected();

        void showSaleDashboard(SaleDashboard data);
        void showRecruitDashboardDirectly(List<RecruitmentDirectlyData> data, int currentPage, int lastPage);

        int getUserIDProcessing();
    }

    interface Action {
        void getSaleDashBoard(int userID, int month, int week);
        void getSaleDirectlyDashboard(int userID, int month, int week, int page);
    }
}
