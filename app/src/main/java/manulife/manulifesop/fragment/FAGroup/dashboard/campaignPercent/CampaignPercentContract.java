package manulife.manulifesop.fragment.FAGroup.dashboard.campaignPercent;

import manulife.manulifesop.base.BaseMVPView;


/**
 * Created by Chick on 10/27/2017.
 */

public interface CampaignPercentContract {

    interface View extends BaseMVPView {
        void updateData();
    }

    interface Action {
        void updateCampaignWeek(int month, int contractW1, int contractW2, int contractW3, int contractW4);
    }
}
