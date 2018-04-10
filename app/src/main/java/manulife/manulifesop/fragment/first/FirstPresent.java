package manulife.manulifesop.fragment.first;


import manulife.manulifesop.base.BasePresenter;
import manulife.manulifesop.fragment.login.agencyInput.AgencyContract;

/**
 * Created by Chick on 10/27/2017.
 */

public class FirstPresent extends BasePresenter<FirstContract.View> implements FirstContract.Action{
    public FirstPresent(FirstContract.View presenterView) {
        super(presenterView);
    }

}
