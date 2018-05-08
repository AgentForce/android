package manulife.manulifesop.activity.FAGroup.personal.setting.notificationMethod;

import manulife.manulifesop.api.ObjectResponse.NotiConfig;
import manulife.manulifesop.base.BaseMVPView;

/**
 * Created by trinm on 12/01/2018.
 */

public interface NotiMethodContract {
    interface View extends BaseMVPView {
        void showData(NotiConfig data);
    }

    interface Action {
        void getNotiConfig();
        void updateNotiConfig(boolean sms, boolean email);
    }
}
