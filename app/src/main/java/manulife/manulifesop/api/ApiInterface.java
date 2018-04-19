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
import manulife.manulifesop.api.ObjectInput.InputRefreshToken;
import manulife.manulifesop.api.ObjectInput.InputRequestOTP;
import manulife.manulifesop.api.ObjectInput.InputSubmitContract;
import manulife.manulifesop.api.ObjectInput.InputUpdateContact;
import manulife.manulifesop.api.ObjectInput.InputUpdateEvent;
import manulife.manulifesop.api.ObjectInput.InputVerifyOTP;
import manulife.manulifesop.api.ObjectResponse.ActivitiHist;
import manulife.manulifesop.api.ObjectResponse.ActivityDetail;
import manulife.manulifesop.api.ObjectResponse.BaseResponse;
import manulife.manulifesop.api.ObjectResponse.CampaignForcastTarget;
import manulife.manulifesop.api.ObjectResponse.CampaignMonth;
import manulife.manulifesop.api.ObjectResponse.CampaignRecruitMonth;
import manulife.manulifesop.api.ObjectResponse.CheckUser;
import manulife.manulifesop.api.ObjectResponse.CheckVersion;
import manulife.manulifesop.api.ObjectResponse.ContactActivity;
import manulife.manulifesop.api.ObjectResponse.ContactHistory;
import manulife.manulifesop.api.ObjectResponse.ContactMonth;
import manulife.manulifesop.api.ObjectResponse.DashboardResult;
import manulife.manulifesop.api.ObjectResponse.DashboardSMResult;
import manulife.manulifesop.api.ObjectResponse.EventsCreate;
import manulife.manulifesop.api.ObjectResponse.EventsMonth;
import manulife.manulifesop.api.ObjectResponse.EventsOneDay;
import manulife.manulifesop.api.ObjectResponse.LoginResult;
import manulife.manulifesop.api.ObjectResponse.RecruitHistory;
import manulife.manulifesop.api.ObjectResponse.RefreshToken;
import manulife.manulifesop.api.ObjectResponse.RequestOTP;
import manulife.manulifesop.api.ObjectResponse.ContactDetail;
import manulife.manulifesop.api.ObjectResponse.UMForcastRecruit;
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
    Observable<UserProfile> getUserProfile(@Header("Authorization") String accessToken, @Header("clientid") String clientid, @Header("versionos") String versionos, @Header("versionapp") String versionapp,
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

    @POST("users/refreshtoken")
    Observable<RefreshToken> refreshToken(@Header("clientid") String clientid, @Header("versionos") String versionos, @Header("versionapp") String versionapp,
                                          @Header("devicename") String devicename, @Header("imei") String imei, @Header("checksum") String checksum,
                                          @Body InputRefreshToken data);

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
                                      @Query("page") int page, @Query("limit") int limit,
                                      @Query("search") String search);

    @GET("releads/period/{period}")
    Observable<UsersList> getIntorduceUserList(@Header("Authorization") String accessToken, @Header("clientid") String clientid, @Header("versionos") String versionos, @Header("versionapp") String versionapp,
                                               @Header("devicename") String devicename, @Header("imei") String imei,
                                               @Path(value = "period", encoded = false) int period, @Query("page") int page,
                                               @Query("limit") int limit, @Query("search") String search);

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

    @PUT("activities/{id}")
    Observable<BaseResponse> updateEvent(@Header("Authorization") String accessToken, @Header("clientid") String clientid, @Header("versionos") String versionos, @Header("versionapp") String versionapp,
                                         @Header("devicename") String devicename, @Header("imei") String imei, @Header("checksum") String checksum,
                                         @Path(value = "id", encoded = false) int id, @Body InputUpdateEvent data);

    @PUT("activities/complete/{id}")
    Observable<BaseResponse> updateEventDone(@Header("Authorization") String accessToken, @Header("clientid") String clientid, @Header("versionos") String versionos, @Header("versionapp") String versionapp,
                                             @Header("devicename") String devicename, @Header("imei") String imei, @Header("checksum") String checksum,
                                             @Path(value = "id", encoded = false) int id);

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

    @PUT("campaigns/forwardtarget")
    Observable<BaseResponse> forwardTarget(@Header("Authorization") String accessToken, @Header("clientid") String clientid, @Header("versionos") String versionos, @Header("versionapp") String versionapp,
                                           @Header("devicename") String devicename, @Header("imei") String imei);

    @GET("leads/search/{month}")
    Observable<ContactMonth> getContactMonth(@Header("Authorization") String accessToken, @Header("clientid") String clientid, @Header("versionos") String versionos, @Header("versionapp") String versionapp,
                                             @Header("devicename") String devicename, @Header("imei") String imei,
                                             @Path(value = "month", encoded = false) int month,
                                             @Query("search") String search,
                                             @Query("page") int page, @Query("limit") int limit);


    //-----------------------SM Group-----------------------
    @GET("campaigns/dashboardrecruit/{type}")
    Observable<DashboardSMResult> dashBoardSM(@Header("Authorization") String accessToken, @Header("clientid") String clientid, @Header("versionos") String versionos, @Header("versionapp") String versionapp,
                                            @Header("devicename") String devicename, @Header("imei") String imei,
                                            @Path(value = "type", encoded = false) String type);
    @GET("campaigns/recruit/{month}")
    Observable<CampaignRecruitMonth> campaignRecruiMonth(@Header("Authorization") String accessToken, @Header("clientid") String clientid, @Header("versionos") String versionos, @Header("versionapp") String versionapp,
                                                         @Header("devicename") String devicename, @Header("imei") String imei,
                                                         @Path(value = "month", encoded = false) int month);

    @GET("recruits")
    Observable<RecruitHistory> recruitHistory(@Header("Authorization") String accessToken, @Header("clientid") String clientid, @Header("versionos") String versionos, @Header("versionapp") String versionapp,
                                                   @Header("devicename") String devicename, @Header("imei") String imei,
                                                   @Query("page") int page, @Query("limit") int limit);

    @GET("recruits/search/{month}")
    Observable<ContactMonth> getContactMonthRecruitment(@Header("Authorization") String accessToken, @Header("clientid") String clientid, @Header("versionos") String versionos, @Header("versionapp") String versionapp,
                                             @Header("devicename") String devicename, @Header("imei") String imei,
                                             @Path(value = "month", encoded = false) int month,
                                             @Query("search") String search,
                                             @Query("page") int page, @Query("limit") int limit);

    @GET("recruits/{period}/{processstep}/{status}")
    Observable<UsersList> getUserListRecruit(@Header("Authorization") String accessToken, @Header("clientid") String clientid, @Header("versionos") String versionos, @Header("versionapp") String versionapp,
                                      @Header("devicename") String devicename, @Header("imei") String imei,
                                      @Path(value = "period", encoded = false) int period,
                                      @Path(value = "processstep", encoded = false) int processstep,
                                      @Path(value = "status", encoded = false) int status,
                                      @Query("page") int page, @Query("limit") int limit,
                                      @Query("search") String search);

    @POST("recruits")
    Observable<BaseResponse> addRecruit(@Header("Authorization") String accessToken, @Header("clientid") String clientid, @Header("versionos") String versionos, @Header("versionapp") String versionapp,
                                        @Header("devicename") String devicename, @Header("imei") String imei, @Header("checksum") String checksum,
                                        @Body InputAddContact data);

    @POST("releads/recruit/movetolead")
    Observable<BaseResponse> changeIntroduceRecruit(@Header("Authorization") String accessToken, @Header("clientid") String clientid, @Header("versionos") String versionos, @Header("versionapp") String versionapp,
                                                    @Header("devicename") String devicename, @Header("imei") String imei, @Header("checksum") String checksum,
                                                    @Body InputChangeRelead data);

    @PUT("recruits/{id}")
    Observable<BaseResponse> updateRecruit(@Header("Authorization") String accessToken, @Header("clientid") String clientid, @Header("versionos") String versionos, @Header("versionapp") String versionapp,
                                           @Header("devicename") String devicename, @Header("imei") String imei, @Header("checksum") String checksum,
                                           @Path(value = "id", encoded = false) int id,
                                           @Body InputUpdateContact data);

    @POST("releads/recruit")
    Observable<BaseResponse> addIntroduceRecruit(@Header("Authorization") String accessToken, @Header("clientid") String clientid, @Header("versionos") String versionos, @Header("versionapp") String versionapp,
                                                 @Header("devicename") String devicename, @Header("imei") String imei, @Header("checksum") String checksum,
                                                 @Body InputIntroduceContact data);
    @GET("releads/recruit/period/{period}")
    Observable<UsersList> getIntorduceRecruitList(@Header("Authorization") String accessToken, @Header("clientid") String clientid, @Header("versionos") String versionos, @Header("versionapp") String versionapp,
                                               @Header("devicename") String devicename, @Header("imei") String imei,
                                               @Path(value = "period", encoded = false) int period, @Query("page") int page,
                                               @Query("limit") int limit, @Query("search") String search);
    @PUT("recruits/status/{id}")
    Observable<BaseResponse> changeRecruitStatus(@Header("Authorization") String accessToken, @Header("clientid") String clientid, @Header("versionos") String versionos, @Header("versionapp") String versionapp,
                                                 @Header("devicename") String devicename, @Header("imei") String imei, @Header("checksum") String checksum,
                                                 @Path(value = "id", encoded = false) int id,
                                                 @Body InputChangeContactStatus data);
    @GET("recruits/{id}")
    Observable<ContactDetail> getRecruitDetail(@Header("Authorization") String accessToken, @Header("clientid") String clientid, @Header("versionos") String versionos, @Header("versionapp") String versionapp,
                                               @Header("devicename") String devicename, @Header("imei") String imei,
                                               @Path(value = "id", encoded = false) int id);
    @GET("campaigns/um/forcast/recruit")
    Observable<UMForcastRecruit> getUmForcastRecruit(@Header("Authorization") String accessToken, @Header("clientid") String clientid, @Header("versionos") String versionos, @Header("versionapp") String versionapp,
                                                     @Header("devicename") String devicename, @Header("imei") String imei);

}
