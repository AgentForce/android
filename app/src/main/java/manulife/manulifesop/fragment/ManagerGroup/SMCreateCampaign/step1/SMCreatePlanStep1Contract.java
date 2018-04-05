package manulife.manulifesop.fragment.ManagerGroup.SMCreateCampaign.step1;

import manulife.manulifesop.base.BaseMVPView;


/**
 * Created by Chick on 10/27/2017.
 */

public interface SMCreatePlanStep1Contract {

    interface View extends BaseMVPView {
        void showDatePicker(final String type);
    }

    interface Action {

    }
}
