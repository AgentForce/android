package manulife.manulifesop.fragment.ManagerGroup.manageSale.content;


import android.content.Context;

import manulife.manulifesop.base.BasePresenter;

/**
 * Created by Chick on 10/27/2017.
 */

public class ContentManageSalePresent extends BasePresenter<ContentManageSaleContract.View> implements ContentManageSaleContract.Action {

    private Context mContext;
    private int mMonth;

    public ContentManageSalePresent(ContentManageSaleContract.View presenterView, Context context) {
        super(presenterView);
        this.mContext = context;
    }
}
