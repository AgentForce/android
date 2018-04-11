package manulife.manulifesop.fragment.ManagerGroup.manageEmploy.content;


import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import manulife.manulifesop.BuildConfig;
import manulife.manulifesop.ProjectApplication;
import manulife.manulifesop.api.ApiService;
import manulife.manulifesop.api.ObjectInput.InputChangeCampaignWeek;
import manulife.manulifesop.api.ObjectInput.InputIncreaseContact;
import manulife.manulifesop.api.ObjectResponse.BaseResponse;
import manulife.manulifesop.api.ObjectResponse.CampaignMonth;
import manulife.manulifesop.base.BaseFragment;
import manulife.manulifesop.base.BasePresenter;
import manulife.manulifesop.fragment.FAGroup.clients.ContentClient.ContactMonth.FAContactMonthFragment;
import manulife.manulifesop.fragment.FAGroup.clients.ContentClient.FAContentCustomerContract;
import manulife.manulifesop.fragment.FAGroup.clients.ContentClient.ObjectMonth.FAObjectMonthFragment;
import manulife.manulifesop.fragment.FAGroup.clients.ContentClient.ObjectWeek.FAObjectWeekFragment;
import manulife.manulifesop.util.Contants;
import manulife.manulifesop.util.DeviceInfo;
import manulife.manulifesop.util.SOPSharedPreferences;

/**
 * Created by Chick on 10/27/2017.
 */

public class ContentManageEmployPresent extends BasePresenter<ContentManageEmployContract.View> implements ContentManageEmployContract.Action {

    private Context mContext;
    private int mMonth;

    public ContentManageEmployPresent(ContentManageEmployContract.View presenterView, Context context) {
        super(presenterView);
        this.mContext = context;
    }
}
