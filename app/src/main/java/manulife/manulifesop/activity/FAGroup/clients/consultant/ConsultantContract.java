package manulife.manulifesop.activity.FAGroup.clients.consultant;

import manulife.manulifesop.base.BaseMVPView;

/**
 * Created by trinm on 12/01/2018.
 */

public interface ConsultantContract {
    interface View extends BaseMVPView {
        void initViewPager();
        int getSelectedType();
    }

    interface Action {
        void getAllData(int period);
    }
}
