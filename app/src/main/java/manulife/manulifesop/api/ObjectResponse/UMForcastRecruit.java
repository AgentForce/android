package manulife.manulifesop.api.ObjectResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UMForcastRecruit {
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

        @SerializedName("goalSetup")
        @Expose
        public GoalSetup goalSetup;
        @SerializedName("noGoalSetup")
        @Expose
        public NoGoalSetup noGoalSetup;
        @SerializedName("quantityNewAgent")
        @Expose
        public Integer quantityNewAgent;
        @SerializedName("quantityAgentTer")
        @Expose
        public Integer quantityAgentTer;
        @SerializedName("quantityAgentGrow")
        @Expose
        public QuantityAgentGrow quantityAgentGrow;
        @SerializedName("quantityContractGrow")
        @Expose
        public Integer quantityContractGrow;

        public class GoalSetup {

            @SerializedName("um")
            @Expose
            public Integer um;
            @SerializedName("fa")
            @Expose
            public Integer fa;

        }
        public class NoGoalSetup {

            @SerializedName("um")
            @Expose
            public Integer um;
            @SerializedName("fa")
            @Expose
            public Integer fa;

        }
        public class QuantityAgentGrow {

            @SerializedName("current")
            @Expose
            public Integer current;
            @SerializedName("future")
            @Expose
            public Integer future;

        }
    }
}
