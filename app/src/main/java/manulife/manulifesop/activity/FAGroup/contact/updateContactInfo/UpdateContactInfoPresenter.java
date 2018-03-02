package manulife.manulifesop.activity.FAGroup.contact.updateContactInfo;

import android.content.Context;

import cn.pedant.SweetAlert.SweetAlertDialog;
import manulife.manulifesop.activity.FAGroup.contact.ContactPersonContract;
import manulife.manulifesop.base.BasePresenter;

/**
 * Created by trinm on 12/01/2018.
 */

public class UpdateContactInfoPresenter extends BasePresenter<UpdateContactInfoContract.View> implements UpdateContactInfoContract.Action {

    private Context mContext;

    public UpdateContactInfoPresenter(UpdateContactInfoContract.View presenterView, Context context) {
        super(presenterView);
        this.mContext = context;
    }

    @Override
    public void updateContactInfo(int position) {
        mPresenterView.showLoading("Cập nhật thông tin người thứ" + (position + 1));
        mPresenterView.finishLoading();
        mPresenterView.loadNextContact(position + 1);
    }
}
