package manulife.manulifesop.fragment.ManagerGroup.dashboard.SMcampaignPercent;

import manulife.manulifesop.base.BaseMVPView;


/**
 * Created by Chick on 10/27/2017.
 */

public interface SMCampaignPercentContract {

    interface View extends BaseMVPView {
        void showDialogEditCampaign();
        void showDialogEditObjectMonth();
        void updateData();
    }

    interface Action {
        void updateCampaignWeekSM(int month, int contractW1, int contractW2, int contractW3, int contractW4);
        void increaseAgentCampaignSM(int month, int increaseNumber);
    }
}
