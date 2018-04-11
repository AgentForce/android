package manulife.manulifesop.fragment.ManagerGroup.menuDashBoard.menuSale;


import manulife.manulifesop.base.BasePresenter;
import manulife.manulifesop.fragment.ManagerGroup.SMCreateCampaign.step1.SMCreatePlanStep1Contract;

/**
 * Created by Chick on 10/27/2017.
 */

public class SMSaleMenuPresent extends BasePresenter<SMSaleMenuContract.View> implements SMSaleMenuContract.Action{
    public SMSaleMenuPresent(SMSaleMenuContract.View presenterView) {
        super(presenterView);
    }

}
