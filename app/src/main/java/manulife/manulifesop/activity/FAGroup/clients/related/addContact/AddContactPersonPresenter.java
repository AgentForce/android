package manulife.manulifesop.activity.FAGroup.clients.related.addContact;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import manulife.manulifesop.adapter.ObjectData.ContactPerson;
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

        getContacts().observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribe(contactPeople -> {
                    mPresenterView.loadDateList(contactPeople);
                });
    }

    private Observable<List<ContactPerson>> getContacts() {
        final Observable<List<ContactPerson>> getContactsObservable = Observable.create(emitter -> {

            List<ContactPerson> dataContacts = new ArrayList<>();
            List<String> groupFirstCharacter = new ArrayList<>();
            Cursor phones = mContext.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);

            //add contact to group
            while (phones.moveToNext()) {
                String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                if (dataContacts.size() > 0) {
                    if (!groupFirstCharacter.contains(name.substring(0, 1).toLowerCase())) {
                        groupFirstCharacter.add(name.substring(0, 1).toLowerCase());
                    }
                    dataContacts.add(new ContactPerson(false, "", name, phoneNumber, groupFirstCharacter.indexOf(name.substring(0, 1).toLowerCase())));
                } else {
                    dataContacts.add(new ContactPerson(false, "", name, phoneNumber, 0));
                    groupFirstCharacter.add(name.substring(0, 1).toLowerCase());
                }
            }
            phones.close();
            //short group
            ContactPerson tmp;
            int groupPosition = -1;
            int num = 0;
            for (int i = 0; i < dataContacts.size(); i += num) {
                num = 0;
                int vt = i;
                groupPosition++;
                for (int j = i; j < dataContacts.size(); j++) {
                    if (dataContacts.get(j).getHeaderGroup() == groupPosition) {
                        tmp = dataContacts.get(vt);
                        dataContacts.set(vt, dataContacts.get(j));
                        dataContacts.set(j, tmp);
                        num++;
                        vt++;
                    }
                }
            }
            emitter.onNext(dataContacts);
            emitter.onComplete();
        });

        return getContactsObservable;
    }
}
