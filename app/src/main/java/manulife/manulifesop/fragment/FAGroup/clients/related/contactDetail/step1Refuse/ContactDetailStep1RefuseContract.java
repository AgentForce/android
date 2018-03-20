package manulife.manulifesop.fragment.FAGroup.clients.related.contactDetail.step1Refuse;

import manulife.manulifesop.api.ObjectInput.InputChangeContactStatus;
import manulife.manulifesop.base.BaseMVPView;


/**
 * Created by Chick on 10/27/2017.
 */

public interface ContactDetailStep1RefuseContract {

    interface View extends BaseMVPView {
        void initViews();
        void finishUpdateStatus();
    }

    interface Action {
        void changeStatusToOne(int leadID, boolean isChangeProcessStep, int status);
    }
}
