package com.example.homeworkers2.category;

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

import okhttp3.Response;

public class CategoryHolder {

    public static ArrayList<CategoryData> getCategories(Urls urls){

        ArrayList<CategoryData> categories = new ArrayList<CategoryData>();

        try {

            Future<Response> responseFuture = urls.sendRequest(
                    UrlsType.GET_CATEGORIES,
                    "",
                    UrlsRequestMethod.GET
            );

            Response response = responseFuture.get();

            JSONArray array = new JSONArray(response.body().string());

            for(int i = 0; i < array.length(); i++){
                JSONObject object = array.getJSONObject(i);

                CategoryData data = new CategoryData();

                data.setId(object.getString("id"));
                data.setIconUrl(object.getString("image_category"));
                data.setName(object.getString("name"));

                categories.add(data);
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

        return categories;
    }

}
