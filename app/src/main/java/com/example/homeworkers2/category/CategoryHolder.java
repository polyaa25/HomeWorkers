package com.example.homeworkers2.category;

import androidx.annotation.NonNull;

import com.example.homeworkers2.accaunt.ServicesData;
import com.example.homeworkers2.backend.Urls;
import com.example.homeworkers2.backend.UrlsRequestMethod;
import com.example.homeworkers2.backend.UrlsType;

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

public class CategoryHolder {

    public interface CategoryListCallback{
        void onSuccess(ArrayList<CategoryData> datas);
        void onFailure(Exception e);
    }

    public static void getCategories(Urls urls, CategoryListCallback callback){

        Callback responseCallback = new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                callback.onFailure(e);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                try {
                    ArrayList<CategoryData> categories = new ArrayList<CategoryData>();

                    JSONArray array = new JSONArray(response.body().string());

                    for(int i = 0; i < array.length(); i++){
                        JSONObject object = array.getJSONObject(i);

                        CategoryData data = new CategoryData();

                        data.setId(object.getString("id"));
                        data.setIconUrl(object.getString("image_category"));
                        data.setName(object.getString("name"));

                        ArrayList<ServicesData> services = new ArrayList<>();
                        JSONArray servicesArray = object.getJSONArray("offered_services");
                        for (int j = 0; j < servicesArray.length(); j++) {
                            JSONObject serviceObject = servicesArray.getJSONObject(j);

                            ServicesData serviceData = new ServicesData();
                            serviceData.setId(serviceObject.getString("id"));
                            serviceData.setName(serviceObject.getString("name_service"));
                            serviceData.setCategoryId(serviceObject.getString("category"));

                            services.add(serviceData);
                        }
                        data.setServices(services);

                        categories.add(data);
                    }

                    callback.onSuccess(categories);

                } catch (JSONException e) {
                    callback.onFailure(e);
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    callback.onFailure(e);
                    throw new RuntimeException(e);
                }
            }
        };

        urls.sendRequest(
                UrlsType.GET_CATEGORIES,
                "",
                UrlsRequestMethod.GET,
                responseCallback
        );
    }

}
