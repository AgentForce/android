package manulife.manulifesop.activity.FAGroup.clients.appointment;

import android.content.Context;

import manulife.manulifesop.base.BasePresenter;

/**
 * Created by trinm on 12/01/2018.
 */

public class AppointmentPresenter extends BasePresenter<AppointmentContract.View> implements AppointmentContract.Action {

    private Context mContext;

    public AppointmentPresenter(AppointmentContract.View presenterView, Context context) {
        super(presenterView);
        this.mContext = context;
    }
}
