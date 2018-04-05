package manulife.manulifesop.fragment.ManagerGroup.SMCreateCampaign.step1;


import manulife.manulifesop.base.BasePresenter;
import manulife.manulifesop.fragment.FAGroup.createPlane.step1.CreatePlanStep1Contract;

/**
 * Created by Chick on 10/27/2017.
 */

public class SMCreatePlanStep1Present extends BasePresenter<SMCreatePlanStep1Contract.View> implements SMCreatePlanStep1Contract.Action{
    public SMCreatePlanStep1Present(SMCreatePlanStep1Contract.View presenterView) {
        super(presenterView);
    }

}
