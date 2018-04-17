package manulife.manulifesop.util;

import java.util.HashMap;

/**
 * Created by Chick on 1/30/2018.
 */

public class Contants {
    public static String clientID = "sopandroid";

    //type = appointment, seen, calllater
    //customer type
    public static final String APPOINTMENT = "Khách hàng hẹn gặp";
    public static final String SEEN = "Khách hàng đã hẹn gặp";
    public static final String CALLLATER = "Khách hàng gọi lại sau";
    public static final String REFUSE = "Khách hàng từ chối";
    public static final String CONTACT = "Khách hàng liên hệ";
    public static final String INTRODURE = "Khách hàng giới thiệu";
    public static final String CONSULTANT = "Khách hàng tư vấn";
    public static final String CONSULTATION_APPOINTMENT = "Khách hàng đã hẹn tư vấn";

    public static final String SIGNED_NOT_APPLY_STRING = "Khách hàng chưa nộp hồ sơ";
    public static final String SIGNED_BHXH_STRING = "Khách hàng hoàn tất BHXH";
    public static final String SIGNED_APPLIED_STRING = "Khách hàng đã nộp hồ sơ";
    public static final String SIGNED_WAIT_APPROVE_STRING = "Khách hàng chờ duyệt hồ sơ";
    public static final String SIGNED_SUCCESS_STRING = "Khách hàng ký hợp đồng thành công";

    //____________SM group____________
    public static final String SURVEY = "Khách hàng khảo sát";
    public static final String COP = "Khách hàng dự COP";
    public static final String COP_APPOINTMENT_STRING = "Khách hàng chuyển sang phỏng vấn";
    public static final String COP_DONE_STRING = "Khách hàng hoàn thành COP";
    public static final String MIT_STRING = "Học MIT";
    public static final String MIT_RELEARN_STRING = "Học lại MIT";
    public static final String MIT_DONE_STRING = "Đã học MIT";



    //menu call type
    public static final String APPOINTMENT_MENU = "appointment_menu";
    public static final String CONSULTANT_MENU = "consultant_menu";
    public static final String CONTACT_MENU = "contact_menu";
    public static final String SIGNED_MENU = "signed_menu";
    public static final String INTRODUCE_MENU = "introduce_menu";
    //menu recruit
    public static final String SURVEY_MENU = "survey_menu";
    public static final String COP_MENU = "cop_menu";
    public static final String MIT_MENU = "cop_menu";

    public static final int ADD_CONTACT = 100;
    public static final int CHANGE_TO_CONTACT = 101;
    public static final int ADD_INTRODUCE_FROM_CONTACT = 102;
    public static final int EVENT_DETAIL = 103;
    public static final int CONTACT_DETAIL = 104;
    public static final int ADD_EVENT = 105;
    public static final int SIGN_SUCCESS = 106;
    public static final int UPDATE_CONTACT = 107;
    public static final int UPDATE_EVENT = 108;

    //variable for contact
    public static final int USER_CONTACT = 1;
    public static final int USER_REFUSE = 2;
    public static final int USER_CALLLATER = 3;

    //variable for appointment
    public static final int APPOINTMENT_NEED = 1;
    public static final int APPOINTMENT_REFUSE = 2;
    public static final int APPOINTMENT_CALLLATER = 3;
    public static final int APPOINTMENT_SEEN = 4;

    //variable for consultant
    public static final int CONSULTANT_NEED = 1;
    public static final int CONSULTANT_REFUSE = 2;
    public static final int CONSULTANT_CALLLATER = 3;
    public static final int CONSULTANT_SEEN = 4;

    //variable for signed
    public static final int SIGNED_NOT_APPLIED = 1;
    public static final int SIGNED_BHXH = 2;
    public static final int SIGNED_APPLIED = 3;
    public static final int SIGNED_WAIT_APPROVE = 4;
    public static final int SIGNED_SUCCESS = 5;

    //_____________________SM group_____________________
    //variable for contact
    public static final int SURVEY_ADDED = 1;
    public static final int SURVEY_REFUSE = 2;
    public static final int SURVEY_CALLLATER = 3;

    //variable for cop
    public static final int COP_ADDED = 1;
    public static final int COP_REFUSE = 2;
    public static final int COP_CALLATER = 3;
    public static final int COP_DONE = 4;

    //variable for mit
    public static final int MIT_ADDED = 1;
    public static final int MIT_REFUSE = 2;
    public static final int MIT_RELEARN = 3;
    public static final int MIT_DONE = 4;

}
