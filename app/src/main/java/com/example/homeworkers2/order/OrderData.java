package com.example.homeworkers2.order;

import com.example.homeworkers2.accaunt.AccauntData;
import com.example.homeworkers2.accaunt.ServicesData;

public class OrderData {
    private String id;
    private String phoneNumber;
    private String orderAddress;
    private String textOrder;
    private Boolean isActive;

    private AccauntData customerUser;
    private AccauntData employeeUser;
    private ServicesData services;
    public ServicesData getServices() {
        return services;
    }

    public void setServices(ServicesData services) {
        this.services = services;
    }


    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean is_active) {
        this.isActive = is_active;
    }

    public AccauntData getCustomerUser() {
        return customerUser;
    }

    public void setCustomerUser(AccauntData customerUser) {
        this.customerUser = customerUser;
    }

    public AccauntData getEmployeeUser() {
        return employeeUser;
    }

    public void setEmployeeUser(AccauntData employeeUser) {
        this.employeeUser = employeeUser;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getOrderAddress() {
        return orderAddress;
    }

    public void setOrderAddress(String orderAddress) {
        this.orderAddress = orderAddress;
    }

    public String getTextOrder() {
        return textOrder;
    }

    public void setTextOrder(String textOrder) {
        this.textOrder = textOrder;
    }


}
