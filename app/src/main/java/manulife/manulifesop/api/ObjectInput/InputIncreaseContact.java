package manulife.manulifesop.api.ObjectInput;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InputIncreaseContact {
    @SerializedName("IncrementContract")
    @Expose
    public Integer incrementContract;
}
