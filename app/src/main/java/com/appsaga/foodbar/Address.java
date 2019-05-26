package com.appsaga.foodbar;

import java.io.Serializable;

public class Address implements Serializable {

    String name;
    String phoneNum;
    String houseNo;
    String area;
    String pincode;
    String nickname;

    public Address(String name, String phoneNum, String houseNo, String area, String pincode, String nickname) {
        this.name = name;
        this.phoneNum = phoneNum;
        this.houseNo = houseNo;
        this.area = area;
        this.pincode = pincode;
        this.nickname = nickname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getHouseNo() {
        return houseNo;
    }

    public void setHouseNo(String houseNo) {
        this.houseNo = houseNo;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
