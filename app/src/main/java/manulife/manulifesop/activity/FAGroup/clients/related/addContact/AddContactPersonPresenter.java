package manulife.manulifesop.activity.FAGroup.clients.related.addContact;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import manulife.manulifesop.adapter.ObjectData.ContactPerson;
import manulife.manulifesop.base.BasePresenter;
import manulife.manulifesop.util.SOPSharedPreferences;

/**
 * Created by trinm on 12/01/2018.
 */

public class AddContactPersonPresenter extends BasePresenter<AddContactPersonContract.View> implements AddContactPersonContract.Action {

    private Context mContext;

    public AddContactPersonPresenter(AddContactPersonContract.View presenterView, Context context) {
        super(presenterView);
        this.mContext = context;
    }

    public boolean isPermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            ArrayList<String> temp = new ArrayList<>();
            //READ_PHONE_STATE
            if (mContext.checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                temp.add(Manifest.permission.READ_PHONE_STATE);
            }
            //READ_CONTACTS
            if (mContext.checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                temp.add(Manifest.permission.READ_CONTACTS);
            }
            //CALL_PHONE
            if (mContext.checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                temp.add(Manifest.permission.CALL_PHONE);
            }
            //READ_CALENDAR
            if (mContext.checkSelfPermission(Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
                temp.add(Manifest.permission.READ_CALENDAR);
            }
            //WRITE_CALENDAR
            if (mContext.checkSelfPermission(Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
                temp.add(Manifest.permission.WRITE_CALENDAR);
            }

            if (temp.size() > 0) {
                String permissions[] = new String[temp.size()];
                for (int i = 0; i < temp.size(); i++) {
                    permissions[i] = temp.get(i);
                }
                ActivityCompat.requestPermissions((Activity) mContext, permissions, 2);
                return false;
            }
        }
        return true;
    }

    @Override
    public void readAllContacts() {

        if(isPermissionGranted()) {
            getContacts().observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .subscribe(contactPeople -> {
                        mPresenterView.loadDateList(contactPeople);
                    });
        }
    }

    private boolean isContainInList(List<String> data, String input) {
        input = input.trim().replace("-", "").replace("+", "")
                .replace(" ", "");
        boolean rs = false;
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).equals(input)) {
                rs = true;
                break;
            }
        }
        return rs;
    }

    private Observable<List<ContactPerson>> getContacts() {
        final Observable<List<ContactPerson>> getContactsObservable = Observable.create(emitter -> {

            //saved phone
            List<String> addedPhone = SOPSharedPreferences.getInstance(mContext).getListAddedPhone();
            if (addedPhone == null) addedPhone = new ArrayList<>();

            List<ContactPerson> dataContacts = new ArrayList<>();
            List<String> groupFirstCharacter = new ArrayList<>();
            Cursor phones = mContext.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);

            //add contact to group
            while (phones.moveToNext()) {
                String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                boolean isAdded = isContainInList(addedPhone, phoneNumber);

                if (dataContacts.size() > 0) {
                    if (!groupFirstCharacter.contains(name.substring(0, 1).toLowerCase())) {
                        groupFirstCharacter.add(name.substring(0, 1).toLowerCase());
                    }
                    dataContacts.add(new ContactPerson(false, "", name, phoneNumber,
                            groupFirstCharacter.indexOf(name.substring(0, 1).toLowerCase()),isAdded));
                } else {
                    dataContacts.add(new ContactPerson(false, "", name, phoneNumber, 0,isAdded));
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
