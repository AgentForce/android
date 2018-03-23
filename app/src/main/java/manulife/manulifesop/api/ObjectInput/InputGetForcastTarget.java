package manulife.manulifesop.api.ObjectInput;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ADMIN on 3/22/2018.
 */

public class InputGetForcastTarget {
    @SerializedName("Fyc")
    @Expose
    public Integer fyc;//thu nhập mong muốn
    @SerializedName("Rate")
    @Expose
    public Integer rate;//hoa hồng
    @SerializedName("CaseSize")
    @Expose
    public Integer caseSize;//độ lớn hợp đồng
}
