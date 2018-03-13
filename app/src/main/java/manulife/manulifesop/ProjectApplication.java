package manulife.manulifesop;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import java.util.HashMap;

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

    //new code
    private static HashMap<Integer,String> mProcessStepValue;
    private static HashMap<Integer,String> mProcessStepColor;

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

        //new code
        setHashmapProcessStep();

    }

    public static HashMap<Integer,String> getHashmapProcessStep(){
        return mProcessStepValue;
    }

    public static String getProcessStepColor(int step){
        return mProcessStepColor.get(step);
    }
    private void setHashmapProcessStep(){
        mProcessStepValue = new HashMap<>();
        mProcessStepValue.put(0,"Liên hệ khách hàng");
        mProcessStepValue.put(1,"Hẹn gặp khách hàng");
        mProcessStepValue.put(2,"Tư vấn khách hàng");
        mProcessStepValue.put(3,"Ký hợp đồng");

        mProcessStepColor = new HashMap<>();
        mProcessStepColor.put(0,"#f44236");
        mProcessStepColor.put(1,"#ffb200");
        mProcessStepColor.put(2,"#1d89e5");
        mProcessStepColor.put(3,"#4caf52");
    }

    /*private void initCustomFont() {
        CalligraphyConfig config = new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/UTM Helve.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build();

        CalligraphyConfig.initDefault(config);
    }*/
}
