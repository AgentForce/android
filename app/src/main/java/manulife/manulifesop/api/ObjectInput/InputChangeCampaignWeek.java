package manulife.manulifesop.api.ObjectInput;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ADMIN on 3/22/2018.
 */

public class InputChangeCampaignWeek {
    @SerializedName("Target")
    @Expose
    public List<Integer> target = null;
}
