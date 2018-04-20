package manulife.manulifesop.api.ObjectInput;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class InputCreateCampaignUM {
    @SerializedName("StartDate")
    @Expose
    public String startDate;
    @SerializedName("EndDate")
    @Expose
    public String endDate;
    @SerializedName("fycPerCasePersonal")
    @Expose
    public Integer fycPerCasePersonal;
    @SerializedName("fycContinuePersonal")
    @Expose
    public Integer fycContinuePersonal;
    @SerializedName("FcTetRYP")
    @Expose
    public Integer fcTetRYP;
    @SerializedName("caseCountPersonalMonthly")
    @Expose
    public Integer caseCountPersonalMonthly;
    @SerializedName("newAgentMonthlyUM")
    @Expose
    public Integer newAgentMonthlyUM;
    @SerializedName("caseCountMonthlyNewAgentUM")
    @Expose
    public Integer caseCountMonthlyNewAgentUM;
    @SerializedName("fycPerCaseNewAgentUM")
    @Expose
    public Integer fycPerCaseNewAgentUM;
    @SerializedName("newAgentStandardMonthlyUM")
    @Expose
    public Integer newAgentStandardMonthlyUM;
    @SerializedName("newUMTrainedYearlyUM")
    @Expose
    public Integer newUMTrainedYearlyUM;
    @SerializedName("existAgentMonthlyUM")
    @Expose
    public Integer existAgentMonthlyUM;
    @SerializedName("caseExistAgentMonthlytUM")
    @Expose
    public Integer caseExistAgentMonthlytUM;
    @SerializedName("fycPerCaseExistAgentUM")
    @Expose
    public Integer fycPerCaseExistAgentUM;
    @SerializedName("fycContinueMonthlyUM")
    @Expose
    public Integer fycContinueMonthlyUM;
    @SerializedName("totalCommissionNewUMYearUM")
    @Expose
    public Integer totalCommissionNewUMYearUM;
    @SerializedName("MonthGoal")
    @Expose
    public List<CreateCampaignUMMonthGoal> monthGoal = null;
}
