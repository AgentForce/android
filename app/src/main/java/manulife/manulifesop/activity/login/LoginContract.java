package manulife.manulifesop.activity.login;


import android.content.Context;
import android.widget.EditText;

import manulife.manulifesop.base.BaseMVPView;

/**
 * Created by Chick on 10/27/2017.
 */

public interface LoginContract {
    interface View extends BaseMVPView {
        void showWelcomeUser();
        void showMainDashboard();
    }

    interface Action {
        void loginApp(EditText user, EditText pass);
        void checkPermissionGranted();
        void getDeviceInfo(Context context);
    }
}
