package com.example.homeworkers2.backend;

import android.content.Context;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Urls {
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    private final OkHttpClient client;
    private String generalUrl = UrlsType.GENERAL_MOBILE;

    public Urls(Context context){
        client = createClient(context);
    }
    public Urls(Context context, String url){
        this.generalUrl = url;
        client = createClient(context);
    }

    private OkHttpClient createClient(Context context){
        OkHttpClient cl = new OkHttpClient.Builder()
                .addInterceptor(new AuthInterceptor(context))
                .build();
        return cl;
    }

    public String getGeneralUrl(){
        return generalUrl;
    }

    public Request createRequest(String url, String body, String method){
        RequestBody bodyJSON = RequestBody.create(body, JSON);

        String currentUrl = generalUrl + url;

        System.out.println(currentUrl);


        Request.Builder requestBuilder = new Request.Builder()
                .url(currentUrl);

        if (method.equals(UrlsRequestMethod.GET)){
            requestBuilder.get();
        }
        else{
            requestBuilder.method(method, bodyJSON);
        }

        if (Auth.getAuthToken() != null){
            requestBuilder.addHeader("Authorization", "Token " + Auth.getAuthToken());
        }

        Request request = requestBuilder.build();

        return request;
    }

    public void sendRequest(String url, String body, String method, Callback callback){

        Request request = createRequest(url, body, method);

        client.newCall(request).enqueue(callback);


    }

    public void sendRequestNonCallback(String url, String body, String method){

        Request request = createRequest(url, body, method);

        executorService.execute(() -> {
            try {
                client.newCall(request).execute();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });


    }

}
