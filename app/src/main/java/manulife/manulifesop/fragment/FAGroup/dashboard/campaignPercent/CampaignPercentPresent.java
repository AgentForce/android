package manulife.manulifesop.fragment.FAGroup.dashboard.campaignPercent;


import manulife.manulifesop.base.BasePresenter;
import manulife.manulifesop.fragment.FAGroup.confirmCreatePlan.ConfirmCreatePlanContract;

/**
 * Created by Chick on 10/27/2017.
 */

public class CampaignPercentPresent extends BasePresenter<CampaignPercentContract.View> implements CampaignPercentContract.Action{
    public CampaignPercentPresent(CampaignPercentContract.View presenterView) {
        super(presenterView);
    }

}
