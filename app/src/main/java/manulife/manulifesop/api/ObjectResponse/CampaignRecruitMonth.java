package manulife.manulifesop.api.ObjectResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ADMIN on 3/9/2018.
 */

public class CampaignRecruitMonth implements Serializable{
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

        @SerializedName("isRequestActive")
        @Expose
        public Integer isRequestActive;
        @SerializedName("period")
        @Expose
        public Integer period;
        @SerializedName("campaigns")
        @Expose
        public List<Campaign> campaigns = null;
        @SerializedName("currentWeek")
        @Expose
        public Integer currentWeek;

        public class Campaign implements Serializable{

            @SerializedName("Id")
            @Expose
            public String id;
            @SerializedName("Period")
            @Expose
            public Integer period;
            @SerializedName("Week")
            @Expose
            public Integer week;
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
            @SerializedName("CurrentReleadRecruit")
            @Expose
            public Integer currentReleadRecruit;

        }

    }
}
