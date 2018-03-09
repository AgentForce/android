package manulife.manulifesop.fragment.FAGroup.clients.consultant;


import manulife.manulifesop.base.BasePresenter;
import manulife.manulifesop.fragment.FAGroup.clients.appointment.AppointmentContactTabContract;

/**
 * Created by Chick on 10/27/2017.
 */

public class ConsultantContactTabPresent extends BasePresenter<ConsultantContactTabContract.View> implements ConsultantContactTabContract.Action{
    public ConsultantContactTabPresent(ConsultantContactTabContract.View presenterView) {
        super(presenterView);
    }

}
