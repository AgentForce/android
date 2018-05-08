package manulife.manulifesop.api.ObjectResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ADMIN on 3/14/2018.
 */

public class ContactDetail {
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

        @SerializedName("Id")
        @Expose
        public Integer id;
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
        @SerializedName("Status")
        @Expose
        public Boolean status;
        @SerializedName("StatusProcessStep")
        @Expose
        public Integer statusProcessStep;
        @SerializedName("Score")
        @Expose
        public Integer score;
        @SerializedName("BirthDay")
        @Expose
        public String birthday;
    }
}
