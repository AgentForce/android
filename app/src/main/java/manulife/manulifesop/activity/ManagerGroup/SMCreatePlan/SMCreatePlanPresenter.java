package manulife.manulifesop.activity.ManagerGroup.SMCreatePlan;

import android.content.Context;

import manulife.manulifesop.base.BasePresenter;

/**
 * Created by trinm on 12/01/2018.
 */

public class SMCreatePlanPresenter extends BasePresenter<SMCreatePlanContract.View> implements SMCreatePlanContract.Action {

    private Context mContext;

    public SMCreatePlanPresenter(SMCreatePlanContract.View presenterView, Context context) {
        super(presenterView);
        this.mContext = context;
    }
}
