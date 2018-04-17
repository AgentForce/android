package manulife.manulifesop.fragment.ManagerGroup.recruiment.manageRecruitment.content.contentDetail;


import android.content.Context;

import manulife.manulifesop.base.BasePresenter;

/**
 * Created by Chick on 10/27/2017.
 */

public class ContentDetailManageEmployPresent extends BasePresenter<ContentDetailManageEmployContract.View> implements ContentDetailManageEmployContract.Action {

    private Context mContext;
    private int mMonth;

    public ContentDetailManageEmployPresent(ContentDetailManageEmployContract.View presenterView, Context context) {
        super(presenterView);
        this.mContext = context;
    }
}
