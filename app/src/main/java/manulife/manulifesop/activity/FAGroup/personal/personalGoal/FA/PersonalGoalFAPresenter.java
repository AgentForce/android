package manulife.manulifesop.activity.FAGroup.personal.personalGoal.FA;

import android.content.Context;

import manulife.manulifesop.base.BasePresenter;

/**
 * Created by trinm on 12/01/2018.
 */

public class PersonalGoalFAPresenter extends BasePresenter<PersonalGoalFAContract.View> implements PersonalGoalFAContract.Action {

    private Context mContext;

    public PersonalGoalFAPresenter(PersonalGoalFAContract.View presenterView, Context context) {
        super(presenterView);
        this.mContext = context;
    }
}
