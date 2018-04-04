package manulife.manulifesop.fragment.FAGroup.createPlane.step4;

import manulife.manulifesop.api.ObjectResponse.CampaignForcastTarget;
import manulife.manulifesop.base.BaseMVPView;


/**
 * Created by Chick on 10/27/2017.
 */

public interface CreatePlanStep4Contract {

    interface View extends BaseMVPView {
        void showData(int income,CampaignForcastTarget data,int monthNum);
    }

    interface Action {

    }
}
