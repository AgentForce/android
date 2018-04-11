package manulife.manulifesop.activity.main;

import manulife.manulifesop.base.BaseMVPView;

/**
 * Created by trinm on 12/01/2018.
 */

public interface MainFAContract {
    interface View extends BaseMVPView
    {
        void showFragmentConfirmCreatePlan(String title);
        void showDashBoard();
        void showCustomer();
        void showEvents();
        void showPersonal();
        void showMenuSale();
        void showMenuEmploy();
        void showManageEmploy();

        void showHideActionbar(boolean isShow);
        void updateActionbarTitle(String title);
        void showLogin();
    }
    interface Action{
        void chekCampaign();
        void refreshAccessToken();
    }
}
