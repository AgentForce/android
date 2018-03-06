package manulife.manulifesop.activity.FAGroup.clients.contact;

import android.content.Context;

import manulife.manulifesop.base.BasePresenter;

/**
 * Created by trinm on 12/01/2018.
 */

public class ContactPersonPresenter extends BasePresenter<ContactPersonContract.View> implements ContactPersonContract.Action {

    private Context mContext;

    public ContactPersonPresenter(ContactPersonContract.View presenterView, Context context) {
        super(presenterView);
        this.mContext = context;
    }
}
