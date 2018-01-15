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
    private static final String EXTRA_CREATED_KEY = "extra_created_key";

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

    public void saveCreatedPlan()
    {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putBoolean(EXTRA_CREATED_KEY,true);
        editor.commit();
    }

    public boolean isCreatedPlan()
    {
        return mPreferences.getBoolean(EXTRA_CREATED_KEY,false);
    }

}
