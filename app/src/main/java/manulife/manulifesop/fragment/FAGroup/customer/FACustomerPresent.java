package manulife.manulifesop.fragment.FAGroup.customer;


import manulife.manulifesop.base.BasePresenter;
import manulife.manulifesop.fragment.FAGroup.dashboard.FADashBoardContract;

/**
 * Created by Chick on 10/27/2017.
 */

public class FACustomerPresent extends BasePresenter<FACustomerContract.View> implements FACustomerContract.Action{
    public FACustomerPresent(FACustomerContract.View presenterView) {
        super(presenterView);
    }

}
