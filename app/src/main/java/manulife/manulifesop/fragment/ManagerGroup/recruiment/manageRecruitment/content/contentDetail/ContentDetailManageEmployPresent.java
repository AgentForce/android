package manulife.manulifesop.fragment.ManagerGroup.recruiment.manageRecruitment.content.contentDetail;


import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import manulife.manulifesop.BuildConfig;
import manulife.manulifesop.adapter.ObjectData.RecruitmentDirectlyData;
import manulife.manulifesop.api.ApiService;
import manulife.manulifesop.api.ObjectResponse.RecruitmentDashboard;
import manulife.manulifesop.api.ObjectResponse.RecruitmentDirectly;
import manulife.manulifesop.base.BasePresenter;
import manulife.manulifesop.util.Contants;
import manulife.manulifesop.util.DeviceInfo;
import manulife.manulifesop.util.SOPSharedPreferences;
import manulife.manulifesop.util.Utils;

/**
 * Created by Chick on 10/27/2017.
 */

public class ContentDetailManageEmployPresent extends BasePresenter<ContentDetailManageEmployContract.View> implements ContentDetailManageEmployContract.Action {

    private Context mContext;
    private int mUserID;
    private int mMonth,mWeek;

    private int mCurrentPage, mLastPage;
    private String mErrorMsg;

    public ContentDetailManageEmployPresent(ContentDetailManageEmployContract.View presenterView, Context context) {
        super(presenterView);
        this.mContext = context;
    }

    @Override
    public void getRecruitDashBoard(int userID, int month, int week) {
        mUserID = userID;
        mMonth = month;
        mWeek = week;
        mPresenterView.showLoading("Lấy dữ liệu");
        getCompositeDisposable().add(ApiService.getServer().getRecruitDashboard(
                SOPSharedPreferences.getInstance(mContext).getAccessToken(),
                Contants.clientID, DeviceInfo.ANDROID_OS_VERSION, BuildConfig.VERSION_NAME,
                DeviceInfo.DEVICE_NAME, DeviceInfo.DEVICEIMEI,
                week,month,userID)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(this::handleResponseRecruitDashboard, this::handleError));
    }

    private void handleError(Throwable throwable) {
        mPresenterView.finishLoading(throwable.getMessage(),false);
    }

    private void handleResponseRecruitDashboard(RecruitmentDashboard rs) {
        if(rs.statusCode == 1){
            mPresenterView.showRecruitDashboard(rs);
            getRecruitDirectlyDashboard(mUserID,mMonth,mWeek,1);
        }else {
            mPresenterView.finishLoading(rs.msg, false);
        }
    }

    @Override
    public void getRecruitDirectlyDashboard(int userID, int month, int week, int page) {
        mPresenterView.showLoading("Lấy dữ liệu");
        getCompositeDisposable().add(ApiService.getServer().getRecruitDashboardDicrectManagement(
                SOPSharedPreferences.getInstance(mContext).getAccessToken(),
                Contants.clientID, DeviceInfo.ANDROID_OS_VERSION, BuildConfig.VERSION_NAME,
                DeviceInfo.DEVICE_NAME, DeviceInfo.DEVICEIMEI,
                week,month,userID,page,10)
                .map(dataResponse -> {
                    if(dataResponse.statusCode == 1){
                        List<RecruitmentDirectlyData> data = new ArrayList<>();
                        RecruitmentDirectlyData tmp;
                        for(int i=0;i<dataResponse.data.rows.size();i++){
                            tmp = new RecruitmentDirectlyData();
                            tmp.setId(dataResponse.data.rows.get(i).id);
                            tmp.setFullName(dataResponse.data.rows.get(i).fullName);
                            tmp.setCurrentStep1(dataResponse.data.rows.get(i).currentSurvey);
                            tmp.setCurrentStep2(dataResponse.data.rows.get(i).currentCop);
                            tmp.setCurrentStep3(dataResponse.data.rows.get(i).currentMit);
                            tmp.setCurrentStep4(dataResponse.data.rows.get(i).currentAgentCode);
                            tmp.setCurrentStep5(dataResponse.data.rows.get(i).currentReLeadRecruit);
                            data.add(tmp);
                        }
                        mCurrentPage = dataResponse.data.page;
                        mLastPage = Utils.genLastPage(dataResponse.data.count,dataResponse.data.limit);
                        return data;
                    }else {
                        mErrorMsg = dataResponse.msg;
                        return null;
                    }

                })
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(this::handleResponseRecruitDashboardDicrectManagement, this::handleError));
    }

    private void handleResponseRecruitDashboardDicrectManagement(List<RecruitmentDirectlyData> rs) {
        if(rs != null){
            mPresenterView.showRecruitDashboardDirectly(rs,mCurrentPage,mLastPage);
            mPresenterView.finishLoading();
        }else{
            mPresenterView.finishLoading(mErrorMsg,false);
        }
    }


}
