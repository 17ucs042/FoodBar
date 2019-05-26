package com.appsaga.foodbar;

public class Categories {

    Integer image;
    String category;

    public Categories(Integer image, String category) {
        this.image = image;
        this.category = category;
    }

    public Integer getImage() {
        return image;
    }

    public void setImage(Integer image) {
        this.image = image;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
