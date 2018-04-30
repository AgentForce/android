package manulife.manulifesop.api.ObjectResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CheckCampaign {
    @SerializedName("statusCode")
    @Expose
    public Integer statusCode;
    @SerializedName("data")
    @Expose
    public Data data;
    @SerializedName("msg")
    @Expose
    public String msg;
    @SerializedName("msgCode")
    @Expose
    public String msgCode;
    public class Data {

        @SerializedName("status")
        @Expose
        public Boolean status;
        @SerializedName("data")
        @Expose
        public Data_ data;

    }
    public class Data_ {

        @SerializedName("Id")
        @Expose
        public Integer id;
        @SerializedName("UserId")
        @Expose
        public Integer userId;
        @SerializedName("StartDate")
        @Expose
        public String startDate;
        @SerializedName("EndDate")
        @Expose
        public String endDate;
        @SerializedName("CommissionRate")
        @Expose
        public Integer commissionRate;
        @SerializedName("CaseSize")
        @Expose
        public Integer caseSize;
        @SerializedName("IncomeMonthly")
        @Expose
        public Integer incomeMonthly;
        @SerializedName("ReportTo")
        @Expose
        public Integer reportTo;
        @SerializedName("ReportToList")
        @Expose
        public String reportToList;
        @SerializedName("AgentTer")
        @Expose
        public Integer agentTer;
        @SerializedName("NewAgent")
        @Expose
        public Integer newAgent;
        @SerializedName("ActiveRaito")
        @Expose
        public Integer activeRaito;
        @SerializedName("M3AARaito")
        @Expose
        public Integer m3AARaito;
        @SerializedName("AverageCC")
        @Expose
        public Integer averageCC;
        @SerializedName("M3AA")
        @Expose
        public Integer m3AA;
        @SerializedName("FypRaito")
        @Expose
        public Integer fypRaito;
        @SerializedName("IsDeleted")
        @Expose
        public Boolean isDeleted;
        @SerializedName("CreatedAt")
        @Expose
        public String createdAt;
        @SerializedName("UpdatedAt")
        @Expose
        public String updatedAt;

    }
}
