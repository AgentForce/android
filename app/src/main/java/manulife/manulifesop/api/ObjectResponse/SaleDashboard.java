package manulife.manulifesop.api.ObjectResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SaleDashboard {
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
        @SerializedName("TargetContract")
        @Expose
        public Integer targetContract;
        @SerializedName("TargetMetting")
        @Expose
        public Integer targetMetting;
        @SerializedName("TargetCallSale")
        @Expose
        public Integer targetCallSale;
        @SerializedName("TargetPresentation")
        @Expose
        public Integer targetPresentation;
        @SerializedName("TargetReLead")
        @Expose
        public Integer targetReLead;
        @SerializedName("CurrentContract")
        @Expose
        public Integer currentContract;
        @SerializedName("CurrentCallSale")
        @Expose
        public Integer currentCallSale;
        @SerializedName("CurrentPresentation")
        @Expose
        public Integer currentPresentation;
        @SerializedName("CurrentMetting")
        @Expose
        public Integer currentMetting;
        @SerializedName("CurrentReLead")
        @Expose
        public Integer currentReLead;

    }
}
