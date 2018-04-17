package manulife.manulifesop.api.ObjectResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RecruitHistory {
    @SerializedName("statusCode")
    @Expose
    public Integer statusCode;
    @SerializedName("data")
    @Expose
    public Data data;
    @SerializedName("msgCode")
    @Expose
    public String msgCode;
    @SerializedName("msg")
    @Expose
    public String msg;

    public class Data {

        @SerializedName("page")
        @Expose
        public Integer page;
        @SerializedName("limit")
        @Expose
        public Integer limit;
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
            @SerializedName("Phone")
            @Expose
            public String phone;
            @SerializedName("Name")
            @Expose
            public String name;
            @SerializedName("ProcessStep")
            @Expose
            public Integer processStep;
            @SerializedName("StatusProcessStep")
            @Expose
            public Integer statusProcessStep;
            @SerializedName("UpdatedAt")
            @Expose
            public String updatedAt;

        }

    }
}
