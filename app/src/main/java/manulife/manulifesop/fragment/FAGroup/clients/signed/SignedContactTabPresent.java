package manulife.manulifesop.fragment.FAGroup.clients.signed;


import manulife.manulifesop.base.BasePresenter;
import manulife.manulifesop.fragment.FAGroup.clients.consultant.ConsultantContactTabContract;

/**
 * Created by Chick on 10/27/2017.
 */

public class SignedContactTabPresent extends BasePresenter<SignedContactTabContract.View> implements SignedContactTabContract.Action{
    public SignedContactTabPresent(SignedContactTabContract.View presenterView) {
        super(presenterView);
    }

}
