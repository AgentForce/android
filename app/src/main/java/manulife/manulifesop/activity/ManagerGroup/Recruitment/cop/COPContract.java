package manulife.manulifesop.activity.ManagerGroup.Recruitment.cop;

import manulife.manulifesop.base.BaseMVPView;

/**
 * Created by trinm on 12/01/2018.
 */

public interface COPContract {
    interface View extends BaseMVPView
    {
        void initViewPager();
        int getSelectedType();
    }
    interface Action{
        void loadAllData(int period);
    }
}
