package com.example.homeworkers2.order;

import androidx.annotation.NonNull;

import com.example.homeworkers2.accaunt.AccauntData;
import com.example.homeworkers2.accaunt.AccauntHandle;
import com.example.homeworkers2.accaunt.ServicesData;
import com.example.homeworkers2.accaunt.ServicesHandle;
import com.example.homeworkers2.backend.Auth;
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

public class OrderHandle {

    public interface OrderListCallback{
        void onSuccess(ArrayList<OrderData> datas);
        void onFailure(Exception e);
    }

    public static void createOrder(
            Urls urls,
            String phoneNumber,
            String orderAddress,
            String textOrder,
            String idOfferedService,
            AccauntHandle.AccauntCallback callback
    ){

        JSONObject body = new JSONObject();

        try {

            body.put("phone_for_communication", phoneNumber);
            body.put("order_address", orderAddress);
            body.put("text_order", textOrder);
            body.put("id_customer_user", Auth.getAuthUserId());
            body.put("id_offered_services", idOfferedService);

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

                if(!response.isSuccessful()){
                    return;
                }

                try {
                    JSONObject object = new JSONObject(response.body().string());

                    AccauntData data = AccauntHandle.accauntDataCreate(object.getString("employee_id"));

                    callback.onSuccess(data);
                } catch (JSONException e) {
                    callback.onFailure(e);
                    throw new RuntimeException(e);
                }
            }
        };

        urls.sendRequest(UrlsType.POST_CREATE_ORDER, body.toString(), UrlsRequestMethod.POST, responseCallback);
    }

    public static void getOrdersByUserId(Urls urls, String userId, OrderListCallback callback){

        Callback responseCallback = new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                callback.onFailure(e);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                if(!response.isSuccessful()){
                    return;
                }

                ArrayList<OrderData> orders = new ArrayList<OrderData>();

                try {

                    JSONArray array = new JSONArray(response.body().string());

                    for(int i = 0; i < array.length(); i++){
                        JSONObject object = array.getJSONObject(i);

                        OrderData data = new OrderData();

                        AccauntData customerUserData = AccauntHandle.accauntDataCreate(object.getString("id_customer_user"));
                        AccauntData employeeUserData = AccauntHandle.accauntDataCreate(object.getString("id_employee_user"));
                        ServicesData servicesData = ServicesHandle.createServicesData(object.getString("id_offered_services"));

                        data.setId(object.getString("id"));
                        data.setPhoneNumber(object.getString("phone_for_communication"));
                        data.setIsActive(object.getBoolean("is_active"));
                        data.setOrderAddress(object.getString("order_address"));
                        data.setTextOrder(object.getString("text_order"));
                        data.setCustomerUser(customerUserData);
                        data.setEmployeeUser(employeeUserData);
                        data.setServices(servicesData);

                        orders.add(data);

                    }

                    callback.onSuccess(orders);

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
                UrlsType.getUrlToOrders(userId),
                "",
                UrlsRequestMethod.GET,
                responseCallback
        );
    }

}
