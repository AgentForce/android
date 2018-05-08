package manulife.manulifesop.api.ObjectInput;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InputUpdateNotiConfig {
    @SerializedName("IsEmail")
    @Expose
    public Integer isEmail;
    @SerializedName("IsSMS")
    @Expose
    public Integer isSMS;
}
