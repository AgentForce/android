package manulife.manulifesop.api.ObjectResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by ADMIN on 3/28/2018.
 */

public class UserProfile implements Serializable{
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

        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("username")
        @Expose
        public String username;
        @SerializedName("phone")
        @Expose
        public String phone;
        @SerializedName("address")
        @Expose
        public String address;
        @SerializedName("city")
        @Expose
        public Integer city;
        @SerializedName("district")
        @Expose
        public Integer district;
        @SerializedName("gender")
        @Expose
        public Integer gender;
        @SerializedName("birthday")
        @Expose
        public String birthday;
        @SerializedName("level")
        @Expose
        public Integer level;
        @SerializedName("status")
        @Expose
        public Integer status;
        @SerializedName("lastlogin")
        @Expose
        public String lastlogin;
        @SerializedName("expirence")
        @Expose
        public String expirence;
        @SerializedName("fullName")
        @Expose
        public String fullName;
        @SerializedName("email")
        @Expose
        public String email;
        @SerializedName("scope")
        @Expose
        public String scope;
        @SerializedName("resource_ids")
        @Expose
        public String resourceIds;
        @SerializedName("zone")
        @Expose
        public Integer zone;
        @SerializedName("createdAt")
        @Expose
        public String createdAt;
        @SerializedName("code_level")
        @Expose
        public String codeLevel;
        @SerializedName("badge")
        @Expose
        public String badge;
        @SerializedName("updatedAt")
        @Expose
        public String updatedAt;
        @SerializedName("report_to")
        @Expose
        public String reportTo;
        @SerializedName("report_to_list")
        @Expose
        public Object reportToList;
        @SerializedName("report_to_username")
        @Expose
        public String reportToUsername;
        @SerializedName("onboard_date")
        @Expose
        public String onboardDate;
        @SerializedName("manager_badge")
        @Expose
        public String managerBadge;
    }
}
