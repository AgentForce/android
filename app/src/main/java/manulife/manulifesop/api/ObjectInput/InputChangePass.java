package manulife.manulifesop.api.ObjectInput;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InputChangePass {
    @SerializedName("OldPassword")
    @Expose
    public String oldPassword;
    @SerializedName("NewPassword")
    @Expose
    public String newPassword;
}
