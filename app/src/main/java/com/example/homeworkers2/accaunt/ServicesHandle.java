package com.example.homeworkers2.accaunt;

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

import okhttp3.Response;

public class ServicesHandle {

    public static ArrayList<ServicesData> getServicesByCategoryId(Urls urls, String id){

        ArrayList<ServicesData> services = new ArrayList<>();

        try {

            Future<Response> responseFuture = urls.sendRequest(
                    UrlsType.getUrlToServices(id),
                    "",
                    UrlsRequestMethod.GET
            );

            Response response = responseFuture.get();

            JSONArray array = new JSONArray(response.body().string());

            for(int i = 0; i < array.length(); i++){
                JSONObject object = array.getJSONObject(i);

                ServicesData data = new ServicesData();

                data.setId(object.getString("id"));
                data.setName(object.getString("name_service"));
                data.setCategoryId(id);

                services.add(data);
            }


        } catch (JSONException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return services;
    }

    public static ServicesData getOfferedService(Urls urls, String id){
        ServicesData data = new ServicesData();

        try {

            Future<Response> responseFuture = urls.sendRequest(
                    UrlsType.GET_OFFERED_SERVICE + id,
                    "",
                    UrlsRequestMethod.GET
            );



            Response response = responseFuture.get();

            JSONObject responseJson = new JSONObject(response.body().string());

            data.setId(responseJson.getString("id"));
            data.setName(responseJson.getString("name_service"));


        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        return  data;
    }

}
