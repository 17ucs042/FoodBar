package com.appsaga.foodbar;

import java.util.ArrayList;
import java.util.HashMap;

public class Order {

    HashMap<String, String> customerDetails;
    ArrayList<HashMap<String, String>> itemsOrdered;

    public Order(HashMap<String, String> customerDetails, ArrayList<HashMap<String, String>> itemsOrdered) {
        this.customerDetails = customerDetails;
        this.itemsOrdered = itemsOrdered;
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
}
