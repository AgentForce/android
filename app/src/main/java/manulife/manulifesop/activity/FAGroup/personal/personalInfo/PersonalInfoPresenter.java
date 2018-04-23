package manulife.manulifesop.activity.FAGroup.personal.personalInfo;

import android.content.Context;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import manulife.manulifesop.BuildConfig;
import manulife.manulifesop.activity.FAGroup.confirmCreatePlan.ConfirmCreatePlanContract;
import manulife.manulifesop.api.ApiService;
import manulife.manulifesop.api.ObjectResponse.UserProfile;
import manulife.manulifesop.base.BasePresenter;
import manulife.manulifesop.util.Contants;
import manulife.manulifesop.util.DeviceInfo;
import manulife.manulifesop.util.SOPSharedPreferences;

/**
 * Created by trinm on 12/01/2018.
 */

public class PersonalInfoPresenter extends BasePresenter<PersonalInfoContract.View> implements PersonalInfoContract.Action {

    private Context mContext;

    public PersonalInfoPresenter(PersonalInfoContract.View presenterView, Context context) {
        super(presenterView);
        this.mContext = context;
    }
}
