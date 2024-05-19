package com.example.homeworkers2.order;

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

import okhttp3.Response;

public class OrderHandle {

    public static AccauntData createorder(
            Urls urls,
            String phoneNumber,
            String orderAddress,
            String textOrder,
            String idOfferedService
    ){

        JSONObject body = new JSONObject();

        AccauntData employee;

        try {
            body.put("phone_for_communication", phoneNumber);
            body.put("order_address", orderAddress);
            body.put("text_order", textOrder);
            body.put("id_customer_user", Auth.getAuthUserId());
            body.put("id_offered_services", idOfferedService);

            Future<Response> responseFuture = urls.sendRequest(
                    UrlsType.POST_CREATE_ORDER,
                    body.toString(),
                    UrlsRequestMethod.POST
            );

            Response response = responseFuture.get();

            JSONObject responseBody = new JSONObject(response.body().string());

            employee = AccauntHandle.getAccaunt(responseBody.getString("employee_id"), urls);

        } catch (JSONException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return employee;

    }

    public static ArrayList<OrderData> getOrdersByUserId(Urls urls, String userId){

        ArrayList<OrderData> orders = new ArrayList<OrderData>();

        JSONObject body = new JSONObject();

        try {
            body.put("customer_user_id", userId);

            Future<Response> responseFuture = urls.sendRequest(
                    UrlsType.GET_ORDER,
                    body.toString(),
                    UrlsRequestMethod.POST
            );

            Response response = responseFuture.get();

            JSONArray array = new JSONArray(response.body().string());

            System.out.println(array.length());

            for(int i = 0; i < array.length(); i++){
                JSONObject object = array.getJSONObject(i);

                OrderData data = new OrderData();

                data.setId(object.getString("id"));
                data.setPhoneNumber(object.getString("phone_for_communication"));
                data.setIsActive(object.getBoolean("is_active"));
                data.setOrderAddress(object.getString("order_address"));
                data.setTextOrder(object.getString("text_order"));
                data.setIdCustomerUser(object.getString("id_customer_user"));
                data.setIdEmployeeUser(object.getString("id_employee_user"));
                data.setIdOfferedSevices(object.getString("id_offered_services"));

                AccauntData customerUserData = AccauntHandle.getAccaunt(data.getIdCustomerUser(), urls);
                AccauntData employeeUserData = AccauntHandle.getAccaunt(data.getIdEmployeeUser(), urls);

                ServicesData employeeServicesData = ServicesHandle.getOfferedService(
                        urls,
                        employeeUserData.getIdOfferedServices()
                );

                employeeUserData.setServices(employeeServicesData);

                data.setCustomerUser(customerUserData);
                data.setEmployeeUser(employeeUserData);

                orders.add(data);

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

        return orders;

    }

}
