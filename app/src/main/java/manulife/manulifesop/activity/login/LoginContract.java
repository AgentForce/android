package manulife.manulifesop.activity.login;


import android.content.Context;
import android.widget.EditText;

import manulife.manulifesop.base.BaseFragment;
import manulife.manulifesop.base.BaseMVPView;

/**
 * Created by Chick on 10/27/2017.
 */

public interface LoginContract {
    interface View extends BaseMVPView {
        void showFragment(BaseFragment fragment,boolean isNext);
        void showFragmentPhoneInput(String agencyID);
        void showFragmentPassInput();//user is actived
        void showFragmentOTPInput();
        void showFragmentCreatePass();
        void showFragmentConfirmPass(String createPass);

        void showCreatePass(String pass);
        void showLogin(String pass);
        void showMainFAActvity();
        void showConfirmCreatePlan();
    }

    interface Action {

        void createPass(String user, String pass);
        void login(String userName, String pass);

        void checkPermissionGranted();
        void getDeviceInfo(Context context);
    }
}
