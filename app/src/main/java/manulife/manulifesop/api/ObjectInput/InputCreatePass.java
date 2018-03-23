package manulife.manulifesop.api.ObjectInput;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Chick on 2/8/2018.
 */

public class InputCreatePass {
    @SerializedName("Password")
    @Expose
    public String password;
    @SerializedName("UserName")
    @Expose
    public String userName;
}
