package manulife.manulifesop.fragment.FAGroup.clients.related.contactDetail.step1;

import manulife.manulifesop.base.BaseMVPView;


/**
 * Created by Chick on 10/27/2017.
 */

public interface ContactDetailStep1Contract {

    interface View extends BaseMVPView {
        void initViews();
        void showXLetter(boolean isShow);
    }

    interface Action {
        void callContact(String phone);
    }
}
