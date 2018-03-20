package manulife.manulifesop.api.ObjectInput;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ADMIN on 3/19/2018.
 */

public class InputChangeContactStatus {
    @SerializedName("NextProcessStep")
    @Expose
    public Boolean nextProcessStep;
    @SerializedName("StatusProcessStep")
    @Expose
    public Integer statusProcessStep;
}
