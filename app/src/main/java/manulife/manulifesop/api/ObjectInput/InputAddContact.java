package manulife.manulifesop.api.ObjectInput;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ADMIN on 3/14/2018.
 */

public class InputAddContact {
    @SerializedName("CampId")
    @Expose
    public Integer campId;
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
    @SerializedName("Description")
    @Expose
    public String description;
    @SerializedName("BirthDay")
    @Expose
    public String birthday;
}
