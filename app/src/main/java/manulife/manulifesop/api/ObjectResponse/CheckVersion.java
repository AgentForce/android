package manulife.manulifesop.api.ObjectResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by trinm on 12/01/2018.
 */

public class CheckVersion {
    @SerializedName("Result")
    @Expose
    public Result result;

    public class Result {

        @SerializedName("ErrorCode")
        @Expose
        public Integer errorCode;
        @SerializedName("ErrorDescription")
        @Expose
        public String errorDescription;
        @SerializedName("Data")
        @Expose
        public Data data;

        public class Data {

            @SerializedName("platform")
            @Expose
            public String platform;
            @SerializedName("version")
            @Expose
            public String version;
            @SerializedName("release_date")
            @Expose
            public String releaseDate;
            @SerializedName("link_update")
            @Expose
            public String linkUpdate;
            @SerializedName("flag")
            @Expose
            public String flag;
        }

    }
}
