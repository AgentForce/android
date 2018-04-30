package manulife.manulifesop.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import manulife.manulifesop.ProjectApplication;
import manulife.manulifesop.adapter.ObjectData.ContactPerson;

/**
 * Created by ADMIN on 1/12/2018.
 */

public class SOPSharedPreferences {

    private final String EXTRA_FIRST_USING_KEY = "extra_first_using_key";
    private final String EXTRA_ACCESS_TOKEN_KEY = "extra_access_token_key";
    private final String EXTRA_REFRESH_TOKEN_KEY = "extra_refresh_token_key";
    private final String EXTRA_USER_NAME = "extra_user_name";
    private final String EXTRA_USER_ID = "extra_user_id";

    private final String EXTRA_ADDED_CONTACT = "extra_added_contact";
    private final String EXTRA_IS_FA = "extra_is_fa";
    private final String EXTRA_LEVEL = "extra_level";
    private final String EXTRA_CAMPAIGN_START = "extra_campaign_start";
    private final String EXTRA_CAMPAIGN_END = "extra_campaign_end";


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

    public void saveIsFA(boolean isFA)
    {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putBoolean(EXTRA_IS_FA,isFA);
        editor.commit();
    }

    public boolean getIsFA()
    {
        return mPreferences.getBoolean(EXTRA_IS_FA,true);
    }

    public void saveLevel(int level)
    {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putInt(EXTRA_LEVEL,level);
        editor.commit();
    }

    public int getLevel()
    {
        return mPreferences.getInt(EXTRA_LEVEL,15);
    }

    public void saveTokenUser(String accessToken, String refreshToken)
    {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString(EXTRA_ACCESS_TOKEN_KEY,accessToken);
        editor.putString(EXTRA_REFRESH_TOKEN_KEY,refreshToken);
        editor.commit();
    }

    public void saveUser(String userName,int userID){
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString(EXTRA_USER_NAME,userName);
        editor.putInt(EXTRA_USER_ID,userID);
        editor.commit();
    }
    public int getUserID(){
        return mPreferences.getInt(EXTRA_USER_ID,-1);
    }
    public String getUserName(){
        return mPreferences.getString(EXTRA_USER_NAME,"");
    }

    public String getAccessToken()
    {
        return mPreferences.getString(EXTRA_ACCESS_TOKEN_KEY,"");
    }

    public String getRefreshToken()
    {
        return mPreferences.getString(EXTRA_REFRESH_TOKEN_KEY,"");
    }

    public void saveAccessToken(String accessToken)
    {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString(EXTRA_ACCESS_TOKEN_KEY,accessToken);
        editor.commit();
    }

    public void saveAddedPhone(String phone){
        Gson gson = new Gson();
        Type listType = new TypeToken<List<String>>() {}.getType();
        String json = mPreferences.getString(EXTRA_ADDED_CONTACT, "");
        List<String> data = gson.fromJson(json, listType);
        if(data == null) data = new ArrayList<>();
        data.add(phone);

        String jsonSave = gson.toJson(data,listType);
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString(EXTRA_ADDED_CONTACT,jsonSave);
        editor.commit();
    }
    public List<String> getListAddedPhone(){
        Gson gson = new Gson();
        Type listType = new TypeToken<List<String>>() {}.getType();
        String json = mPreferences.getString(EXTRA_ADDED_CONTACT, "");
        List<String> data = gson.fromJson(json, listType);
        return data;
    }

    public void saveCampaignStartEnd(String startDate, String endDate, String inputFormat){
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString(EXTRA_CAMPAIGN_START,Utils.convertStringTimeZoneDateToStringDate(
                startDate,inputFormat,"dd/MM/yyyy"
        ));
        editor.putString(EXTRA_CAMPAIGN_END,Utils.convertStringTimeZoneDateToStringDate(
                endDate,inputFormat,"dd/MM/yyyy"
        ));
        editor.commit();
    }

    public String getCampaignStart(){
        return mPreferences.getString(EXTRA_CAMPAIGN_START,"");
    }
    public String getCampaignEnd(){
        return mPreferences.getString(EXTRA_CAMPAIGN_END,"");
    }
}
