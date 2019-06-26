package com.appsaga.foodbar;

import java.io.Serializable;

public class ItemsOrdered implements Serializable {

    String Name;
    String Number_of_items;
    String Price;
    String Quantity;

    public ItemsOrdered(String name, String number_of_items, String price, String quantity) {
        Name = name;
        Number_of_items = number_of_items;
        Price = price;
        Quantity = quantity;
    }

    public ItemsOrdered()
    {

    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getNumber_of_items() {
        return Number_of_items;
    }

    public void setNumber_of_items(String number_of_items) {
        Number_of_items = number_of_items;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }
}

