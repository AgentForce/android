package manulife.manulifesop.fragment.ManagerGroup.UMCreateCampaign.step8;

import java.util.List;

import manulife.manulifesop.adapter.ObjectData.UMStep6;
import manulife.manulifesop.api.ObjectResponse.ForcastIncomeUM;
import manulife.manulifesop.base.BaseMVPView;


/**
 * Created by Chick on 10/27/2017.
 */

public interface UMCreatePlanStep8Contract {

    interface View extends BaseMVPView {
        void getForCastIncome();
        void showData(ForcastIncomeUM data);
        void showSuccess();
    }

    interface Action {
        void getForcastIncome(String startDate,String enDate,int fycPerCasePersonal,
                              int fycContinuePersonal, int FcTetRYP,int caseCountPersonalMonthly,
                              int newAgentMonthlyUM,
                              int caseCountMonthlyNewAgentUM, int fycPerCaseNewAgentUM,
                              int newAgentStandardMonthlyUM,int existAgentMonthlyUM,
                              int newUMTrainedYearlyUM, int caseExistAgentMonthlytUM,
                              int fycPerCaseExistAgentUM, int fycContinueMonthlyUM, int totalCommissionNewUMYearUM);

        void createCampaign(String startDate, String enDate, int fycPerCasePersonal,
                            int fycContinuePersonal, int FcTetRYP, int caseCountPersonalMonthly,
                            int newAgentMonthlyUM,
                            int caseCountMonthlyNewAgentUM, int fycPerCaseNewAgentUM,
                            int newAgentStandardMonthlyUM, int existAgentMonthlyUM,
                            int newUMTrainedYearlyUM, int caseExistAgentMonthlytUM,
                            int fycPerCaseExistAgentUM, int fycContinueMonthlyUM, int totalCommissionNewUMYearUM,
                            List<UMStep6> monthGoal);

    }
}
