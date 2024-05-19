package com.example.homeworkers2.order;

import com.example.homeworkers2.accaunt.AccauntData;

public class OrderData {
    private String id;
    private String phoneNumber;
    private String orderAddress;
    private String textOrder;
    private String idCustomerUser;
    private String idEmployeeUser;
    private String idOfferedSevices;
    private Boolean isActive;

    private AccauntData customerUser;
    private AccauntData employeeUser;

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

    public String getIdCustomerUser() {
        return idCustomerUser;
    }

    public void setIdCustomerUser(String idCustomerUser) {
        this.idCustomerUser = idCustomerUser;
    }

    public String getIdEmployeeUser() {
        return idEmployeeUser;
    }

    public void setIdEmployeeUser(String idEmployeeUser) {
        this.idEmployeeUser = idEmployeeUser;
    }

    public String getIdOfferedSevices() {
        return idOfferedSevices;
    }

    public void setIdOfferedSevices(String idOfferedSevices) {
        this.idOfferedSevices = idOfferedSevices;
    }


}
