package manulife.manulifesop.fragment.FAGroup.contactPerson.tab1;


import manulife.manulifesop.base.BasePresenter;
import manulife.manulifesop.fragment.FAGroup.confirmCreatePlan.ConfirmCreatePlanContract;

/**
 * Created by Chick on 10/27/2017.
 */

public class ContactPersonTab1Present extends BasePresenter<ContactPersonTab1Contract.View> implements ContactPersonTab1Contract.Action{
    public ContactPersonTab1Present(ContactPersonTab1Contract.View presenterView) {
        super(presenterView);
    }

}