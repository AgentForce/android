package manulife.manulifesop.api.ObjectInput;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InputUpdateEvent {
    @SerializedName("Type")
    @Expose
    public Integer type;
    @SerializedName("Name")
    @Expose
    public String name;
    @SerializedName("Location")
    @Expose
    public String location;
    @SerializedName("StartDate")
    @Expose
    public String startDate;
    @SerializedName("EndDate")
    @Expose
    public String endDate;
    @SerializedName("Description")
    @Expose
    public String description;
    @SerializedName("FullDate")
    @Expose
    public Boolean fullDate;
    @SerializedName("Notification")
    @Expose
    public Integer notification;
    @SerializedName("IsSupport")
    @Expose
    public Boolean isSupport;
}
