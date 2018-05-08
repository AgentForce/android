package manulife.manulifesop.activity.FAGroup.personal.activityNewsSetting;

import android.content.Context;

import manulife.manulifesop.activity.FAGroup.personal.activityHistSetting.ActivityHistSettingContract;
import manulife.manulifesop.base.BasePresenter;

/**
 * Created by trinm on 12/01/2018.
 */

public class ActivityNewsSettingPresenter extends BasePresenter<ActivityNewsSettingContract.View> implements ActivityNewsSettingContract.Action {

    private Context mContext;

    public ActivityNewsSettingPresenter(ActivityNewsSettingContract.View presenterView, Context context) {
        super(presenterView);
        this.mContext = context;
    }
}
