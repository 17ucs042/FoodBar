package com.appsaga.foodbar;

import java.util.ArrayList;
import java.util.HashMap;

public class Order {

    HashMap<String, String> customerDetails;
    ArrayList<HashMap<String, String>> itemsOrdered;
    String paymentType;
    String time;
    String everyday;
    String daysLeft;
    String status;
    String id;

    public Order(HashMap<String, String> customerDetails, ArrayList<HashMap<String, String>> itemsOrdered, String paymentType,
                 String time,String everyday,String daysLeft,String status,String id) {
        this.customerDetails = customerDetails;
        this.itemsOrdered = itemsOrdered;
        this.paymentType = paymentType;
        this.time = time;
        this.everyday=everyday;
        this.daysLeft=daysLeft;
        this.status = status;
        this.id = id;
    }

    public Order()
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
}
