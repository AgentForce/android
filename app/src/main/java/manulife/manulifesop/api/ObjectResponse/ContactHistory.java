package manulife.manulifesop.api.ObjectResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ADMIN on 3/15/2018.
 */

public class ContactHistory {
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

        @SerializedName("count")
        @Expose
        public Integer count;
        @SerializedName("rows")
        @Expose
        public List<Row> rows = null;
        @SerializedName("page")
        @Expose
        public Integer page;
        @SerializedName("limit")
        @Expose
        public Integer limit;

        public class Row {

            @SerializedName("LeadId")
            @Expose
            public Integer leadID;
            @SerializedName("StatusProcessStep")
            @Expose
            public Integer statusProcessStep;
            @SerializedName("ProcessStep")
            @Expose
            public Integer processStep;
            @SerializedName("CreatedAt")
            @Expose
            public String createdAt;

        }

    }
}
