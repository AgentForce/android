package manulife.manulifesop.api.ObjectInput;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InputLogCall {
    @SerializedName("LeadId")
    @Expose
    public Integer leadId;
}
