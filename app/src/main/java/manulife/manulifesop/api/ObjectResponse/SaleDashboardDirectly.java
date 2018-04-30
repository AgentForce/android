package manulife.manulifesop.api.ObjectResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SaleDashboardDirectly {
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

        @SerializedName("limit")
        @Expose
        public Integer limit;
        @SerializedName("page")
        @Expose
        public Integer page;
        @SerializedName("count")
        @Expose
        public Integer count;
        @SerializedName("rows")
        @Expose
        public List<Row> rows = null;
        public class Row {

            @SerializedName("Id")
            @Expose
            public Integer id;
            @SerializedName("FullName")
            @Expose
            public String fullName;
            @SerializedName("ReportTo")
            @Expose
            public Integer reportTo;
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
}
