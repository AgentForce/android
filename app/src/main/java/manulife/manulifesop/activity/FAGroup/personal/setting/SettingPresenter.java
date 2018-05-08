package manulife.manulifesop.activity.FAGroup.personal.setting;

import android.content.Context;

import manulife.manulifesop.activity.FAGroup.personal.personalInfo.PersonalInfoContract;
import manulife.manulifesop.base.BasePresenter;

/**
 * Created by trinm on 12/01/2018.
 */

public class SettingPresenter extends BasePresenter<SettingContract.View> implements SettingContract.Action {

    private Context mContext;

    public SettingPresenter(SettingContract.View presenterView, Context context) {
        super(presenterView);
        this.mContext = context;
    }
}
