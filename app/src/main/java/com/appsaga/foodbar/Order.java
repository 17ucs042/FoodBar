package com.appsaga.foodbar;

import java.util.ArrayList;
import java.util.HashMap;

public class Order {

    HashMap<String, String> customerDetails;
    ArrayList<HashMap<String, String>> itemsOrdered;
    String paymentType;
    String time;
    String everyday;

    public Order(HashMap<String, String> customerDetails, ArrayList<HashMap<String, String>> itemsOrdered, String paymentType,
                 String time,String everyday) {
        this.customerDetails = customerDetails;
        this.itemsOrdered = itemsOrdered;
        this.paymentType = paymentType;
        this.time = time;
        this.everyday=everyday;
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
}
