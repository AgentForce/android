package manulife.manulifesop.activity.ManagerGroup.Recruitment.mit;

import manulife.manulifesop.base.BaseMVPView;

/**
 * Created by trinm on 12/01/2018.
 */

public interface MITContract {
    interface View extends BaseMVPView {
        void initViewPager();
        int getSelectedType();
    }

    interface Action {
        void getAllData(int period);
    }
}
