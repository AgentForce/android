package manulife.manulifesop.fragment.FAGroup.personal;

import manulife.manulifesop.api.ObjectResponse.UserProfile;
import manulife.manulifesop.base.BaseMVPView;


/**
 * Created by Chick on 10/27/2017.
 */

public interface FAPersonalContract {

    interface View extends BaseMVPView {
        void showData(UserProfile data);
    }

    interface Action {
        void getUserProfile(String userName);
    }
}
