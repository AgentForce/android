package manulife.manulifesop.api;

import io.reactivex.Observable;
import manulife.manulifesop.api.ObjectInput.InputAddContact;
import manulife.manulifesop.api.ObjectInput.InputChangeCampaignWeek;
import manulife.manulifesop.api.ObjectInput.InputChangeContactStatus;
import manulife.manulifesop.api.ObjectInput.InputChangeRelead;
import manulife.manulifesop.api.ObjectInput.InputCreateCampaign;
import manulife.manulifesop.api.ObjectInput.InputCreateEvent;
import manulife.manulifesop.api.ObjectInput.InputCreatePass;
import manulife.manulifesop.api.ObjectInput.InputGetForcastTarget;
import manulife.manulifesop.api.ObjectInput.InputIncreaseContact;
import manulife.manulifesop.api.ObjectInput.InputIntroduceContact;
import manulife.manulifesop.api.ObjectInput.InputLoginData;
import manulife.manulifesop.api.ObjectInput.InputRequestOTP;
import manulife.manulifesop.api.ObjectInput.InputSubmitContract;
import manulife.manulifesop.api.ObjectInput.InputUpdateContact;
import manulife.manulifesop.api.ObjectInput.InputVerifyOTP;
import manulife.manulifesop.api.ObjectResponse.ActivitiHist;
import manulife.manulifesop.api.ObjectResponse.ActivityDetail;
import manulife.manulifesop.api.ObjectResponse.BaseResponse;
import manulife.manulifesop.api.ObjectResponse.CampaignForcastTarget;
import manulife.manulifesop.api.ObjectResponse.CampaignMonth;
import manulife.manulifesop.api.ObjectResponse.CheckUser;
import manulife.manulifesop.api.ObjectResponse.CheckVersion;
import manulife.manulifesop.api.ObjectResponse.ContactActivity;
import manulife.manulifesop.api.ObjectResponse.ContactHistory;
import manulife.manulifesop.api.ObjectResponse.DashboardResult;
import manulife.manulifesop.api.ObjectResponse.EventsCreate;
import manulife.manulifesop.api.ObjectResponse.EventsMonth;
import manulife.manulifesop.api.ObjectResponse.EventsOneDay;
import manulife.manulifesop.api.ObjectResponse.LoginResult;
import manulife.manulifesop.api.ObjectResponse.RequestOTP;
import manulife.manulifesop.api.ObjectResponse.ContactDetail;
import manulife.manulifesop.api.ObjectResponse.UserProfile;
import manulife.manulifesop.api.ObjectResponse.UsersList;
import manulife.manulifesop.api.ObjectResponse.VerifyOTP;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

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
    @GET("users/profile/{username}")
    Observable<UserProfile> getUserProfile(@Header("Authorization") String accessToken,@Header("clientid") String clientid, @Header("versionos") String versionos, @Header("versionapp") String versionapp,
                                           @Header("devicename") String devicename, @Header("imei") String imei,
                                           @Path(value = "username", encoded = false) String username);

    @GET("users/check/{phone}/{username}")
    Observable<CheckUser> checkUser(@Header("clientid") String clientid, @Header("versionos") String versionos, @Header("versionapp") String versionapp,
                                    @Header("devicename") String devicename, @Header("imei") String imei, @Header("checksum") String checksum,
                                    @Path(value = "phone", encoded = false) String phone, @Path(value = "username", encoded = false) String username);

    @POST("users/opt/request")
    Observable<RequestOTP> requestOTP(@Header("Authorization") String accessToken, @Header("clientid") String clientid, @Header("versionos") String versionos, @Header("versionapp") String versionapp,
                                      @Header("devicename") String devicename, @Header("imei") String imei, @Header("checksum") String checksum,
                                      @Body InputRequestOTP data);

    @POST("users/otp/verify")
    Observable<VerifyOTP> verifyOTP(@Header("Authorization") String accessToken, @Header("clientid") String clientid, @Header("versionos") String versionos, @Header("versionapp") String versionapp,
                                    @Header("devicename") String devicename, @Header("imei") String imei, @Header("checksum") String checksum,
                                    @Body InputVerifyOTP data);

    @PUT("users/setpassword")
    Observable<VerifyOTP> setPassword(@Header("Authorization") String accessToken, @Header("clientid") String clientid, @Header("versionos") String versionos, @Header("versionapp") String versionapp,
                                      @Header("devicename") String devicename, @Header("imei") String imei, @Header("checksum") String checksum,
                                      @Body InputCreatePass data);

    @GET("campaigns/check")
    Observable<VerifyOTP> checkCampaign(@Header("Authorization") String accessToken, @Header("clientid") String clientid, @Header("versionos") String versionos, @Header("versionapp") String versionapp,
                                        @Header("devicename") String devicename, @Header("imei") String imei);

    @POST("campaigns/fa")
    Observable<VerifyOTP> createCampaign(@Header("Authorization") String accessToken, @Header("clientid") String clientid, @Header("versionos") String versionos, @Header("versionapp") String versionapp,
                                         @Header("devicename") String devicename, @Header("imei") String imei, @Header("checksum") String checksum,
                                         @Body InputCreateCampaign data);

    @GET("campaigns/dashboard/{type}")
    Observable<DashboardResult> dashBoard(@Header("Authorization") String accessToken, @Header("clientid") String clientid, @Header("versionos") String versionos, @Header("versionapp") String versionapp,
                                          @Header("devicename") String devicename, @Header("imei") String imei,
                                          @Path(value = "type", encoded = false) String type);

    @GET("leads")
    Observable<ActivitiHist> activitiesDashboard(@Header("Authorization") String accessToken, @Header("clientid") String clientid, @Header("versionos") String versionos, @Header("versionapp") String versionapp,
                                                 @Header("devicename") String devicename, @Header("imei") String imei,
                                                 @Query("page") int page, @Query("limit") int limit);

    @GET("campaigns/period/{period}")
    Observable<CampaignMonth> campaignMonth(@Header("Authorization") String accessToken, @Header("clientid") String clientid, @Header("versionos") String versionos, @Header("versionapp") String versionapp,
                                            @Header("devicename") String devicename, @Header("imei") String imei,
                                            @Path(value = "period", encoded = false) int period);

    @GET("activities/day/{date}")
    Observable<EventsOneDay> getEventsDay(@Header("Authorization") String accessToken, @Header("clientid") String clientid, @Header("versionos") String versionos, @Header("versionapp") String versionapp,
                                          @Header("devicename") String devicename, @Header("imei") String imei,
                                          @Path(value = "date", encoded = false) String date);

    @GET("activities/rangedate/{from}/{to}")
    Observable<EventsMonth> getEventsMonth(@Header("Authorization") String accessToken, @Header("clientid") String clientid, @Header("versionos") String versionos, @Header("versionapp") String versionapp,
                                           @Header("devicename") String devicename, @Header("imei") String imei,
                                           @Path(value = "from", encoded = false) String fromDate,
                                           @Path(value = "to", encoded = false) String toDate);

    @GET("leads/{period}/{processstep}/{status}")
    Observable<UsersList> getUserList(@Header("Authorization") String accessToken, @Header("clientid") String clientid, @Header("versionos") String versionos, @Header("versionapp") String versionapp,
                                      @Header("devicename") String devicename, @Header("imei") String imei,
                                      @Path(value = "period", encoded = false) int period,
                                      @Path(value = "processstep", encoded = false) int processstep,
                                      @Path(value = "status", encoded = false) int status,
                                      @Query("page") int page, @Query("limit") int limit);

    @GET("releads/period/{period}")
    Observable<UsersList> getIntorduceUserList(@Header("Authorization") String accessToken, @Header("clientid") String clientid, @Header("versionos") String versionos, @Header("versionapp") String versionapp,
                                               @Header("devicename") String devicename, @Header("imei") String imei,
                                               @Path(value = "period", encoded = false) int period,
                                               @Query("page") int page, @Query("limit") int limit);

    @POST("leads")
    Observable<BaseResponse> addContact(@Header("Authorization") String accessToken, @Header("clientid") String clientid, @Header("versionos") String versionos, @Header("versionapp") String versionapp,
                                        @Header("devicename") String devicename, @Header("imei") String imei, @Header("checksum") String checksum,
                                        @Body InputAddContact data);
    @PUT("leads/{id}")
    Observable<BaseResponse> updateContact(@Header("Authorization") String accessToken, @Header("clientid") String clientid, @Header("versionos") String versionos, @Header("versionapp") String versionapp,
                                                 @Header("devicename") String devicename, @Header("imei") String imei, @Header("checksum") String checksum,
                                                 @Path(value = "id", encoded = false) int id,
                                                 @Body InputUpdateContact data);

    @GET("leads/{id}")
    Observable<ContactDetail> getContactDetail(@Header("Authorization") String accessToken, @Header("clientid") String clientid, @Header("versionos") String versionos, @Header("versionapp") String versionapp,
                                               @Header("devicename") String devicename, @Header("imei") String imei,
                                               @Path(value = "id", encoded = false) int id);

    @GET("activities/leadid/{leadid}")
    Observable<ContactActivity> getContactActivity(@Header("Authorization") String accessToken, @Header("clientid") String clientid, @Header("versionos") String versionos, @Header("versionapp") String versionapp,
                                                   @Header("devicename") String devicename, @Header("imei") String imei,
                                                   @Path(value = "leadid", encoded = false) int leadid);

    @GET("leads/history/{leadid}")
    Observable<ContactHistory> getContactHistory(@Header("Authorization") String accessToken, @Header("clientid") String clientid, @Header("versionos") String versionos, @Header("versionapp") String versionapp,
                                                 @Header("devicename") String devicename, @Header("imei") String imei,
                                                 @Path(value = "leadid", encoded = false) int leadid,
                                                 @Query("page") int page, @Query("limit") int limit);

    @POST("releads")
    Observable<BaseResponse> addIntroduceContact(@Header("Authorization") String accessToken, @Header("clientid") String clientid, @Header("versionos") String versionos, @Header("versionapp") String versionapp,
                                                 @Header("devicename") String devicename, @Header("imei") String imei, @Header("checksum") String checksum,
                                                 @Body InputIntroduceContact data);

    @POST("releads/movetolead")
    Observable<BaseResponse> changeIntroduceContact(@Header("Authorization") String accessToken, @Header("clientid") String clientid, @Header("versionos") String versionos, @Header("versionapp") String versionapp,
                                                    @Header("devicename") String devicename, @Header("imei") String imei, @Header("checksum") String checksum,
                                                    @Body InputChangeRelead data);

    @GET("activities/{id}")
    Observable<ActivityDetail> getActivityDetail(@Header("Authorization") String accessToken, @Header("clientid") String clientid, @Header("versionos") String versionos, @Header("versionapp") String versionapp,
                                                 @Header("devicename") String devicename, @Header("imei") String imei,
                                                 @Path(value = "id", encoded = false) int id);

    @PUT("leads/status/{id}")
    Observable<BaseResponse> changeContactStatus(@Header("Authorization") String accessToken, @Header("clientid") String clientid, @Header("versionos") String versionos, @Header("versionapp") String versionapp,
                                                 @Header("devicename") String devicename, @Header("imei") String imei, @Header("checksum") String checksum,
                                                 @Path(value = "id", encoded = false) int id,
                                                 @Body InputChangeContactStatus data);

    @DELETE("activities/{id}")
    Observable<BaseResponse> deleteEvent(@Header("Authorization") String accessToken, @Header("clientid") String clientid, @Header("versionos") String versionos, @Header("versionapp") String versionapp,
                                         @Header("devicename") String devicename, @Header("imei") String imei,
                                         @Path(value = "id", encoded = false) int id);
    @POST("activities")
    Observable<EventsCreate> createActivity(@Header("Authorization") String accessToken, @Header("clientid") String clientid, @Header("versionos") String versionos, @Header("versionapp") String versionapp,
                                            @Header("devicename") String devicename, @Header("imei") String imei, @Header("checksum") String checksum,
                                            @Body InputCreateEvent data);
    @PUT("campaigns/incrementcontract/{period}")
    Observable<BaseResponse> increaseContactCampaign(@Header("Authorization") String accessToken, @Header("clientid") String clientid, @Header("versionos") String versionos, @Header("versionapp") String versionapp,
                                                 @Header("devicename") String devicename, @Header("imei") String imei, @Header("checksum") String checksum,
                                                 @Path(value = "period", encoded = false) int period,
                                                 @Body InputIncreaseContact data);
    @PUT("campaigns/target/{period}")
    Observable<BaseResponse> changeCampaignWeek(@Header("Authorization") String accessToken, @Header("clientid") String clientid, @Header("versionos") String versionos, @Header("versionapp") String versionapp,
                                                @Header("devicename") String devicename, @Header("imei") String imei, @Header("checksum") String checksum,
                                                @Path(value = "period", encoded = false) int period,
                                                @Body InputChangeCampaignWeek data);


    @POST("campaigns/forcasttarget")
    Observable<CampaignForcastTarget> getCampaignForcast(@Header("Authorization") String accessToken, @Header("clientid") String clientid, @Header("versionos") String versionos, @Header("versionapp") String versionapp,
                                                     @Header("devicename") String devicename, @Header("imei") String imei, @Header("checksum") String checksum,
                                                     @Body InputGetForcastTarget data);

    @POST("leads/contract")
    Observable<BaseResponse> submitContract(@Header("Authorization") String accessToken, @Header("clientid") String clientid, @Header("versionos") String versionos, @Header("versionapp") String versionapp,
                                                         @Header("devicename") String devicename, @Header("imei") String imei, @Header("checksum") String checksum,
                                                         @Body InputSubmitContract data);
}
