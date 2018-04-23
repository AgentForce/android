package manulife.manulifesop.api.ObjectResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Test {
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

        @SerializedName("encode")
        @Expose
        public String encode;
        @SerializedName("decode")
        @Expose
        public Decode decode;
        public class Decode {

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
    }
}
