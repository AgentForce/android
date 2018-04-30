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
        void showCustomer(String title);
        void showEvents();
        void showPersonal();
        void showMenuSale();
        void showMenuEmploy();
        void showManageEmploy();
        void showManageSale();

        void showHideActionbar(boolean isShow);
        void updateActionbarTitle(String title);
        void showLogin();

        void showPersonalRecruiment();
    }
    interface Action{
        void chekCampaign();
        void refreshAccessToken();

        void testChecksum();
    }
}
