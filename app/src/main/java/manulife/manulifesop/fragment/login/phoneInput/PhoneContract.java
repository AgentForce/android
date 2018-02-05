package manulife.manulifesop.fragment.login.phoneInput;

import manulife.manulifesop.base.BaseMVPView;


/**
 * Created by Chick on 10/27/2017.
 */

public interface PhoneContract {

    interface View extends BaseMVPView {
        void showFragmentOTPInput();
        void showFragmentPassInput();
    }

    interface Action {
        void checkUser(String user,String phone);
    }
}
