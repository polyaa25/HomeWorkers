package com.example.homeworkers2.backend;

public class UrlsType {

    public final static String GENERAL_URL = "http://172.16.0.102:8000/";
    public final static String MOBILE_V1 = "mobile_client/v1/";
    public final static String USERS = "users/";

    public final static String GENERAL_MOBILE = GENERAL_URL + MOBILE_V1;

    //POST
    public final static String POST_REGISTER_USER = "register_user/";
    public final static String POST_CREATE_ORDER = "create_order/";
    public final static String POST_LOGIN = "login/";
    public final static String POST_SMS_SEND = "sms_code_create/";
    public final static String POST_SMS_CHECK = "sms_code_check/";
    public final static String POST_LOGOUT = "auth/token/logout/";
    public final static String POST_CHECK_TELEPHONE = "check_telephone/";
    public final static String POST_REGISTER_DEVICE = "register_device/";

    //GET
    public final static String GET_ORDER = "get_order_by_customer_user/";
    public final static String GET_USER = "get_user/";
    public final static String GET_OFFERED_SERVICE = "get_offered_service/";
    public final static String GET_CATEGORIES = "categories/";

    //PATCH
    public final static String PATCH_ADD_SCROE = "add_score/";
    public final static String PATCH_RESET_PASSWORD = "users/reset_password/";

    public static String getUrlToOrders(String userID){
        return "get_order_by_customer_user/" + userID;
    }
    public static String getUrlToServices(String categoryID){
        return "services/" + categoryID;
    }

    public static String getUrlToUserResetParams(String userID){
        return USERS + "reset_params/" + userID;
    }


}
