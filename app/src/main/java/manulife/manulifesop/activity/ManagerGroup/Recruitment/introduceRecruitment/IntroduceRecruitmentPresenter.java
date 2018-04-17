package manulife.manulifesop.activity.ManagerGroup.Recruitment.introduceRecruitment;

import android.content.Context;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import manulife.manulifesop.BuildConfig;
import manulife.manulifesop.ProjectApplication;
import manulife.manulifesop.api.ApiService;
import manulife.manulifesop.api.ObjectResponse.CampaignMonth;
import manulife.manulifesop.api.ObjectResponse.CampaignRecruitMonth;
import manulife.manulifesop.base.BasePresenter;
import manulife.manulifesop.util.Contants;
import manulife.manulifesop.util.DeviceInfo;
import manulife.manulifesop.util.SOPSharedPreferences;

/**
 * Created by trinm on 12/01/2018.
 */

public class IntroduceRecruitmentPresenter extends BasePresenter<IntroduceRecruitmentContract.View> implements IntroduceRecruitmentContract.Action {

    private Context mContext;

    public IntroduceRecruitmentPresenter(IntroduceRecruitmentContract.View presenterView, Context context) {
        super(presenterView);
        this.mContext = context;
    }

    @Override
    public void getAllIntroduces(String search,int period, int page) {
        mPresenterView.showLoading("Xử lý dữ liệu");
        getCompositeDisposable().add(ApiService.getServer().getIntorduceRecruitList(
                SOPSharedPreferences.getInstance(mContext).getAccessToken(),
                Contants.clientID, DeviceInfo.ANDROID_OS_VERSION, BuildConfig.VERSION_NAME, DeviceInfo.DEVICE_NAME, DeviceInfo.DEVICEIMEI,
                period,page,10,search)//khách hàng giới thiệu tuyển dụng
                .flatMap(usersList -> {
                    ProjectApplication.getInstance().setIntroduce(usersList);
                    return ApiService.getServer().campaignRecruiMonth(
                            SOPSharedPreferences.getInstance(mContext).getAccessToken(),
                            Contants.clientID, DeviceInfo.ANDROID_OS_VERSION, BuildConfig.VERSION_NAME,
                            DeviceInfo.DEVICE_NAME, DeviceInfo.DEVICEIMEI, period);
                })
                .subscribeOn(Schedulers.computation())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponse, this::handleError));
    }

    private void handleError(Throwable throwable) {
        mPresenterView.finishLoading(throwable.getMessage(),false);
    }

    private void handleResponse(CampaignRecruitMonth campaignMonth) {
        if(campaignMonth.statusCode == 1){
            ProjectApplication.getInstance().setCampaignRecruit(campaignMonth);
            mPresenterView.initViewPager();
            mPresenterView.finishLoading();
        }else{
            mPresenterView.finishLoading(campaignMonth.msg,false);
        }
    }
}
