package manulife.manulifesop;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

/*
import com.crashlytics.android.Crashlytics;
import com.facebook.FacebookSdk;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.thecoffeehouse.guestapp.service.ArabicaService;
import com.thecoffeehouse.guestapp.util.PreferenceUtils;

import io.fabric.sdk.android.Fabric;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
*/

/**
 * Created by trinm on 12/01/2018.
 */
public class ProjectApplication extends MultiDexApplication {

    private static ProjectApplication instance = null;
    //private static Context context;
    private static final Object instanceLock = new Object();

    public void setInstance(ProjectApplication application) {
        synchronized (instanceLock) {
            instance = application;
        }
    }

    public static ProjectApplication getInstance() {
        return instance;
    }

    /*public static Context getAppContext() {
        return ProjectApplication.context;
    }*/

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //ProjectApplication.context = getApplicationContext();
        //Fabric.with(this, new Crashlytics());
        //FirebaseAnalytics.getInstance(this);
        setInstance(this);
        //arabicaService = new ArabicaService();
        //FacebookSdk.sdkInitialize(getApplicationContext());
        //PreferenceUtils.init(this);
        //initCustomFont();
    }

    /*private void initCustomFont() {
        CalligraphyConfig config = new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/UTM Helve.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build();

        CalligraphyConfig.initDefault(config);
    }*/
}
