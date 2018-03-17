package manulife.manulifesop.api.ObjectInput;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ADMIN on 3/16/2018.
 */

public class InputIntroduceContact {
    @SerializedName("Phone")
    @Expose
    public String phone;
    @SerializedName("Name")
    @Expose
    public String name;
    @SerializedName("CampId")
    @Expose
    public Integer campId;

    public InputIntroduceContact(String phone, String name, Integer campId) {
        this.phone = phone;
        this.name = name;
        this.campId = campId;
    }
}
