package manulife.manulifesop.api.ObjectInput;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreateCampaignUMMonthGoal {
    @SerializedName("Month")
    @Expose
    public Integer month;
    @SerializedName("NumRecruit")
    @Expose
    public Integer numRecruit;
    @SerializedName("NumOff")
    @Expose
    public Integer numOff;

    public CreateCampaignUMMonthGoal(Integer month, Integer numRecruit, Integer numOff) {
        this.month = month;
        this.numRecruit = numRecruit;
        this.numOff = numOff;
    }
}
