package manulife.manulifesop.fragment.FAGroup.dashboardv2.campaignPercent;

import manulife.manulifesop.base.BaseMVPView;


/**
 * Created by Chick on 10/27/2017.
 */

public interface FACampaignPercentContract {

    interface View extends BaseMVPView {
        void showDialogEditCampaign();
        void showDialogEditObjectMonth();
        void updateData();
    }

    interface Action {
        void updateCampaignWeek(int month,int contractW1,int contractW2,int contractW3,int contractW4);
        void increaseContactCampaign(int month, int increaseNumber);
    }
}
