package manulife.manulifesop.activity.first;

import android.content.Context;

import manulife.manulifesop.base.BaseMVPView;

/**
 * Created by trinm on 12/01/2018.
 */

public interface FirstContract {
    interface View extends BaseMVPView
    {
        void showWelcome();
        void showLogin();
    }
    interface Action{
        void checkInternetViaPingServer();
        void clickAgreeButton();
    }
}
