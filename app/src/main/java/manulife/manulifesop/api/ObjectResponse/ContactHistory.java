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

            @SerializedName("Phone")
            @Expose
            public String phone;
            @SerializedName("Name")
            @Expose
            public String name;
            @SerializedName("Age")
            @Expose
            public Integer age;
            @SerializedName("Gender")
            @Expose
            public Integer gender;
            @SerializedName("IncomeMonthly")
            @Expose
            public Integer incomeMonthly;
            @SerializedName("MaritalStatus")
            @Expose
            public Integer maritalStatus;
            @SerializedName("Relationship")
            @Expose
            public Integer relationship;
            @SerializedName("Source")
            @Expose
            public Integer source;
            @SerializedName("LeadType")
            @Expose
            public Integer leadType;
            @SerializedName("ProcessStep")
            @Expose
            public Integer processStep;
            @SerializedName("Description")
            @Expose
            public String description;
            @SerializedName("StatusProcessStep")
            @Expose
            public Integer statusProcessStep;
            @SerializedName("Score")
            @Expose
            public Integer score;

        }

    }
}
