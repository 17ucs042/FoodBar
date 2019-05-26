package com.appsaga.foodbar;

public class BasketItems {

    String name;
    String quantity;
    String price;
    int item_num;

    public BasketItems(String name, String quantity, String price, int item_num) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.item_num = item_num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getItem_num() {
        return item_num;
    }

    public void setItem_num(int item_num) {
        this.item_num = item_num;
    }
}
