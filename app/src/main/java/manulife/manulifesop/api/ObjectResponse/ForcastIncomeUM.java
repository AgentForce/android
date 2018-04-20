package manulife.manulifesop.api.ObjectResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ForcastIncomeUM implements Serializable{
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

        @SerializedName("personal")
        @Expose
        public Personal personal;
        @SerializedName("manager")
        @Expose
        public Manager manager;
        public class Personal implements Serializable{

            @SerializedName("comm")
            @Expose
            public Double comm;
            @SerializedName("newAgentBonus")
            @Expose
            public Double newAgentBonus;
            @SerializedName("monthlyBonus")
            @Expose
            public Double monthlyBonus;
            @SerializedName("quarterBonus")
            @Expose
            public Double quarterBonus;
            @SerializedName("fcTetBonus")
            @Expose
            public Double fcTetBonus;
            @SerializedName("yearlyMDRTBonus")
            @Expose
            public Double yearlyMDRTBonus;
            @SerializedName("monthlyMDRTBonus")
            @Expose
            public Double monthlyMDRTBonus;
        }
        public class Manager implements Serializable{

            @SerializedName("newUMBonus")
            @Expose
            public Double newUMBonus;
            @SerializedName("monthlyOverride")
            @Expose
            public Double monthlyOverride;
            @SerializedName("recruiterBonus")
            @Expose
            public Double recruiterBonus;
            @SerializedName("NABMangerBonus")
            @Expose
            public Double nABMangerBonus;
            @SerializedName("individualManagerBonus")
            @Expose
            public Double individualManagerBonus;
            @SerializedName("coachingUMBonus")
            @Expose
            public Double coachingUMBonus;
            @SerializedName("newAgentQualitityBonus")
            @Expose
            public Double newAgentQualitityBonus;
        }
    }
}
