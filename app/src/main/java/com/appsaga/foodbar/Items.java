package com.appsaga.foodbar;

class Items {

    String name;
    String price;
    Boolean inStock;
    int quantity;

    public Items(String name, String price, Boolean inStock, int quantity) {
        this.name = name;
        this.price = price;
        this.inStock = inStock;
        this.quantity=quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Boolean getInStock() {
        return inStock;
    }

    public void setInStock(Boolean inStock) {
        this.inStock = inStock;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
