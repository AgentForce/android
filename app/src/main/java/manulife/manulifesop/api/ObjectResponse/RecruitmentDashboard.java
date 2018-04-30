package manulife.manulifesop.api.ObjectResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RecruitmentDashboard {
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

        @SerializedName("Id")
        @Expose
        public Integer id;
        @SerializedName("LevelCode")
        @Expose
        public Integer levelCode;
        @SerializedName("ReportTo")
        @Expose
        public Integer reportTo;
        @SerializedName("FullName")
        @Expose
        public String fullName;
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
    }
}
