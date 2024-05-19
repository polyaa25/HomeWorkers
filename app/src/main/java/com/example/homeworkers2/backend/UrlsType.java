package com.example.homeworkers2.backend;

public class UrlsType {

    public final static String GENERAL_URL = "http://172.16.0.103:8000/";
    public final static String MOBILE_V1 = "mobile_client/v1/";
    public final static String USERS = "users/";

    public final static String GENERAL_MOBILE = GENERAL_URL + MOBILE_V1;

    //POST
    public final static String POST_REGISTER_USER = "register_user/";
    public final static String POST_CREATE_ORDER = "create_order/";
    public final static String POST_SMS_CODE_CREATE = "sms_code_create/";
    public final static String POST_LOGIN = "login/";
    public final static String POST_LOGOUT = "auth/token/logout/";

    //GET
    public final static String GET_ORDER = "get_order_by_customer_user/";
    public final static String GET_USER = "get_user/";
    public final static String GET_OFFERED_SERVICE = "get_offered_service/";
    public final static String GET_CATEGORIES = "categories/";

    //PATCH
    public final static String PATCH_ADD_SCROE = "add_score/";

    public static String getUrlToServices(String categoryID){
        return "services/" + categoryID;
    }

    public static String getUrlToUserResetParams(String userID){
        return USERS + "reset_params/" + userID;
    }

    public static String getUrlToUserResetPassword(String userID){
        return USERS + "reset_password/" + userID;
    }


}
