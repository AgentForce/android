package manulife.manulifesop.fragment.dashboard;

import manulife.manulifesop.base.BaseMVPView;


/**
 * Created by Chick on 10/27/2017.
 */

public interface DashboardContract {

    interface View extends BaseMVPView {
        void initViewPagerFA();
        void initViewPagerSM();

        int getSelectedPage();

        void finishLoadingChild(int positionChild);
    }

    interface Action {

    }
}
