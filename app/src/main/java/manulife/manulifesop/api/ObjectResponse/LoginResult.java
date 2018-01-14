package manulife.manulifesop.api.ObjectResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by trinm on 12/01/2018.
 */

public class LoginResult {
    @SerializedName("Result")
    @Expose
    private Result result;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public class Result {

        @SerializedName("ErrorCode")
        @Expose
        private Integer errorCode;
        @SerializedName("ErrorDescription")
        @Expose
        private String errorDescription;
        @SerializedName("Data")
        @Expose
        private Data data;

        public Integer getErrorCode() {
            return errorCode;
        }

        public void setErrorCode(Integer errorCode) {
            this.errorCode = errorCode;
        }

        public String getErrorDescription() {
            return errorDescription;
        }

        public void setErrorDescription(String errorDescription) {
            this.errorDescription = errorDescription;
        }

        public Data getData() {
            return data;
        }

        public void setData(Data data) {
            this.data = data;
        }

        public class Data {

            @SerializedName("staff_id")
            @Expose
            private String staffId;
            @SerializedName("full_name")
            @Expose
            private String fullName;
            @SerializedName("email")
            @Expose
            private String email;
            @SerializedName("avatar")
            @Expose
            private String avatar;
            @SerializedName("allow_create_project")
            @Expose
            private Integer allowCreateProject;

            public String getStaffId() {
                return staffId;
            }

            public void setStaffId(String staffId) {
                this.staffId = staffId;
            }

            public String getFullName() {
                return fullName;
            }

            public void setFullName(String fullName) {
                this.fullName = fullName;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public Integer getAllowCreateProject() {
                return allowCreateProject;
            }

            public void setAllowCreateProject(Integer allowCreateProject) {
                this.allowCreateProject = allowCreateProject;
            }

        }

    }
}
