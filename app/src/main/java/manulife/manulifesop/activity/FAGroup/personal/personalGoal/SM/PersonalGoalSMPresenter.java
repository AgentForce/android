package manulife.manulifesop.activity.FAGroup.personal.personalGoal.SM;

import android.content.Context;

import manulife.manulifesop.activity.FAGroup.personal.personalGoal.FA.PersonalGoalFAContract;
import manulife.manulifesop.base.BasePresenter;

/**
 * Created by trinm on 12/01/2018.
 */

public class PersonalGoalSMPresenter extends BasePresenter<PersonalGoalSMContract.View> implements PersonalGoalSMContract.Action {

    private Context mContext;

    public PersonalGoalSMPresenter(PersonalGoalSMContract.View presenterView, Context context) {
        super(presenterView);
        this.mContext = context;
    }
}
