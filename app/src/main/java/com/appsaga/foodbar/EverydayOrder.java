package com.appsaga.foodbar;

import java.util.ArrayList;
import java.util.HashMap;

public class EverydayOrder {

    HashMap<String, String> customerDetails;
    ArrayList<HashMap<String, String>> itemsOrdered;
    String paymentType;
    String time;
    String everyday;
    String daysLeft;
    String status;
    String id;
    String branchPin;

    public EverydayOrder(HashMap<String, String> customerDetails, ArrayList<HashMap<String, String>> itemsOrdered, String paymentType,
                 String time,String everyday,String daysLeft,String status,String id,String branchPin) {
        this.customerDetails = customerDetails;
        this.itemsOrdered = itemsOrdered;
        this.paymentType = paymentType;
        this.time = time;
        this.everyday=everyday;
        this.daysLeft=daysLeft;
        this.status = status;
        this.id = id;
        this.branchPin = branchPin;
    }

    public EverydayOrder(Order order,String branchPin)
    {
        this.customerDetails=order.getCustomerDetails();
        this.itemsOrdered=order.getItemsOrdered();
        this.paymentType=order.getPaymentType();
        this.time=order.getTime();
        this.everyday=order.getEveryday();
        this.daysLeft=order.getDaysLeft();
        this.status=order.getStatus();
        this.id=order.getId();
        this.branchPin=branchPin;
    }

    public EverydayOrder()
    {

    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public HashMap<String, String> getCustomerDetails() {
        return customerDetails;
    }

    public void setCustomerDetails(HashMap<String, String> customerDetails) {
        this.customerDetails = customerDetails;
    }

    public ArrayList<HashMap<String, String>> getItemsOrdered() {
        return itemsOrdered;
    }

    public void setItemsOrdered(ArrayList<HashMap<String, String>> itemsOrdered) {
        this.itemsOrdered = itemsOrdered;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getEveryday() {
        return everyday;
    }

    public void setEveryday(String everyday) {
        this.everyday = everyday;
    }

    public String getDaysLeft() {
        return daysLeft;
    }

    public void setDaysLeft(String daysLeft) {
        this.daysLeft = daysLeft;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBranchPin() {
        return branchPin;
    }

    public void setBranchPin(String branchPin) {
        this.branchPin = branchPin;
    }
}
