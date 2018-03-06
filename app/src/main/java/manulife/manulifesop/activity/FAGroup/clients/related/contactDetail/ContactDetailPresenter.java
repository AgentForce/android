package manulife.manulifesop.activity.FAGroup.clients.related.contactDetail;

import android.content.Context;

import manulife.manulifesop.base.BasePresenter;

/**
 * Created by trinm on 12/01/2018.
 */

public class ContactDetailPresenter extends BasePresenter<ContactDetailContract.View> implements ContactDetailContract.Action {

    private Context mContext;

    public ContactDetailPresenter(ContactDetailContract.View presenterView, Context context) {
        super(presenterView);
        this.mContext = context;
    }
}
