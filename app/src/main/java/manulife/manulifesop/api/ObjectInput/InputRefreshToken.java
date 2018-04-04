package manulife.manulifesop.api.ObjectInput;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InputRefreshToken {
    @SerializedName("refreshToken")
    @Expose
    public String refreshToken;
}
