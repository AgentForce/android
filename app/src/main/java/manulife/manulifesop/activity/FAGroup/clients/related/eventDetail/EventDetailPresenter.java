package manulife.manulifesop.activity.FAGroup.clients.related.eventDetail;

import android.content.Context;

import manulife.manulifesop.base.BasePresenter;

/**
 * Created by trinm on 12/01/2018.
 */

public class EventDetailPresenter extends BasePresenter<EventDetailContract.View> implements EventDetailContract.Action {

    private Context mContext;

    public EventDetailPresenter(EventDetailContract.View presenterView, Context context) {
        super(presenterView);
        this.mContext = context;
    }
}
