package com.appsaga.foodbar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Item implements Serializable {

    public String name;
    public String url;
    public HashMap<String,String> quant_price;
    public String type;

    public Item() {

    }

    public Item(String name, String url, HashMap<String, String> quant_price, String type) {
        this.name = name;
        this.url = url;
        this.quant_price = quant_price;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public HashMap<String, String> getQuant_price() {
        return quant_price;
    }

    public void setQuant_price(HashMap<String, String> quant_price) {
        this.quant_price = quant_price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


}
