package com.example.homeworkers2.category;

import com.example.homeworkers2.accaunt.ServicesData;

import java.io.Serializable;
import java.util.ArrayList;

public class CategoryData implements Serializable {

    private String id;
    private String name;
    private String iconUrl;
    private ArrayList<ServicesData> services;


    public ArrayList<ServicesData> getServices() {
        return services;
    }

    public void setServices(ArrayList<ServicesData> services) {
        this.services = services;
    }
    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
