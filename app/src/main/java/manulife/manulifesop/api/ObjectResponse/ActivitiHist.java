package manulife.manulifesop.api.ObjectResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ADMIN on 2/28/2018.
 */

public class ActivitiHist {
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

        @SerializedName("page")
        @Expose
        public Integer page;
        @SerializedName("limit")
        @Expose
        public Integer limit;
        @SerializedName("rows")
        @Expose
        public List<Row> rows = null;

        public class Row {

            @SerializedName("Id")
            @Expose
            public Integer id;
            @SerializedName("ActivityTypeId")
            @Expose
            public Integer activityTypeId;
            @SerializedName("Phone")
            @Expose
            public String phone;
            @SerializedName("Name")
            @Expose
            public String name;
            @SerializedName("ProcessStep")
            @Expose
            public Integer processStep;
            @SerializedName("Location")
            @Expose
            public String location;
            @SerializedName("StartDate")
            @Expose
            public String startDate;
            @SerializedName("EndDate")
            @Expose
            public String endDate;
            @SerializedName("FullDate")
            @Expose
            public Boolean fullDate;
            @SerializedName("Notification")
            @Expose
            public Integer notification;
            @SerializedName("Description")
            @Expose
            public String description;
            @SerializedName("Type")
            @Expose
            public Integer type;
            @SerializedName("Status")
            @Expose
            public Boolean status;
            @SerializedName("manulife_lead")
            @Expose
            public ManulifeLead manulifeLead;

            public class ManulifeLead {

                @SerializedName("Name")
                @Expose
                public String name;
                @SerializedName("ProcessStep")
                @Expose
                public Integer processStep;

            }

        }

    }
}
