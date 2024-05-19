package com.example.homeworkers2.backend;

import android.content.Context;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Urls {
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

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



    public Future<Response> sendRequest(String url, String body, String method){



        RequestBody bodyJSON = RequestBody.create(body, JSON);

        String currentUrl = generalUrl + url;

        System.out.println(currentUrl);

        Callable<Response> callable = () -> {

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


            try {
                Response response = client.newCall(request).execute();

                return response;
            } catch (IOException e) {
                e.printStackTrace();
                throw e;
            }
        };

        return executorService.submit(callable);
    }

    public void shutdown() {
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(800, TimeUnit.MILLISECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
        }
    }

}
