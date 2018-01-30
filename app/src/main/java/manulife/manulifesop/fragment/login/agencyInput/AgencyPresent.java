package manulife.manulifesop.fragment.login.agencyInput;


import manulife.manulifesop.base.BasePresenter;
import manulife.manulifesop.fragment.FAGroup.createPlane.step2.CreatePlanStep2Contract;

/**
 * Created by Chick on 10/27/2017.
 */

public class AgencyPresent extends BasePresenter<AgencyContract.View> implements AgencyContract.Action{
    public AgencyPresent(AgencyContract.View presenterView) {
        super(presenterView);
    }

}
