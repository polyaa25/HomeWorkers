package com.example.homeworkers2.accaunt;

import com.example.homeworkers2.backend.Urls;
import com.example.homeworkers2.backend.UrlsRequestMethod;
import com.example.homeworkers2.backend.UrlsType;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import okhttp3.Response;

public class AccauntData implements Serializable {
    private String id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String avatarUrl;
    private String idOfferedServices;
    private String arithmeticMeanScore;
    private ServicesData services;

    public ServicesData getServices() {
        return services;
    }

    public void setServices(ServicesData services) {
        this.services = services;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAvatarUrl() {
        return UrlsType.GENERAL_URL + avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getIdOfferedServices() {
        return idOfferedServices;
    }

    public void setIdOfferedServices(String idOfferedServices) {
        this.idOfferedServices = idOfferedServices;
    }

    public String getArithmeticMeanScore() {
        return arithmeticMeanScore;
    }

    public void setArithmeticMeanScore(String arithmeticMeanScore) {
        this.arithmeticMeanScore = arithmeticMeanScore;
    }

}
