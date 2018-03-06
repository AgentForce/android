package manulife.manulifesop.fragment.FAGroup.clients;


import manulife.manulifesop.base.BasePresenter;

/**
 * Created by Chick on 10/27/2017.
 */

public class FACustomerPresent extends BasePresenter<FACustomerContract.View> implements FACustomerContract.Action{
    public FACustomerPresent(FACustomerContract.View presenterView) {
        super(presenterView);
    }

}
