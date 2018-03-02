package manulife.manulifesop.activity.FAGroup.contact.addContact;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;

import manulife.manulifesop.activity.FAGroup.contact.ContactPersonContract;
import manulife.manulifesop.base.BasePresenter;

/**
 * Created by trinm on 12/01/2018.
 */

public class AddContactPersonPresenter extends BasePresenter<AddContactPersonContract.View> implements AddContactPersonContract.Action {

    private Context mContext;

    public AddContactPersonPresenter(AddContactPersonContract.View presenterView, Context context) {
        super(presenterView);
        this.mContext = context;
    }

    @Override
    public void readAllContacts() {
        Cursor phones = mContext.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null, null);
        while (phones.moveToNext())
        {
            String name=phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            Log.d("test",name + " " + phoneNumber);
        }
        phones.close();
    }
}
