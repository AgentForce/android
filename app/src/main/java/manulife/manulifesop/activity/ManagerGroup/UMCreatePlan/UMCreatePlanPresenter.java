package manulife.manulifesop.activity.ManagerGroup.UMCreatePlan;

import android.content.Context;

import manulife.manulifesop.activity.ManagerGroup.SMCreatePlan.SMCreatePlanContract;
import manulife.manulifesop.base.BasePresenter;

/**
 * Created by trinm on 12/01/2018.
 */

public class UMCreatePlanPresenter extends BasePresenter<UMCreatePlanContract.View> implements UMCreatePlanContract.Action {

    private Context mContext;

    public UMCreatePlanPresenter(UMCreatePlanContract.View presenterView, Context context) {
        super(presenterView);
        this.mContext = context;
    }
}
