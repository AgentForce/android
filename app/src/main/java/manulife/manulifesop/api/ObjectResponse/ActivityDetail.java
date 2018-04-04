package manulife.manulifesop.api.ObjectResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by ADMIN on 3/19/2018.
 */

public class ActivityDetail implements Serializable{
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

    public class Data implements Serializable{

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
        @SerializedName("IsSupport")
        @Expose
        public Boolean isSupport;
        @SerializedName("manulife_lead")
        @Expose
        public ManulifeLead manulifeLead;

        public class ManulifeLead implements Serializable{
            @SerializedName("Name")
            @Expose
            public String name;
        }

    }
}
