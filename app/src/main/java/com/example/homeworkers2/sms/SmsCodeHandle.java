package com.example.homeworkers2.sms;

import androidx.annotation.NonNull;

import com.example.homeworkers2.backend.Urls;
import com.example.homeworkers2.backend.UrlsRequestMethod;
import com.example.homeworkers2.backend.UrlsType;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class SmsCodeHandle {

    public static final String SMS_CODE_RESULT_EXTRA = "sms_code_result_extra";
    public static final String TELEPHONE_TO_SMS_CODE_EXTRA = "telephone_to_sms_code_extra";

    public interface SmsCallback{
        void onSuccess(boolean isCode);
        void onFailure(Exception e);
    }


    public static void sendSms(Urls urls, String telephone){

        JSONObject object = new JSONObject();
        try {
            object.put("telephone", telephone);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        Callback resultCallback = new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

            }
        };

        urls.sendRequest(UrlsType.POST_SMS_SEND, object.toString(), UrlsRequestMethod.POST, resultCallback);
    }

    public static void checkSmsCode(Urls urls, String telephone, int code, SmsCallback callback){

        JSONObject object = new JSONObject();
        try {
            object.put("telephone", telephone);
            object.put("code", code);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        Callback resultCallback = new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                callback.onFailure(e);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.code() == 200){
                    try {
                        JSONObject responseObject = new JSONObject(response.body().string());

                        boolean isCode = responseObject.getBoolean("code");

                        callback.onSuccess(isCode);

                    } catch (JSONException e) {
                        callback.onFailure(e);
                        throw new RuntimeException(e);
                    }

                }
            }
        };

        urls.sendRequest(UrlsType.POST_SMS_CHECK, object.toString(), UrlsRequestMethod.POST, resultCallback);
    }

}
