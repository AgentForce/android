package manulife.manulifesop.activity.FAGroup.clients.consultant;

import android.content.Context;

import manulife.manulifesop.activity.FAGroup.clients.appointment.AppointmentContract;
import manulife.manulifesop.base.BasePresenter;

/**
 * Created by trinm on 12/01/2018.
 */

public class ConsultantPresenter extends BasePresenter<ConsultantContract.View> implements ConsultantContract.Action {

    private Context mContext;

    public ConsultantPresenter(ConsultantContract.View presenterView, Context context) {
        super(presenterView);
        this.mContext = context;
    }
}
