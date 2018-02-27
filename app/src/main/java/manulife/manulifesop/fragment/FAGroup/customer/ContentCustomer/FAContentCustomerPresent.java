package manulife.manulifesop.fragment.FAGroup.customer.ContentCustomer;


import manulife.manulifesop.base.BasePresenter;
import manulife.manulifesop.fragment.FAGroup.customer.FACustomerContract;

/**
 * Created by Chick on 10/27/2017.
 */

public class FAContentCustomerPresent extends BasePresenter<FAContentCustomerContract.View> implements FAContentCustomerContract.Action{
    public FAContentCustomerPresent(FAContentCustomerContract.View presenterView) {
        super(presenterView);
    }

}
