package manulife.manulifesop.fragment.ManagerGroup.menuDashBoard.menuEmploy;


import manulife.manulifesop.base.BasePresenter;
import manulife.manulifesop.fragment.ManagerGroup.menuDashBoard.menuSale.SMSaleMenuContract;

/**
 * Created by Chick on 10/27/2017.
 */

public class SMEmployMenuPresent extends BasePresenter<SMEmployMenuContract.View> implements SMEmployMenuContract.Action{
    public SMEmployMenuPresent(SMEmployMenuContract.View presenterView) {
        super(presenterView);
    }

}
