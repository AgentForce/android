package manulife.manulifesop.api.ObjectResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ActivitiHistSetting {
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

        @SerializedName("count")
        @Expose
        public Integer count;
        @SerializedName("page")
        @Expose
        public Integer page;
        @SerializedName("limit")
        @Expose
        public Integer limit;
        @SerializedName("items")
        @Expose
        public List<Item> items = null;
        public class Item {

            @SerializedName("_id")
            @Expose
            public String id;
            @SerializedName("updatedAt")
            @Expose
            public String updatedAt;
            @SerializedName("createdAt")
            @Expose
            public String createdAt;
            @SerializedName("username")
            @Expose
            public String username;
            @SerializedName("activityType")
            @Expose
            public String activityType;
            @SerializedName("activityName")
            @Expose
            public String activityName;
        }
    }
}
