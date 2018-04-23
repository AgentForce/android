package manulife.manulifesop.api.ObjectInput;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InputTest {
    @SerializedName("Name")
    @Expose
    public String name;
    @SerializedName("Age")
    @Expose
    public String age;
    @SerializedName("Phone")
    @Expose
    public String phone;
}
