package manulife.manulifesop.activity.FAGroup.clients.signed;

import manulife.manulifesop.base.BaseMVPView;

/**
 * Created by trinm on 12/01/2018.
 */

public interface SignedPersonContract {
    interface View extends BaseMVPView {
        void initViewPager();
        int getSelectedType();
    }

    interface Action {
        void getAllData(int period);
    }
}
