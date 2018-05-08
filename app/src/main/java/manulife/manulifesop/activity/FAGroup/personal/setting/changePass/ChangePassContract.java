package manulife.manulifesop.activity.FAGroup.personal.setting.changePass;

import manulife.manulifesop.base.BaseMVPView;

/**
 * Created by trinm on 12/01/2018.
 */

public interface ChangePassContract {
    interface View extends BaseMVPView {
        void finishChangePass();
    }

    interface Action {
        boolean validateData(String oldPass, String pass, String passConfirm);
        void changePass(String oldPass, String newPass);
    }
}
