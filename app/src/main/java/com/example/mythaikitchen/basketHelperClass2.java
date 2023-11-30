package com.example.mythaikitchen;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class basketHelperClass2 {
    String paymentMethod;

    public basketHelperClass2(String paymentMethod) {
        this.paymentMethod = paymentMethod;

    }
    public String getPaymentMethod() {
        return paymentMethod;
    }
}