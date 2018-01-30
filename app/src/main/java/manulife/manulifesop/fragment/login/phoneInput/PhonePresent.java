package manulife.manulifesop.fragment.login.phoneInput;


import manulife.manulifesop.base.BasePresenter;
import manulife.manulifesop.fragment.login.agencyInput.AgencyContract;

/**
 * Created by Chick on 10/27/2017.
 */

public class PhonePresent extends BasePresenter<PhoneContract.View> implements PhoneContract.Action{
    public PhonePresent(PhoneContract.View presenterView) {
        super(presenterView);
    }

}
