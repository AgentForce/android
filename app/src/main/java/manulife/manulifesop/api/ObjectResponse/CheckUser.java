package manulife.manulifesop.api.ObjectResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Chick on 1/30/2018.
 */

public class CheckUser {
    @SerializedName("statusCode")
    @Expose
    public Integer statusCode;

    @SerializedName("msgcode")
    @Expose
    public String msgcode;

    @SerializedName("msg")
    @Expose
    public String msg;

    @SerializedName("data")
    @Expose
    public Data data;

    public class Data {
        @SerializedName("status")
        @Expose
        public Integer status;
    }


}
