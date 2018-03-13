package manulife.manulifesop.api.ObjectResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ADMIN on 3/13/2018.
 */

public class EventsOneDay {
    @SerializedName("statusCode")
    @Expose
    public Integer statusCode;
    @SerializedName("data")
    @Expose
    public List<Data> data = null;
    @SerializedName("msgCode")
    @Expose
    public String msgCode;
    @SerializedName("msg")
    @Expose
    public String msg;

    public class Data {

        @SerializedName("Status")
        @Expose
        public Boolean status;
        @SerializedName("StartDate")
        @Expose
        public String startDate;
        @SerializedName("ProcessStep")
        @Expose
        public Integer processStep;
        @SerializedName("manulife_lead")
        @Expose
        public ManulifeLead manulifeLead;

        public class ManulifeLead {

            @SerializedName("Name")
            @Expose
            public String name;
        }
    }
}
