package manulife.manulifesop.api.ObjectResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class DashboardSMResult implements Serializable {
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

    public class Data implements Serializable{

        @SerializedName("type")
        @Expose
        public String type;
        @SerializedName("currentWeek")
        @Expose
        public Integer currentWeek;
        @SerializedName("isRequestActive")
        @Expose
        public Integer isRequestActive;
        @SerializedName("campaigns")
        @Expose
        public List<Campaign> campaigns = null;

        public class Campaign implements Serializable{
            @SerializedName("Period")
            @Expose
            public Integer period;
            @SerializedName("Week")
            @Expose
            public Integer week;

            @SerializedName("UserId")
            @Expose
            public Integer userId;
            @SerializedName("TargetAgentCode")
            @Expose
            public Integer targetAgentCode;
            @SerializedName("TargetCop")
            @Expose
            public Integer targetCop;

            @SerializedName("TargetMit")
            @Expose
            public Integer targetMit;

            @SerializedName("TargetSurvey")
            @Expose
            public Integer targetSurvey;
            @SerializedName("TargetReLeadRecruit")
            @Expose
            public Integer targetReLeadRecruit;
            @SerializedName("CurrentAgentCode")
            @Expose
            public Integer currentAgentCode;
            @SerializedName("CurrentCop")
            @Expose
            public Integer currentCop;
            @SerializedName("CurrentMit")
            @Expose
            public Integer currentMit;
            @SerializedName("CurrentSurvey")
            @Expose
            public Integer currentSurvey;
            @SerializedName("CurrentReLeadRecruit")
            @Expose
            public Integer currentReLeadRecruit;
            @SerializedName("ForcastAgentCode")
            @Expose
            public Integer forcastAgentCode;
            @SerializedName("ForcastCop")
            @Expose
            public Integer forcastCop;
            @SerializedName("ForcastMit")
            @Expose
            public Integer forcastMit;
            @SerializedName("ForcastSurvey")
            @Expose
            public Integer forcastSurvey;
            @SerializedName("ForcastReLeadRecruit")
            @Expose
            public Integer forcastReLeadRecruit;

        }

    }
}
