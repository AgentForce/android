package manulife.manulifesop.activity.FAGroup.clients.introduceContact;

import manulife.manulifesop.api.ObjectResponse.UsersList;
import manulife.manulifesop.base.BaseMVPView;

/**
 * Created by trinm on 12/01/2018.
 */

public interface IntroduceContactContract {
    interface View extends BaseMVPView
    {
        void initViewPager();
        void reloadData();
    }
    interface Action{
        void getAllIntroduces(int period, int page);
    }
}
