package com.example.homeworkers2.accaunt;

import androidx.annotation.NonNull;

import com.example.homeworkers2.backend.Urls;
import com.example.homeworkers2.backend.UrlsRequestMethod;
import com.example.homeworkers2.backend.UrlsType;
import com.example.homeworkers2.category.CategoryData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ServicesHandle {

    interface ServicesArrayCallback{
        void onSuccess(ArrayList<ServicesData> datas);
        void onFailure(Exception e);
    }

    interface  ServiceCallback{
        void onSuccess(ServicesData data);
        void onFailure(Exception e);
    }

    public static ServicesData createServicesData(String body){

        ServicesData data = new ServicesData();

        try {
            JSONObject object = new JSONObject(body);

            if (object.getString("category") == "null")
                return null;

            data.setId(object.getString("id"));
            data.setName(object.getString("name_service"));
            data.setCategoryId(object.getString("category"));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        return data;
    }

    public static void getOfferedService(Urls urls, String id, ServiceCallback callback){

        Callback responseCallback = new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                callback.onFailure(e);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                ServicesData data = new ServicesData();

                try {
                    JSONObject responseJson = new JSONObject(response.body().string());

                    System.out.println(responseJson.getString("category"));


                    data.setId(responseJson.getString("id"));
                    data.setName(responseJson.getString("name_service"));

                    callback.onSuccess(data);
                } catch (IOException e) {
                    callback.onFailure(e);
                    throw new RuntimeException(e);
                } catch (JSONException e) {
                    callback.onFailure(e);
                    throw new RuntimeException(e);
                }
            }
        };

        urls.sendRequest(
                UrlsType.GET_OFFERED_SERVICE + id,
                "",
                UrlsRequestMethod.GET,
                responseCallback
        );
    }

}
