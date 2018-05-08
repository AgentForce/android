package manulife.manulifesop.api.ObjectResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NotiConfig {
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

        @SerializedName("FullName")
        @Expose
        public String fullName;
        @SerializedName("IsSMS")
        @Expose
        public Integer isSMS;
        @SerializedName("IsEmail")
        @Expose
        public Integer isEmail;
        @SerializedName("CreatedAt")
        @Expose
        public String createdAt;
        @SerializedName("UpdatedAt")
        @Expose
        public String updatedAt;

    }
}
