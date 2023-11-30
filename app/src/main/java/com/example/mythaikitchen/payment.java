package com.example.mythaikitchen;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class payment extends AppCompatActivity {
    FirebaseDatabase rootNode;
    String payment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        ImageButton card = findViewById(R.id.card);
        ImageButton cash = findViewById(R.id.cash);

        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payment = "CARD";
                Toast.makeText(payment.this, "Payment will be handled via Deliveroo", Toast.LENGTH_SHORT).show();
                uploadPaymentMethod();
                paymentMethod();
                openOrders();
            }
        });
        cash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payment = "CASH";
                uploadPaymentMethod();
                paymentMethod();
                openOrders();
            }
        });
    }
    private void uploadPaymentMethod(){
        SharedPreferences address = getSharedPreferences("MyUser", Context.MODE_PRIVATE);
        String phoneNumber = address.getString("PHONENUMBER", "");
        SharedPreferences orderid = getSharedPreferences("orderid", Context.MODE_PRIVATE);
        int orderidInt = orderid.getInt("ORDERID", 0);
        String orderidStr = String.valueOf(orderidInt);

        rootNode = FirebaseDatabase.getInstance();
        DatabaseReference usersRef = rootNode.getReference("users");
        DatabaseReference infoRef = usersRef.child(phoneNumber);
        DatabaseReference orderRef = infoRef.child("orders");
        DatabaseReference orderidRef = orderRef.child(orderidStr);
        DatabaseReference paymentRef = orderidRef.child("payment");

        paymentRef.setValue(payment);
    }

    private void openOrders(){
        Intent intent = new Intent(this, orders.class);
        startActivity(intent);
        overridePendingTransition(0,0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
    }
    private void paymentMethod(){
        SharedPreferences paymentMethod = getSharedPreferences("paymentmethod", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = paymentMethod.edit();
        editor.putString("PAYMENT", payment);
        editor.apply();
    }
}