package manulife.manulifesop.fragment.FAGroup.confirmCreatePlan;


import manulife.manulifesop.base.BasePresenter;
import manulife.manulifesop.fragment.FAGroup.createPlane.step1.CreatePlanStep1Contract;

/**
 * Created by Chick on 10/27/2017.
 */

public class ConfirmCreatePlanPresent extends BasePresenter<ConfirmCreatePlanContract.View> implements ConfirmCreatePlanContract.Action{
    public ConfirmCreatePlanPresent(ConfirmCreatePlanContract.View presenterView) {
        super(presenterView);
    }

}
