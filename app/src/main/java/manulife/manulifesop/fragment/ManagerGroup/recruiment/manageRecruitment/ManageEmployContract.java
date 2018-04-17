package manulife.manulifesop.fragment.ManagerGroup.recruiment.manageRecruitment;

import manulife.manulifesop.base.BaseMVPView;


/**
 * Created by Chick on 10/27/2017.
 */

public interface ManageEmployContract {

    interface View extends BaseMVPView {
        void initViewPager();
        int getSeletedPage();
    }

    interface Action {

    }
}
