package manulife.manulifesop.fragment.ManagerGroup.UMCreateCampaign.step8;


import android.content.Context;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import manulife.manulifesop.BuildConfig;
import manulife.manulifesop.adapter.ObjectData.UMStep6;
import manulife.manulifesop.api.ApiService;
import manulife.manulifesop.api.ObjectInput.CreateCampaignUMMonthGoal;
import manulife.manulifesop.api.ObjectInput.InputCreateCampaignUM;
import manulife.manulifesop.api.ObjectInput.InputForcastIncomeUM;
import manulife.manulifesop.api.ObjectResponse.BaseResponse;
import manulife.manulifesop.api.ObjectResponse.ForcastIncomeUM;
import manulife.manulifesop.base.BasePresenter;
import manulife.manulifesop.fragment.ManagerGroup.UMCreateCampaign.step7.UMCreatePlanStep7Contract;
import manulife.manulifesop.util.Contants;
import manulife.manulifesop.util.DeviceInfo;
import manulife.manulifesop.util.SOPSharedPreferences;
import manulife.manulifesop.util.Utils;

/**
 * Created by Chick on 10/27/2017.
 */

public class UMCreatePlanStep8Present extends BasePresenter<UMCreatePlanStep8Contract.View> implements UMCreatePlanStep8Contract.Action{

    private Context mContext;

    public UMCreatePlanStep8Present(UMCreatePlanStep8Contract.View presenterView,Context context) {
        super(presenterView);
        this.mContext = context;
    }

