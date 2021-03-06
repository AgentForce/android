package manulife.manulifesop.api.ObjectResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ADMIN on 3/22/2018.
 */

public class CampaignForcastTarget {
    @SerializedName("statusCode")
    @Expose
    public Integer statusCode;
    @SerializedName("data")
    @Expose
    public Data data;
    @SerializedName("msg")
    @Expose
    public String msg;
    @SerializedName("msgCode")
    @Expose
    public String msgCode;
    public class Data {

        @SerializedName("newAgent")
        @Expose
        public List<Long> newAgent = null;
        @SerializedName("monthlyBonus")
        @Expose
        public List<Long> monthlyBonus = null;
        @SerializedName("quarterBonus")
        @Expose
        public List<Long> quarterBonus = null;
        @SerializedName("mdrtBonus")
        @Expose
        public List<Long> mdrtBonus = null;

    }
}
