package manulife.manulifesop.activity.FAGroup.clients.appointment;

import manulife.manulifesop.base.BaseMVPView;

/**
 * Created by trinm on 12/01/2018.
 */

public interface AppointmentContract {
    interface View extends BaseMVPView
    {
        void initViewPager();
        int getSelectedType();
    }
    interface Action{
        void loadAllData(int period);
    }
}
