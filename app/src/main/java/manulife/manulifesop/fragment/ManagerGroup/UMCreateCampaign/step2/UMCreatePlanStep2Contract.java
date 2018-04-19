package manulife.manulifesop.fragment.ManagerGroup.UMCreateCampaign.step2;

import manulife.manulifesop.api.ObjectResponse.UMForcastRecruit;
import manulife.manulifesop.base.BaseMVPView;


/**
 * Created by Chick on 10/27/2017.
 */

public interface UMCreatePlanStep2Contract {

    interface View extends BaseMVPView {
        void showData(UMForcastRecruit data);
        void updateData(UMForcastRecruit data, int month);
        void getData();
    }

    interface Action {
        void getUmForcastRecruit();
    }
}
