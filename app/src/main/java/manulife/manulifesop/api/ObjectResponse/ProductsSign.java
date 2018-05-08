package manulife.manulifesop.api.ObjectResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductsSign {
    @SerializedName("statusCode")
    @Expose
    public Integer statusCode;
    @SerializedName("data")
    @Expose
    public Data data;
    @SerializedName("msgCode")
    @Expose
    public String msgCode;
    @SerializedName("msg")
    @Expose
    public String msg;
    public class Data {

        @SerializedName("main")
        @Expose
        public List<Main> main = null;
        @SerializedName("sub")
        @Expose
        public List<Sub> sub = null;
        public class Main {

            @SerializedName("Id")
            @Expose
            public Integer id;
            @SerializedName("Title")
            @Expose
            public String title;
            @SerializedName("Type")
            @Expose
            public String type;
            @SerializedName("Excerpt")
            @Expose
            public Object excerpt;
            @SerializedName("DeletedAt")
            @Expose
            public Object deletedAt;
        }
        public class Sub {

            @SerializedName("Id")
            @Expose
            public Integer id;
            @SerializedName("Title")
            @Expose
            public String title;
            @SerializedName("Type")
            @Expose
            public String type;
            @SerializedName("Excerpt")
            @Expose
            public Object excerpt;
            @SerializedName("DeletedAt")
            @Expose
            public Object deletedAt;
        }
    }
}
