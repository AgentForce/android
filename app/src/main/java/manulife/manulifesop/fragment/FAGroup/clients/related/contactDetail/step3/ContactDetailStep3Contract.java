package manulife.manulifesop.fragment.FAGroup.clients.related.contactDetail.step3;

import manulife.manulifesop.api.ObjectResponse.ContactHistory;
import manulife.manulifesop.base.BaseMVPView;


/**
 * Created by Chick on 10/27/2017.
 */

public interface ContactDetailStep3Contract {

    interface View extends BaseMVPView {
        void loadData(ContactHistory data);
    }

    interface Action {
        void getContactHistory(int leadId, int page);
    }
}