    @Override
    public void getForcastIncome(
            String startDate,
            String enDate,
            int fycPerCasePersonal,
            int fycContinuePersonal,
            int FcTetRYP,
            int caseCountPersonalMonthly,
            int newAgentMonthlyUM ,
            int caseCountMonthlyNewAgentUM,
            int fycPerCaseNewAgentUM,
            int newAgentStandardMonthlyUM,
            int existAgentMonthlyUM,
            int newUMTrainedYearlyUM,
            int caseExistAgentMonthlytUM,
            int fycPerCaseExistAgentUM,
            int fycContinueMonthlyUM,
            int totalCommissionNewUMYearUM) {

        mPresenterView.showLoading("Lấy dữ liệu");

        InputForcastIncomeUM data = new InputForcastIncomeUM();
        data.startDate = Utils.convertStringDateToStringDate(
                startDate,"dd/MM/yyyy","yyyy-MM-dd");
        data.endDate = Utils.convertStringDateToStringDate(
                enDate,"dd/MM/yyyy","yyyy-MM-dd");
        data.fycPerCasePersonal = fycPerCasePersonal;
        data.fycContinuePersonal = fycContinuePersonal;
        data.fcTetRYP = FcTetRYP;
        data.caseCountPersonalMonthly = caseCountPersonalMonthly;
        data.newAgentMonthlyUM = newAgentMonthlyUM;
        data.caseCountMonthlyNewAgentUM = caseCountMonthlyNewAgentUM;
        data.fycPerCaseNewAgentUM = fycPerCaseNewAgentUM;
        data.newAgentStandardMonthlyUM = newAgentStandardMonthlyUM;
        data.existAgentMonthlyUM = existAgentMonthlyUM;
        data.newUMTrainedYearlyUM = newUMTrainedYearlyUM;
        data.caseExistAgentMonthlytUM = caseExistAgentMonthlytUM;
        data.fycPerCaseExistAgentUM = fycPerCaseExistAgentUM;
        data.fycContinueMonthlyUM = fycContinueMonthlyUM;
        data.totalCommissionNewUMYearUM = totalCommissionNewUMYearUM;

        String checksum = Utils.getSignature(new Gson().toJson(data));

        getCompositeDisposable().add(ApiService.getServer().getForcastIncomeUM(
                SOPSharedPreferences.getInstance(mContext).getAccessToken(),
                Contants.clientID, DeviceInfo.ANDROID_OS_VERSION, BuildConfig.VERSION_NAME,
                DeviceInfo.DEVICE_NAME, DeviceInfo.DEVICEIMEI, checksum, data)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError));
    }

    private void handleError(Throwable throwable) {
        mPresenterView.finishLoading(throwable.getMessage(),false);
    }

    private void handleResponse(ForcastIncomeUM rs) {
        if(rs.statusCode == 1)
        {
            mPresenterView.showData(rs);
            mPresenterView.finishLoading();
        }else{
            mPresenterView.finishLoading(rs.msg,false);
        }
    }

    @Override
    public void createCampaign(
            String startDate,
            String enDate,
            int fycPerCasePersonal,
            int fycContinuePersonal,
            int FcTetRYP,
            int caseCountPersonalMonthly,
            int newAgentMonthlyUM,
            int caseCountMonthlyNewAgentUM,
            int fycPerCaseNewAgentUM,
            int newAgentStandardMonthlyUM,
            int existAgentMonthlyUM,
            int newUMTrainedYearlyUM,
            int caseExistAgentMonthlytUM,
            int fycPerCaseExistAgentUM,
            int fycContinueMonthlyUM,
            int totalCommissionNewUMYearUM,
            List<UMStep6> monthGoal) {
        mPresenterView.showLoading("Lấy dữ liệu");

        InputCreateCampaignUM data = new InputCreateCampaignUM();
        data.startDate = Utils.convertStringDateToStringDate(
                startDate,"dd/MM/yyyy","yyyy-MM-dd");
        data.endDate = Utils.convertStringDateToStringDate(
                enDate,"dd/MM/yyyy","yyyy-MM-dd");
        data.fycPerCasePersonal = fycPerCasePersonal;
        data.fycContinuePersonal = fycContinuePersonal;
        data.fcTetRYP = FcTetRYP;
        data.caseCountPersonalMonthly = caseCountPersonalMonthly;
        data.newAgentMonthlyUM = newAgentMonthlyUM;
        data.caseCountMonthlyNewAgentUM = caseCountMonthlyNewAgentUM;
        data.fycPerCaseNewAgentUM = fycPerCaseNewAgentUM;
        data.newAgentStandardMonthlyUM = newAgentStandardMonthlyUM;
        data.existAgentMonthlyUM = existAgentMonthlyUM;
        data.newUMTrainedYearlyUM = newUMTrainedYearlyUM;
        data.caseExistAgentMonthlytUM = caseExistAgentMonthlytUM;
        data.fycPerCaseExistAgentUM = fycPerCaseExistAgentUM;
        data.fycContinueMonthlyUM = fycContinueMonthlyUM;
        data.totalCommissionNewUMYearUM = totalCommissionNewUMYearUM;

        List<CreateCampaignUMMonthGoal> dataGoalMonth = new ArrayList<>();
        for(int i=0;i<monthGoal.size();i++){
            dataGoalMonth.add(new CreateCampaignUMMonthGoal(
                    monthGoal.get(i).getMonth(),
                    monthGoal.get(i).getAgentAdd(),
                    monthGoal.get(i).getAgentDecrease()));
        }
        data.monthGoal = dataGoalMonth;

        String checksum = Utils.getSignature(new Gson().toJson(data));

        getCompositeDisposable().add(ApiService.getServer().createCampaignUM(
                SOPSharedPreferences.getInstance(mContext).getAccessToken(),
                Contants.clientID, DeviceInfo.ANDROID_OS_VERSION, BuildConfig.VERSION_NAME,
                DeviceInfo.DEVICE_NAME, DeviceInfo.DEVICEIMEI, checksum, data)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(this::handleResponseCreate, this::handleError));
    }

    private void handleResponseCreate(BaseResponse rs) {
        if(rs.statusCode == 1){
            mPresenterView.showSuccess();
            mPresenterView.finishLoading();
        }else{
            mPresenterView.finishLoading(rs.msg,false);
        }
    }
}
