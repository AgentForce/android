package manulife.manulifesop.api.ObjectResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RefreshToken {
    @SerializedName("statusCode")
    @Expose
    public Integer statusCode;
    @SerializedName("data")
    @Expose
    public Data data;
    @SerializedName("msg")
    @Expose
    public String msg;
    @SerializedName("msgcode")
    @Expose
    public String msgcode;

    public class Data {

        @SerializedName("access_token")
        @Expose
        public String accessToken;
        @SerializedName("token_type")
        @Expose
        public String tokenType;
        @SerializedName("expires_in")
        @Expose
        public Integer expiresIn;
        @SerializedName("refresh_token")
        @Expose
        public String refreshToken;
        @SerializedName("scope")
        @Expose
        public String scope;

    }
}
