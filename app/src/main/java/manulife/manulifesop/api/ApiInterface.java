package manulife.manulifesop.api;

import io.reactivex.Observable;
import manulife.manulifesop.api.ObjectInput.LoginData;
import manulife.manulifesop.api.ObjectResponse.CheckVersion;
import manulife.manulifesop.api.ObjectResponse.LoginResult;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by trinm on 12/01/2018.
 */

public interface ApiInterface {
    @FormUrlEncoded
    @POST("/index/check-version")
    Observable<CheckVersion> checkVersion(@Field("platform") String android);

    @POST("/login")
    Observable<LoginResult> login(@Body LoginData data);


}
