package manulife.manulifesop.activity.ManagerGroup.UMCreatePlan;

import java.util.List;

import manulife.manulifesop.adapter.ObjectData.UMStep6;
import manulife.manulifesop.api.ObjectResponse.UMForcastRecruit;
import manulife.manulifesop.base.BaseMVPView;

/**
 * Created by trinm on 12/01/2018.
 */

public interface UMCreatePlanContract {
    interface View extends BaseMVPView
    {
        void showNextFragment();
        void setDataStep1(String startDate, String endDate);
        void setDataStep2(UMForcastRecruit data);
        void setDataStep3(int newAgent, int newContact, int FYC);
        void setDataStep4(int currentAgent,int currentContact,int currentFYC,
                          int maintainAgent);
        void setDataStep5(int numUM, int totalUMProfit);
        void setDataStep6(List<UMStep6> data);
        void setDataStep7(int contactPerMonth,int FYCPerMonth, int FYC,int RYP);
        void setInComeMonthly(String income);
        String getStartDate();
        String getEndDate();
        int getMonthNumber();
        UMForcastRecruit getDataStep2();
        List<Integer> getDataStep3();
        List<Integer> getDataStep7();
        List<Integer> getDataStep4();
        List<Integer> getDataStep5();
        List<UMStep6> getDataStep6();

        void showSuccessView();

    }
    interface Action{

    }
}
