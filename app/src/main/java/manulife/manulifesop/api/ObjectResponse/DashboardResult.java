package manulife.manulifesop.api.ObjectResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ADMIN on 2/28/2018.
 */

public class DashboardResult {
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

        @SerializedName("type")
        @Expose
        public String type;
        @SerializedName("currentWeek")
        @Expose
        public Integer currentWeek;
        @SerializedName("campaigns")
        @Expose
        public List<Campaign> campaign = null;

        public class Campaign {

            @SerializedName("Period")
            @Expose
            public Integer period;
            @SerializedName("Week")
            @Expose
            public Integer week;
            @SerializedName("TargetCallSale")
            @Expose
            public Integer targetCallSale;
            @SerializedName("TargetMetting")
            @Expose
            public Integer targetMetting;
            @SerializedName("TargetPresentation")
            @Expose
            public Integer targetPresentation;
            @SerializedName("TargetContractSale")
            @Expose
            public Integer targetContractSale;
            @SerializedName("TargetReLead")
            @Expose
            public Integer targetReLead;
            @SerializedName("CurrentCallSale")
            @Expose
            public Integer currentCallSale;
            @SerializedName("CurrentMetting")
            @Expose
            public Integer currentMetting;
            @SerializedName("CurrentPresentation")
            @Expose
            public Integer currentPresentation;
            @SerializedName("CurrentContract")
            @Expose
            public Integer currentContract;
            @SerializedName("CurrentReLead")
            @Expose
            public Integer currentReLead;

        }

    }
}
