package manulife.manulifesop;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import java.util.HashMap;
import java.util.List;

import manulife.manulifesop.api.ObjectResponse.CampaignMonth;
import manulife.manulifesop.api.ObjectResponse.ContactActivity;
import manulife.manulifesop.api.ObjectResponse.ContactDetail;
import manulife.manulifesop.api.ObjectResponse.ContactHistory;
import manulife.manulifesop.api.ObjectResponse.UsersList;
import manulife.manulifesop.util.DeviceInfo;

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

    private static HashMap<Integer,String> mIncomeMonth;
    private static HashMap<Integer,String> mAge;
    private static HashMap<Integer,String> mMarriage;
    private static HashMap<Integer,String> mRelationship;
    private static HashMap<Integer,String> mSource;

    //String for process step and status step
    private static HashMap<String,String> mStringProcessStatus;

    //variable for campaign detail
    private CampaignMonth mDataCampaign;
    private int mCampaignWeekId;

    //variable for contact detail;
    private ContactDetail mContactDetail;
    private ContactActivity mContactActivity;
    private ContactHistory mContactHistory;
    private HashMap<Integer,String> mEventType;

    //variables for appointment activity
    private UsersList mAppointment_Need;
    private UsersList mAppointment_Seen;
    private UsersList mAppointment_Refuse;
    private UsersList mAppointment_CallLater;

    //variable for consultant
    private UsersList mConsultant_Need;
    private UsersList mConsultant_Refuse;
    private UsersList mConsultant_CallLater;
    private UsersList mConsultant_Seen;

    //variable for signed
    private UsersList mSign_Success;
    private UsersList mSign_NotApply;
    private UsersList mSign_BHXH;
    private UsersList mSign_Applied;
    private UsersList mSign_WaitApprove;

    //variable for introduce contact
    private UsersList mIntroduce;

    //variable for contact
    List<UsersList> mListDataContact;

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

        new DeviceInfo(this);
        //new code
        setHashmapStringProcessStatus();
        setHashmapProcessStep();
        setHashmapIncome();
        setHashmapAge();
        setHashmapMarriage();
        setHashmapRelationship();
        setHashmapSource();
        setHashmapEventType();


    }

    public void setHashmapEventType(){
        mEventType = new HashMap<>();
        mEventType.put(1,"Hẹn gặp lần đầu");
        mEventType.put(2,"Hẹn tư vấn");
        mEventType.put(3,"Hẹn chốt hợp đồng");
        mEventType.put(4,"Sự kiện khác");
    }

    public String getEventStringFromType(int type){
        return mEventType.get(type);
    }

    public void setHashmapStringProcessStatus(){
        mStringProcessStatus = new HashMap<>();
        mStringProcessStatus.put("11","Đã thêm liên hệ");
        mStringProcessStatus.put("12","Đã chuyển sang từ chối");
        mStringProcessStatus.put("13","Đã chuyển sang gọi lại");
        mStringProcessStatus.put("14","Đã liên hệ");
        mStringProcessStatus.put("21","Đã chuyển sang cần hẹn gặp");
        mStringProcessStatus.put("22","Đã chuyển sang từ chối");
        mStringProcessStatus.put("23","Đã chuyển sang gọi lại sau");
        mStringProcessStatus.put("24","Đã gặp");
        mStringProcessStatus.put("31","Đã chuyển sang tư vấn");
        mStringProcessStatus.put("32","Đã chuyển sang từ chối");
        mStringProcessStatus.put("33","Đã chuyển sang tư vấn");
        mStringProcessStatus.put("34","Đã tư vấn");
        mStringProcessStatus.put("41","Đã chuyển sang hứa nộp hồ sơ");
        mStringProcessStatus.put("42","Đã hoàn tất hồ sơ BHXH");
        mStringProcessStatus.put("43","Đã nộp hồ sơ");
        mStringProcessStatus.put("44","Chờ duyệt hồ sơ");
        mStringProcessStatus.put("45","Đã hoàn thành hợp đồng");
    }

    public String getStringProcessStatus(String keyProcessStatus){
        return mStringProcessStatus.get(keyProcessStatus);
    }

    public HashMap<Integer,String> getHashmapProcessStep(){
        return mProcessStepValue;
    }

    public String getProcessStepColor(int step){
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
    public String getIncomeString(int value){
        return mIncomeMonth.get(value);
    }
    private void setHashmapIncome(){
        mIncomeMonth = new HashMap<>();
        mIncomeMonth.put(1,"Dưới 10 triệu");
        mIncomeMonth.put(2,"Từ 10tr đến 20tr");
        mIncomeMonth.put(3,"Từ 20tr đến 30tr");
        mIncomeMonth.put(4,"Trên 30 triệu");

    }

    public String getAgeString(int value){
        return mAge.get(value);
    }
    private void setHashmapAge(){
        mAge = new HashMap<>();
        mAge.put(1,"Dưới hoặc bằng 25t");
        mAge.put(2,"Từ 26t đến 44t");
        mAge.put(3,"Từ 45t đến 64t");
        mAge.put(4,"Từ 64t trở lên");

    }

    public String getMarriageString(int value){
        return mMarriage.get(value);
    }
    private void setHashmapMarriage(){
        mMarriage = new HashMap<>();
        mMarriage.put(1,"Độc thân");
        mMarriage.put(2,"Kết hôn - chưa con");
        mMarriage.put(3,"Kết hôn - có con");

    }

    public String getRelationshipString(int value){
        return mRelationship.get(value);
    }
    private void setHashmapRelationship(){
        mRelationship = new HashMap<>();
        mRelationship.put(1,"Bình thường");
        mRelationship.put(2,"Thân thiết");
        mRelationship.put(3,"Rất thân thiết");

    }
    public String getSourceString(int value){
        return mSource.get(value);
    }
    private void setHashmapSource(){
        mSource = new HashMap<>();
        mSource.put(1,"Cá nhân");
        mSource.put(2,"Sự kiện");
        mSource.put(3,"Khách hàng giới thiệu");
        mSource.put(4,"Khác");

    }

    /*private void initCustomFont() {
        CalligraphyConfig config = new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/UTM Helve.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build();

        CalligraphyConfig.initDefault(config);
    }*/

    //init for campaign detail in client month
    public void setCampaign(CampaignMonth data){
        this.mDataCampaign = data;
    }
    public CampaignMonth getCampaign(){
        return mDataCampaign;
    }
    public void setCampaignWeekId(int id){
        this.mCampaignWeekId = id;
    }
    public int getCampaignWeekId(){
        return this.mCampaignWeekId;
    }

    //init for userdetail
    public void setContactDetail(ContactDetail data){
        this.mContactDetail = data;
    }
    public ContactDetail getContactDetail(){
        return mContactDetail;
    }
    public void setContactActivity(ContactActivity data){
        this.mContactActivity = data;
    }
    public ContactActivity getContactActivity(){
        return mContactActivity;
    }
    public void setContactHistory(ContactHistory data){
        this.mContactHistory = data;
    }
    public ContactHistory getContactHistory(){
        return mContactHistory;
    }

    //init for appointment activity
    public void setAppointMentNeed(UsersList data){
        this.mAppointment_Need = data;
    }
    public UsersList getAppointMentNeed(){
        return this.mAppointment_Need;
    }
    public void setAppointMentSeen(UsersList data){
        this.mAppointment_Seen = data;
    }
    public UsersList getAppointMentSeen(){
        return this.mAppointment_Seen;
    }
    public void setAppointMentRefuse(UsersList data){
        this.mAppointment_Refuse = data;
    }
    public UsersList getAppointMentRefuse(){
        return this.mAppointment_Refuse;
    }
    public void setAppointMentCallLater(UsersList data){
        this.mAppointment_CallLater = data;
    }
    public UsersList getAppointMentCallLater(){
        return this.mAppointment_CallLater;
    }

    //init for consultant activity
    public void setConsultantNeed(UsersList data){
        this.mConsultant_Need = data;
    }
    public UsersList getConsultantNeed(){
        return this.mConsultant_Need;
    }
    public void setConsultantRefuse(UsersList data){
        this.mConsultant_Refuse = data;
    }
    public UsersList getConsultantRefuse(){
        return this.mConsultant_Refuse;
    }
    public void setConsultantCallLater(UsersList data){
        this.mConsultant_CallLater = data;
    }
    public UsersList getConsultantCallLater(){
        return this.mConsultant_CallLater;
    }
    public void setConsultantSeen(UsersList data){
        this.mConsultant_Seen = data;
    }
    public UsersList getConsultantSeen(){
        return this.mConsultant_Seen;
    }

    //init for sign activity
    public void setSignSuccess(UsersList data){
        this.mSign_Success = data;
    }
    public UsersList getSignSuccess(){
        return this.mSign_Success;
    }
    public void setSignNotApply(UsersList data){
        this.mSign_NotApply = data;
    }
    public UsersList getSignNotApply(){
        return this.mSign_NotApply;
    }
    public void setSignBHXH(UsersList data){
        this.mSign_BHXH = data;
    }
    public UsersList getSignBHXH(){
        return this.mSign_BHXH;
    }
    public void setSignApplied(UsersList data){
        this.mSign_Applied = data;
    }
    public UsersList getSignApplied(){
        return this.mSign_Applied;
    }
    public void setSignWaitApprove(UsersList data){
        this.mSign_WaitApprove = data;
    }
    public UsersList getSignWaitApprove(){
        return this.mSign_WaitApprove;
    }

    //init for introduce
    public void setIntroduce(UsersList data){
        this.mIntroduce = data;
    }
    public UsersList getIntroduce(){
        return this.mIntroduce;
    }

    //init for contact

}
