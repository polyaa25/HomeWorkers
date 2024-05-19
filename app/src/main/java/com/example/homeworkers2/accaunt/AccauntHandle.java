package com.example.homeworkers2.accaunt;

import android.os.Handler;
import android.os.Looper;

import com.example.homeworkers2.backend.Urls;
import com.example.homeworkers2.backend.UrlsRequestMethod;
import com.example.homeworkers2.backend.UrlsType;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import okhttp3.Response;

public class AccauntHandle {

    public static void accauntCreate(Urls urls, String phoneText, String firstNameText, String lastNameText, String passwordText,
                                     String password2Text){
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

        urls.sendRequest(UrlsType.POST_REGISTER_USER, body.toString(), UrlsRequestMethod.POST);

    }

    public static AccauntData getAccaunt(String id, Urls urlsBackend){
        JSONObject bodyRequest = new JSONObject();

        try {
            bodyRequest.put("user_id", id);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        Future<Response> responseFuture = urlsBackend.sendRequest(
                UrlsType.GET_USER,
                bodyRequest.toString(),
                UrlsRequestMethod.POST
        );

        AccauntData data = new AccauntData();

        try {
            Response response = responseFuture.get();

            JSONObject responseJson = new JSONObject(response.body().string());

            String servicesId = responseJson.getString("id_offered_services");

            data.setId(responseJson.getString("id"));
            data.setFirstName(responseJson.getString("first_name"));
            data.setLastName(responseJson.getString("last_name"));
            data.setPhoneNumber(responseJson.getString("telephone"));
            data.setAvatarUrl(responseJson.getString("avatar"));
            data.setIdOfferedServices(servicesId);
            data.setArithmeticMeanScore(responseJson.getString("arithmetic_mean_score"));

        } catch (JSONException ex) {
            throw new RuntimeException(ex);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } catch (ExecutionException ex) {
            throw new RuntimeException(ex);
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }

}
