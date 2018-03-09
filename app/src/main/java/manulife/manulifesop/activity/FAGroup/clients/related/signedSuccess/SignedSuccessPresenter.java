package manulife.manulifesop.activity.FAGroup.clients.related.signedSuccess;

import android.content.Context;

import manulife.manulifesop.activity.FAGroup.clients.related.eventDetail.EventDetailContract;
import manulife.manulifesop.base.BasePresenter;

/**
 * Created by trinm on 12/01/2018.
 */

public class SignedSuccessPresenter extends BasePresenter<SignedSuccessContract.View> implements SignedSuccessContract.Action {

    private Context mContext;

    public SignedSuccessPresenter(SignedSuccessContract.View presenterView, Context context) {
        super(presenterView);
        this.mContext = context;
    }
}
