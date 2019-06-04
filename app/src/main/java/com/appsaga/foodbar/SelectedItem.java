package com.appsaga.foodbar;

import java.util.HashMap;

public class SelectedItem {

    String price;
    String quantity;
    String inStock;

    public SelectedItem(String price, String quantity, String inStock) {
        this.price = price;
        this.quantity = quantity;
        this.inStock = inStock;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getInStock() {
        return inStock;
    }

    public void setInStock(String inStock) {
        this.inStock = inStock;
    }
}
