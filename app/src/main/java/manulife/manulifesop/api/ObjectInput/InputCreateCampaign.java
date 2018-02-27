package manulife.manulifesop.api.ObjectInput;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ADMIN on 2/27/2018.
 */

public class InputCreateCampaign {
    @SerializedName("StartDate")
    @Expose
    private String startDate;
    @SerializedName("CaseSize")
    @Expose
    private Long caseSize;
    @SerializedName("IncomeMonthly")
    @Expose
    private Long incomeMonthly;
    @SerializedName("CommissionRate")
    @Expose
    private Long commissionRate;

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public Long getCaseSize() {
        return caseSize;
    }

    public void setCaseSize(Long caseSize) {
        this.caseSize = caseSize;
    }

    public Long getIncomeMonthly() {
        return incomeMonthly;
    }

    public void setIncomeMonthly(Long incomeMonthly) {
        this.incomeMonthly = incomeMonthly;
    }

    public Long getCommissionRate() {
        return commissionRate;
    }

    public void setCommissionRate(Long commissionRate) {
        this.commissionRate = commissionRate;
    }
}
