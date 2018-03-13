package manulife.manulifesop.api.ObjectResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ADMIN on 3/13/2018.
 */

public class UsersList implements Serializable {
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

        @SerializedName("page")
        @Expose
        public String page;
        @SerializedName("limit")
        @Expose
        public String limit;
        @SerializedName("count")
        @Expose
        public Integer count;
        @SerializedName("rows")
        @Expose
        public List<Row> rows = null;

        public class Row implements Serializable{

            @SerializedName("Phone")
            @Expose
            public String phone;
            @SerializedName("Name")
            @Expose
            public String name;
            @SerializedName("Score")
            @Expose
            public Integer score;
            @SerializedName("ProcessStep")
            @Expose
            public Integer processStep;
            @SerializedName("StatusProcessStep")
            @Expose
            public Integer statusProcessStep;
            @SerializedName("Id")
            @Expose
            public Integer id;

        }

    }
}
