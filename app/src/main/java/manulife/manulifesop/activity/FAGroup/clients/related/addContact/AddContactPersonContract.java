package manulife.manulifesop.activity.FAGroup.clients.related.addContact;

import java.util.List;

import manulife.manulifesop.adapter.ObjectData.ContactPerson;
import manulife.manulifesop.base.BaseMVPView;

/**
 * Created by trinm on 12/01/2018.
 */

public interface AddContactPersonContract {
    interface View extends BaseMVPView
    {
        void updateTitleBar(int num);
        void setCheckStusList(int position,boolean checked);
        void updateContactChoosed(ContactPerson data, boolean isAdd);
        int getSizeChoosed();

        void loadDateList(List<ContactPerson> data);
    }
    interface Action{
        void readAllContacts();
    }
}
