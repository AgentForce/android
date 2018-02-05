package manulife.manulifesop.fragment.login.otpInput;

import manulife.manulifesop.base.BaseMVPView;


/**
 * Created by Chick on 10/27/2017.
 */

public interface OTPContract {

    interface View extends BaseMVPView {
        void disableRequestButton(boolean isDisable);
        void showFragmentCreatePass();
    }

    interface Action {
        void requestOTP(String user, String phone);
        void verifyOTP(String user,String phone,String otp);
    }
}
