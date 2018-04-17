package manulife.manulifesop.activity.ManagerGroup.Recruitment.introduceRecruitment;

import manulife.manulifesop.base.BaseMVPView;

/**
 * Created by trinm on 12/01/2018.
 */

public interface IntroduceRecruitmentContract {
    interface View extends BaseMVPView
    {
        void initViewPager();
        void reloadData();
    }
    interface Action{
        void getAllIntroduces(String search, int period, int page);
    }
}
