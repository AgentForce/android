package manulife.manulifesop.activity.FAGroup.clients.related.createEvent;

import android.content.Context;

import manulife.manulifesop.base.BasePresenter;

/**
 * Created by trinm on 12/01/2018.
 */

public class CreateEventPresenter extends BasePresenter<CreateEventContract.View> implements CreateEventContract.Action {

    private Context mContext;

    public CreateEventPresenter(CreateEventContract.View presenterView, Context context) {
        super(presenterView);
        this.mContext = context;
    }
}
