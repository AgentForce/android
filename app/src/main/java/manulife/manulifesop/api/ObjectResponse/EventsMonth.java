package manulife.manulifesop.api.ObjectResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ADMIN on 3/13/2018.
 */

public class EventsMonth {
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

        @SerializedName("rows")
        @Expose
        public List<Row> rows = null;
        public class Row {

            @SerializedName("date")
            @Expose
            public String date;
            @SerializedName("activities")
            @Expose
            public List<Activity> activities = null;

            public class Activity {

                @SerializedName("ProcessStep")
                @Expose
                public Integer processStep;

            }

        }
    }
}
