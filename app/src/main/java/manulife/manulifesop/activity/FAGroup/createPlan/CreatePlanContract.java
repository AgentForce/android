package manulife.manulifesop.activity.FAGroup.createPlan;

import manulife.manulifesop.api.ObjectResponse.CampaignForcastTarget;
import manulife.manulifesop.base.BaseMVPView;

/**
 * Created by trinm on 12/01/2018.
 */

public interface CreatePlanContract {
    interface View extends BaseMVPView
    {
        void showNextFragment(int contractNum,String startDate, String endDate,int income,int contractPrice, int profit);
        void showCreateCampaign();
        void showSuccessView();
        void showForcast(CampaignForcastTarget data);
    }
    interface Action{
        void createCampaign(String startDate, String endDate, long profit, long contractPrice, long inCome);
        void getCampaignForcast(int income,int profit,int contractPrice,String startDate, String endDate);
    }
}
