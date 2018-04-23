package manulife.manulifesop;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import java.util.HashMap;
import java.util.List;

import manulife.manulifesop.api.ObjectResponse.CampaignMonth;
import manulife.manulifesop.api.ObjectResponse.CampaignRecruitMonth;
import manulife.manulifesop.api.ObjectResponse.ContactActivity;
import manulife.manulifesop.api.ObjectResponse.ContactDetail;
import manulife.manulifesop.api.ObjectResponse.ContactHistory;
import manulife.manulifesop.api.ObjectResponse.ContactMonth;
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

    //String for process status
    private static HashMap<String,String> mStringProcessStatusName;

    //String for process status sm
    private static HashMap<String,String> mStringProcessStatusNameSM;
    private static HashMap<String,String> mStringProcessStatusSM;

    //variable for campaign detail
    private CampaignMonth mDataCampaign;
    private int mCampaignWeekId;
    private ContactMonth mDataContactMonth;

    //variable for comapign sm detail
    private CampaignRecruitMonth mDataCampaignRecruit;

    //variables for sm cop
    private UsersList mCOP_added;
    private UsersList mCOP_calllater;
    private UsersList mCOP_refuse;
    private UsersList mCOP_done;

    //variables for sm mit
    private UsersList mMIT_added;
    private UsersList mMIT_relearn;
    private UsersList mMIT_refuse;
    private UsersList mMIT_done;

    //variable for sm granted code
    private UsersList mCode_added;
    private UsersList mCode_applied_agent_document;
    private UsersList mCode_applied_done;
    private UsersList mCode_wait_approve;
    private UsersList mCode_granted_code;

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
        setHashmapStringProcessStatusSM();
        setHashmapStringProcessStatusName();
        setHashmapStringProcessStatusNameSM();
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
        mStringProcessStatus.put("41","Đã chuyển sang nộp hồ sơ");
        mStringProcessStatus.put("42","Đã hoàn tất hồ sơ BHXH");
        mStringProcessStatus.put("43","Đã nộp hồ sơ");
        mStringProcessStatus.put("44","Chờ duyệt hồ sơ");
        mStringProcessStatus.put("45","Đã hoàn thành hợp đồng");
    }

    public String getStringProcessStatus(String keyProcessStatus){
        return mStringProcessStatus.get(keyProcessStatus);
    }

    public void setHashmapStringProcessStatusName(){
        mStringProcessStatusName = new HashMap<>();
        mStringProcessStatusName.put("11","Khách hàng liên hệ");
        mStringProcessStatusName.put("12","Khách hàng từ chối(Liên hệ)");
        mStringProcessStatusName.put("13","Khách hàng gọi lại sau(Liên hệ)");
        mStringProcessStatusName.put("14","Khách hàng đã liên hệ");
        mStringProcessStatusName.put("21","Khách hàng cần hẹn gặp");
        mStringProcessStatusName.put("22","Khách hàng từ chối(Hẹn gặp)");
        mStringProcessStatusName.put("23","Khách hàng gọi lại sau(Hẹn gặp)");
        mStringProcessStatusName.put("24","Khách hàng đã gặp");
        mStringProcessStatusName.put("31","Khách hàng tư vấn");
        mStringProcessStatusName.put("32","Khách hàng từ chối(Tư vấn)");
        mStringProcessStatusName.put("33","Khách hàng liên hệ sau(Tư vấn)");
        mStringProcessStatusName.put("34","Khách hàng đã tư vấn");
        mStringProcessStatusName.put("41","Khách hàng hứa nộp hồ sơ");
        mStringProcessStatusName.put("42","Khách hàng tất hồ sơ BHXH");
        mStringProcessStatusName.put("43","Khách hàng đã nộp hồ sơ");
        mStringProcessStatusName.put("44","Khách hàng chờ duyệt hồ sơ");
        mStringProcessStatusName.put("45","Khách hàng đã hoàn thành hợp đồng");
    }

    public String getStringProcessStatusName(String keyProcessStatus){
        return mStringProcessStatusName.get(keyProcessStatus);
    }

    public void setHashmapStringProcessStatusSM(){
        mStringProcessStatusSM = new HashMap<>();
        mStringProcessStatusSM.put("11","Đã thêm ứng viên");
        mStringProcessStatusSM.put("12","Đã chuyển sang từ chối");
        mStringProcessStatusSM.put("13","Đã chuyển sang gọi lại");
        mStringProcessStatusSM.put("21","Đã chuyển sang dự COP");
        mStringProcessStatusSM.put("22","Đã chuyển sang từ chối");
        mStringProcessStatusSM.put("23","Đã chuyển sang gọi lại sau");
        mStringProcessStatusSM.put("24","Đã hoàn thành thành COP");
        mStringProcessStatusSM.put("31","Đã chuyển học MIT");
        mStringProcessStatusSM.put("32","Đã chuyển sang từ chối");
        mStringProcessStatusSM.put("33","Đã chuyển học lại MIT");
        mStringProcessStatusSM.put("34","Đã học MIT");
        mStringProcessStatusSM.put("41","Đã chuyển sang nộp hồ sơ đại lý");
        mStringProcessStatusSM.put("42","Đã hoàn tất nộp hồ sơ đại lý");
        mStringProcessStatusSM.put("43","Đã nộp hồ sơ");
        mStringProcessStatusSM.put("44","Chờ cấp mã");
        mStringProcessStatusSM.put("45","Đã cấp mã");
    }

    public String getStringProcessStatusSM(String keyProcessStatus){
        return mStringProcessStatusSM.get(keyProcessStatus);
    }

    public void setHashmapStringProcessStatusNameSM(){
        mStringProcessStatusNameSM = new HashMap<>();
        mStringProcessStatusNameSM.put("11","Ứng viên khảo sát");
        mStringProcessStatusNameSM.put("12","Ứng viên từ chối(Khảo sát)");
        mStringProcessStatusNameSM.put("13","Ứng viên gọi lại sau(Khảo sát)");
        mStringProcessStatusNameSM.put("21","Ứng viên tham dự COP");
        mStringProcessStatusNameSM.put("22","Ứng viên từ chối(COP)");
        mStringProcessStatusNameSM.put("23","Ứng viên gọi lại sau(COP)");
        mStringProcessStatusNameSM.put("24","Ứng viên hoàn thành COP");
        mStringProcessStatusNameSM.put("31","Ứng viên học MIT");
        mStringProcessStatusNameSM.put("32","Ứng viên từ chối(MIT)");
        mStringProcessStatusNameSM.put("33","Ứng viên học lại MIT");
        mStringProcessStatusNameSM.put("34","Ứng viên hoàn thành MIT");
        mStringProcessStatusNameSM.put("41","Ứng viên nộp hồ sơ đại lý");
        mStringProcessStatusNameSM.put("42","Ứng viên hoàn tất nộp hồ sơ đại lý");
        mStringProcessStatusNameSM.put("43","Ứng viên đã nộp hồ sơ");
        mStringProcessStatusNameSM.put("44","Ứng viên chờ cấp mã");
        mStringProcessStatusNameSM.put("45","Ứng viên đã cấp mã");
    }

    public String getStringProcessStatusNameSM(String keyProcessStatus){
        return mStringProcessStatusNameSM.get(keyProcessStatus);
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
        mProcessStepValue.put(4,"Giới thiệu");

        mProcessStepColor = new HashMap<>();
        mProcessStepColor.put(0,"#f44236");
        mProcessStepColor.put(1,"#ffb200");
        mProcessStepColor.put(2,"#1d89e5");
        mProcessStepColor.put(3,"#4caf52");
        mProcessStepColor.put(4,"#fe7537");
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

    //init for all contact in month
    public void setContactMonth(ContactMonth data){
        this.mDataContactMonth = data;
    }
    public ContactMonth getContactMonth(){
        return mDataContactMonth;
    }

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

    //init fo campaign sm detail
    public void setCampaignRecruit(CampaignRecruitMonth data){
        this.mDataCampaignRecruit = data;
    }
    public CampaignRecruitMonth getCampaignRecruit(){
        return mDataCampaignRecruit;
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

    //init for sm cop
    public void setCOPAdded(UsersList data)
    {
        this.mCOP_added = data;
    }
    public UsersList getCOPAdded(){
        return this.mCOP_added;
    }
    public void setCOPCallLater(UsersList data)
    {
        this.mCOP_calllater = data;
    }
    public UsersList getCOPCallLater(){
        return this.mCOP_calllater;
    }
    public void setCOPRefuse(UsersList data)
    {
        this.mCOP_refuse = data;
    }
    public UsersList getCOPRefuse(){
        return this.mCOP_refuse;
    }
    public void setCOPDone(UsersList data)
    {
        this.mCOP_done = data;
    }
    public UsersList getCOPDone(){
        return this.mCOP_done;
    }

    //init for sm mit
    public void setMITAdded(UsersList data)
    {
        this.mMIT_added = data;
    }
    public UsersList getMITAdded(){
        return this.mMIT_added;
    }
    public void setMITRefuse(UsersList data)
    {
        this.mMIT_refuse = data;
    }
    public UsersList getMITRefuse(){
        return this.mMIT_refuse;
    }
    public void setMITRelearn(UsersList data)
    {
        this.mMIT_relearn = data;
    }
    public UsersList getMITRelearn(){
        return this.mMIT_relearn;
    }
    public void setMITDone(UsersList data)
    {
        this.mMIT_done = data;
    }
    public UsersList getMITDone(){
        return this.mMIT_done;
    }

    //init for sm granted code
    public void setCodeAdded(UsersList data)
    {
        this.mCode_added = data;
    }
    public UsersList getCodeAdded(){
        return this.mCode_added;
    }
    public void setCodeAppliedAgent(UsersList data)
    {
        this.mCode_applied_agent_document = data;
    }
    public UsersList getCodeAppliedAgent(){
        return this.mCode_applied_agent_document;
    }
    public void setCodeAppliedDone(UsersList data)
    {
        this.mCode_applied_done = data;
    }
    public UsersList getCodeAppliedDone(){
        return this.mCode_applied_done;
    }
    public void setCodeWaitApprove(UsersList data)
    {
        this.mCode_wait_approve = data;
    }
    public UsersList getCodeWaitApprove(){
        return this.mCode_wait_approve;
    }
    public void setCodeGranted(UsersList data)
    {
        this.mCode_granted_code = data;
    }
    public UsersList getCodeGranted(){
        return this.mCode_granted_code;
    }
}
