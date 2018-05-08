package manulife.manulifesop.api.ObjectInput;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ADMIN on 3/23/2018.
 */

public class InputSubmitContract {
    @SerializedName("LeadId")
    @Expose
    public Integer leadId;
    @SerializedName("CommissionRate")
    @Expose
    public Integer commissionRate;
    @SerializedName("Revenue")
    @Expose
    public Integer revenue;
    @SerializedName("NumContract")
    @Expose
    public Integer numContract;
    @SerializedName("DateContract")
    @Expose
    public String dateContract;
    @SerializedName("ProductId")
    @Expose
    public List<Integer> productId = null;
}
