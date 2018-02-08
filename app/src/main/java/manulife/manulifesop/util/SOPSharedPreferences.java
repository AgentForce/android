package manulife.manulifesop.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import manulife.manulifesop.ProjectApplication;

/**
 * Created by ADMIN on 1/12/2018.
 */

public class SOPSharedPreferences {

    private static final String EXTRA_FIRST_USING_KEY = "extra_first_using_key";
    private static final String EXTRA_ACCESS_TOKEN_KEY = "extra_access_token_key";
    private static final String EXTRA_REFRESH_TOKEN_KEY = "extra_refresh_token_key";


    private SharedPreferences mPreferences;
    private static SOPSharedPreferences mSOPSharedPreferences;

    private SOPSharedPreferences(Context context) {
        mPreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
    }

    public static SOPSharedPreferences getInstance(Context context) {
        if (mSOPSharedPreferences == null) {
            mSOPSharedPreferences = new SOPSharedPreferences(context);
        }
        return mSOPSharedPreferences;
    }

    public void saveFirstUsing()
    {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putBoolean(EXTRA_FIRST_USING_KEY,true);
        editor.commit();
    }

    public boolean isFirstUsing()
    {
        return mPreferences.getBoolean(EXTRA_FIRST_USING_KEY,false);
    }

    public void saveToken(String accessToken, String refreshToken)
    {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString(EXTRA_ACCESS_TOKEN_KEY,accessToken);
        editor.putString(EXTRA_REFRESH_TOKEN_KEY,refreshToken);
        editor.commit();
    }

    public String getAccessToken()
    {
        return mPreferences.getString(EXTRA_ACCESS_TOKEN_KEY,"");
    }

    public String getRefreshToken()
    {
        return mPreferences.getString(EXTRA_REFRESH_TOKEN_KEY,"");
    }

}
