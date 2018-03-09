package manulife.manulifesop.activity.FAGroup.clients.signed;

import android.content.Context;

import manulife.manulifesop.activity.FAGroup.clients.contact.ContactPersonContract;
import manulife.manulifesop.base.BasePresenter;

/**
 * Created by trinm on 12/01/2018.
 */

public class SignedPersonPresenter extends BasePresenter<SignedPersonContract.View> implements SignedPersonContract.Action {

    private Context mContext;

    public SignedPersonPresenter(SignedPersonContract.View presenterView, Context context) {
        super(presenterView);
        this.mContext = context;
    }
}
