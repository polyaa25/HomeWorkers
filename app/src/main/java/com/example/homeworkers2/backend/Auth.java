package com.example.homeworkers2.backend;

import android.content.Context;
import android.content.SharedPreferences;

public class Auth {

    private static final String SHARED_PREFS_NAME = "prefs";
    private static final String TOKEN_KEY = "token_key";
    private static final String USER_ID = "user_id";

    private static SharedPreferences sharedPreferences;

    public static void create(Context context){
        sharedPreferences = context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
    }

    public static void setAuthUserId(String userId){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_ID, userId);
        editor.apply();
    }

    public static void setAuthToken(String token){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TOKEN_KEY, token);
        editor.apply();
    }

    public static String getAuthUserId(){
        return sharedPreferences.getString(USER_ID, null);
    }

    public static String getAuthToken(){
        return sharedPreferences.getString(TOKEN_KEY, null);
    }

    public static void clearAuth(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(TOKEN_KEY);
        editor.remove(USER_ID);
        editor.apply();
    }

}
