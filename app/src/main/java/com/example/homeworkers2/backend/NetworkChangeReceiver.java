package com.example.homeworkers2.backend;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class NetworkChangeReceiver extends BroadcastReceiver {

    public static final String MESSAGE_ERROR_INTERNET = "Подключение к интренету отсутствует";

    private MutableLiveData<Boolean> isNetworkAvailable = new MutableLiveData<>();

    private Urls urls;

    boolean isConnected;
    boolean isConnectedServer = false;

    public NetworkChangeReceiver(Urls urls){
        super();

        this.urls = urls;

        isConnectedServer();
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        isNetworkAvailable.postValue(isConnected);

        isConnectedAndMessage(context);
    }

    private void sendErrorMessage(Context context){
        Toast.makeText(context, MESSAGE_ERROR_INTERNET, Toast.LENGTH_SHORT).show();
    }

    private void isConnectedServer(){
        urls.sendRequest("", "", UrlsRequestMethod.GET, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                    isConnectedServer = true;
                }else{
                    isConnectedServer = false;
                }

            }
        });
    }

    public boolean isConnected(){
        return isConnected;
    }

    public boolean isConnectedAndMessage(Context context){

        if(!isConnected()){
            sendErrorMessage(context);
        }

        return isConnected;
    }

    public void observe(@NonNull androidx.lifecycle.LifecycleOwner owner, @NonNull androidx.lifecycle.Observer<Boolean> observer){

        isNetworkAvailable.observe(owner, observer);

    }

    public MutableLiveData<Boolean> getNetworkStatus() {
        return isNetworkAvailable;
    }
}