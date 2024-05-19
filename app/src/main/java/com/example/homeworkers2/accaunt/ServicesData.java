package com.example.homeworkers2.accaunt;

import com.example.homeworkers2.category.CategoryData;

import java.io.Serializable;

public class ServicesData implements Serializable {

    private String id;
    private String name;
    private String categoryId;

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
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
