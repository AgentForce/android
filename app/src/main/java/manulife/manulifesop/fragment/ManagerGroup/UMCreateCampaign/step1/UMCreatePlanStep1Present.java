package manulife.manulifesop.fragment.ManagerGroup.UMCreateCampaign.step1;


import manulife.manulifesop.base.BasePresenter;
import manulife.manulifesop.fragment.ManagerGroup.SMCreateCampaign.step1.SMCreatePlanStep1Contract;

/**
 * Created by Chick on 10/27/2017.
 */

public class UMCreatePlanStep1Present extends BasePresenter<UMCreatePlanStep1Contract.View> implements UMCreatePlanStep1Contract.Action{
    public UMCreatePlanStep1Present(UMCreatePlanStep1Contract.View presenterView) {
        super(presenterView);
    }

}
