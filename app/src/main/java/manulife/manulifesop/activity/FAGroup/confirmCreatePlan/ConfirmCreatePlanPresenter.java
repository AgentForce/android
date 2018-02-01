package manulife.manulifesop.activity.FAGroup.confirmCreatePlan;

import android.content.Context;

import manulife.manulifesop.activity.FAGroup.createPlan.CreatePlanContract;
import manulife.manulifesop.base.BasePresenter;

/**
 * Created by trinm on 12/01/2018.
 */

public class ConfirmCreatePlanPresenter extends BasePresenter<ConfirmCreatePlanContract.View> implements ConfirmCreatePlanContract.Action {

    private Context mContext;

    public ConfirmCreatePlanPresenter(ConfirmCreatePlanContract.View presenterView, Context context) {
        super(presenterView);
        this.mContext = context;
    }
}
