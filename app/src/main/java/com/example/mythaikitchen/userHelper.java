package com.example.mythaikitchen;

public class userHelper {
    String fullName, phoneNumber, address;
    public userHelper(){

    }
    public userHelper(String fullName, String phoneNumber, String address){
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }
}
