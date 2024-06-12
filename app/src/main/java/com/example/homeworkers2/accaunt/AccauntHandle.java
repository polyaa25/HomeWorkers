package com.example.homeworkers2.accaunt;

import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.example.homeworkers2.backend.Auth;
import com.example.homeworkers2.backend.Urls;
import com.example.homeworkers2.backend.UrlsRequestMethod;
import com.example.homeworkers2.backend.UrlsType;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class AccauntHandle {

    public interface AccauntCallback{
        void onSuccess(AccauntData data);
        void onFailure(Exception e);
    }

    public interface UserCallback{
        void onSuccess(Response response);
        void onFailure(Exception e);
    }

    public interface PhoneCallback{
        void onSuccess(boolean isExists, boolean isFirst);
        void onFailure(Exception e);
    }

    public interface PasswordCallback{
        void onSuccess(boolean isIdenticalPasswords);
        void onFailure(Exception e);
    }

    public static void setAvatar(AccauntData data, ImageView view){
        String avatarUrl = data.getAvatarUrl();

        if (avatarUrl != null){
            Picasso.get()
                    .load(avatarUrl)
                    .into(view);
        }
    }

    public static void checkPhoneNumber(Urls urls, String phone, PhoneCallback callback){

        JSONObject body = new JSONObject();

        try {
            body.put("telephone", phone);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        Callback responseCallback = new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                callback.onFailure(e);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                try {

                    if(!response.isSuccessful()){ return; }

                    JSONObject object = new JSONObject(response.body().string());

                    boolean isExists = object.getBoolean("phone_exists");
                    boolean isFirst = object.getBoolean("phone_is_first");

                    callback.onSuccess(isExists, isFirst);
                } catch (JSONException e) {
                    callback.onFailure(e);
                    throw new RuntimeException(e);
                }
            }
        };

        urls.sendRequest(UrlsType.POST_CHECK_TELEPHONE, body.toString(), UrlsRequestMethod.POST, responseCallback);
    }

    public static void resetPassword(Urls urls, String phone, String password, String repeatPassword, PasswordCallback callback){

        JSONObject body = new JSONObject();

        try {
            body.put("telephone", phone);
            body.put("password", password);
            body.put("password_repeat", repeatPassword);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        Callback responseCallback = new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                callback.onFailure(e);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                try {
                    if(!response.isSuccessful()){ return; }

                    JSONObject object = new JSONObject(response.body().string());

                    boolean isIdenticalPasswords = object.getBoolean("identical_passwords");

                    callback.onSuccess(isIdenticalPasswords);
                } catch (JSONException e) {
                    callback.onFailure(e);
                    throw new RuntimeException(e);
                }
            }
        };

        urls.sendRequest(UrlsType.PATCH_RESET_PASSWORD, body.toString(), UrlsRequestMethod.PATCH, responseCallback);
    }

    public static float arithmeticMeanScore(String scoreBody){

        float result = 0.0f;

        try {
            JSONArray array = new JSONArray(scoreBody);

            int arthI = 0;

            for(int i = 0; i < array.length(); i++){
                arthI += array.getInt(i);
            }

            result = arthI / array.length();

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    public static void login(Urls urls, String telephone, String password, UserCallback callback){

        Callback responseCallback = new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                callback.onFailure(e);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                callback.onSuccess(response);
            }
        };

        JSONObject body = new JSONObject();

        try {
            body.put("telephone", telephone);
            body.put("password", password);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        urls.sendRequest(
                UrlsType.POST_LOGIN,
                body.toString(),
                UrlsRequestMethod.POST,
                responseCallback
        );
    }

    public static AccauntData accauntDataCreate(String body){
        AccauntData data = new AccauntData();

        try {

            JSONObject object = new JSONObject(body);

            String offeredServices = object.getString("id_offered_services");

            data.setId(object.getString("id"));
            data.setFirstName(object.getString("first_name"));
            data.setLastName(object.getString("last_name"));
            data.setPhoneNumber(object.getString("telephone"));
            data.setAvatarUrl(object.getString("avatar"));

            String scors = object.getString("scors");

            data.setArithmeticMeanScore(AccauntHandle.arithmeticMeanScore(scors));

            if(offeredServices != "null"){
                data.setServices(ServicesHandle.createServicesData(offeredServices));
            }

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        return data;
    }

    public static void accauntCreate(Urls urls, String phoneText, String firstNameText, String lastNameText, String passwordText,
                                     String password2Text, UserCallback callback){
        JSONObject body = new JSONObject();

        try {
            body.put("telephone", phoneText);
            body.put("first_name", firstNameText);
            body.put("last_name", lastNameText);
            body.put("password", passwordText);
            body.put("password2", password2Text);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        urls.sendRequest(UrlsType.POST_REGISTER_USER, body.toString(), UrlsRequestMethod.POST, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                callback.onFailure(e);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                callback.onSuccess(response);
            }
        });

    }


    public static void getAccaunt(String id, Urls urlsBackend, AccauntCallback callback){
        JSONObject bodyRequest = new JSONObject();

        try {
            bodyRequest.put("user_id", id);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        Callback resultCallback = new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                if(!response.isSuccessful()){
                    return;
                }

                try {

                    AccauntData data = accauntDataCreate(response.body().string());

                    callback.onSuccess(data);

                } catch (IOException ex) {
                    callback.onFailure(ex);
                    throw new RuntimeException(ex);
                }
            }
        };

        urlsBackend.sendRequest(UrlsType.GET_USER, bodyRequest.toString(), UrlsRequestMethod.POST, resultCallback);
    }

}
