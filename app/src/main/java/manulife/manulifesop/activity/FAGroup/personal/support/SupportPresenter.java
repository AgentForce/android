package manulife.manulifesop.activity.FAGroup.personal.support;

import android.content.Context;

import manulife.manulifesop.activity.FAGroup.personal.activityHistSetting.ActivityHistSettingContract;
import manulife.manulifesop.base.BasePresenter;

/**
 * Created by trinm on 12/01/2018.
 */

public class SupportPresenter extends BasePresenter<SupportContract.View> implements SupportContract.Action {

    private Context mContext;

    public SupportPresenter(SupportContract.View presenterView, Context context) {
        super(presenterView);
        this.mContext = context;
    }
}
