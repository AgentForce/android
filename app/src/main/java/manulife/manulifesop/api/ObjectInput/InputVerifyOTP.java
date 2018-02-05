package manulife.manulifesop.api.ObjectInput;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Chick on 2/5/2018.
 */

public class InputVerifyOTP {
    @SerializedName("Code")
    @Expose
    private String code;
    @SerializedName("UserName")
    @Expose
    private String userName;
    @SerializedName("Phone")
    @Expose
    private String phone;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
