package manulife.manulifesop.fragment.ManagerGroup.recruiment.manageRecruitment.content;


import android.content.Context;

import manulife.manulifesop.base.BasePresenter;

/**
 * Created by Chick on 10/27/2017.
 */

public class ContentManageEmployPresent extends BasePresenter<ContentManageEmployContract.View> implements ContentManageEmployContract.Action {

    private Context mContext;
    private int mMonth;

    public ContentManageEmployPresent(ContentManageEmployContract.View presenterView, Context context) {
        super(presenterView);
        this.mContext = context;
    }
}
