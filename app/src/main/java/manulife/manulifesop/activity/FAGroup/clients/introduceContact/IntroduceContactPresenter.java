package manulife.manulifesop.activity.FAGroup.clients.introduceContact;

import android.content.Context;

import manulife.manulifesop.base.BasePresenter;

/**
 * Created by trinm on 12/01/2018.
 */

public class IntroduceContactPresenter extends BasePresenter<IntroduceContactContract.View> implements IntroduceContactContract.Action {

    private Context mContext;

    public IntroduceContactPresenter(IntroduceContactContract.View presenterView, Context context) {
        super(presenterView);
        this.mContext = context;
    }
}
