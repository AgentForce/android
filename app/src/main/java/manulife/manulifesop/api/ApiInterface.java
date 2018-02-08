package manulife.manulifesop.api;

import io.reactivex.Observable;
import manulife.manulifesop.api.ObjectInput.InputCreatePass;
import manulife.manulifesop.api.ObjectInput.InputLoginData;
import manulife.manulifesop.api.ObjectInput.InputRequestOTP;
import manulife.manulifesop.api.ObjectInput.InputVerifyOTP;
import manulife.manulifesop.api.ObjectResponse.CheckUser;
import manulife.manulifesop.api.ObjectResponse.CheckVersion;
import manulife.manulifesop.api.ObjectResponse.LoginResult;
import manulife.manulifesop.api.ObjectResponse.RequestOTP;
import manulife.manulifesop.api.ObjectResponse.VerifyOTP;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by trinm on 12/01/2018.
 */

public interface ApiInterface {
    @FormUrlEncoded
    @POST("/index/check-version")
    Observable<CheckVersion> checkVersion(@Field("platform") String android);

    @POST("users/login")
    Observable<LoginResult> login(@Header("clientid") String clientid, @Header("versionos") String versionos, @Header("versionapp") String versionapp,
                                  @Header("devicename") String devicename, @Header("imei") String imei, @Header("checksum") String checksum,
                                  @Body InputLoginData data);

    @GET("users/check/{phone}/{username}")
    Observable<CheckUser> checkUser(@Header("clientid") String clientid, @Header("versionos") String versionos, @Header("versionapp") String versionapp,
                                    @Header("devicename") String devicename, @Header("imei") String imei, @Header("checksum") String checksum,
                                    @Path(value = "phone", encoded = false) String phone, @Path(value = "username", encoded = false) String username);

    @POST("users/opt/request")
    Observable<RequestOTP> requestOTP(@Header("clientid") String clientid, @Header("versionos") String versionos, @Header("versionapp") String versionapp,
                                      @Header("devicename") String devicename, @Header("imei") String imei, @Header("checksum") String checksum,
                                      @Body InputRequestOTP data);

    @POST("users/otp/verify")
    Observable<VerifyOTP> verifyOTP(@Header("clientid") String clientid, @Header("versionos") String versionos, @Header("versionapp") String versionapp,
                                    @Header("devicename") String devicename, @Header("imei") String imei, @Header("checksum") String checksum,
                                    @Body InputVerifyOTP data);

    @PUT("users/setpassword")
    Observable<VerifyOTP> setPassword(@Header("clientid") String clientid, @Header("versionos") String versionos, @Header("versionapp") String versionapp,
                                      @Header("devicename") String devicename, @Header("imei") String imei, @Header("checksum") String checksum,
                                      @Body InputCreatePass data);

    @GET("campaigns/check")
    Observable<VerifyOTP> checkCampaign(@Header("clientid") String clientid, @Header("versionos") String versionos, @Header("versionapp") String versionapp,
                                      @Header("devicename") String devicename, @Header("imei") String imei);


}
