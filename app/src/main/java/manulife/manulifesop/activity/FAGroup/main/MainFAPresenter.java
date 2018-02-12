package manulife.manulifesop.activity.FAGroup.main;

import android.content.Context;

import cn.pedant.SweetAlert.SweetAlertDialog;
import manulife.manulifesop.activity.FAGroup.createPlan.CreatePlanContract;
import manulife.manulifesop.base.BasePresenter;

/**
 * Created by trinm on 12/01/2018.
 */

public class MainFAPresenter extends BasePresenter<MainFAContract.View> implements MainFAContract.Action {

    private Context mContext;

    public MainFAPresenter(MainFAContract.View presenterView, Context context) {
        super(presenterView);
        this.mContext = context;
    }

    @Override
    public void checkIsGetCampaign(boolean isGetCampaign) {
        if (isGetCampaign) {
            mPresenterView.showDashBoard();
        } else {
            //if is not campaign
            mPresenterView.showFragmentConfirmCreatePlan();
        }
    }
}
