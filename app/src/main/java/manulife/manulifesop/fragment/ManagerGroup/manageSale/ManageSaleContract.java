package manulife.manulifesop.fragment.ManagerGroup.manageSale;

import manulife.manulifesop.base.BaseMVPView;


/**
 * Created by Chick on 10/27/2017.
 */

public interface ManageSaleContract {

    interface View extends BaseMVPView {
        void initViewPager();
        int getSeletedPage();
        int getUserIDProcessing();
    }

    interface Action {

    }
}
