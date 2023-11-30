package com.example.mythaikitchen;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import org.w3c.dom.Text;

public class confirmOrder extends AppCompatActivity {
    FirebaseDatabase rootNode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);

        ImageButton basket = findViewById(R.id.currentbasket);
        ImageButton next = findViewById(R.id.confirmBt);
        ImageButton back = findViewById(R.id.back);
        TextView text = findViewById(R.id.text);

        basket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCurrentBasket();
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDeliveryStatus2();
                openPayment();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDeliveryStatus();
                back();
            }
        });
    }

    private void openCurrentBasket() {
        Intent intent = new Intent(this, currentBasket.class);
        startActivity(intent);
        overridePendingTransition(0,0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
    }

    private void openPayment(){
        Intent intent = new Intent(this, payment.class);
        startActivity(intent);
        overridePendingTransition(0,0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
    }
    private void back(){
        Intent intent = new Intent(this, basket.class);
        startActivity(intent);
        overridePendingTransition(0,0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
    }
    private void updateDeliveryStatus(){
        Boolean delivering = false;
        String deliveryStatusStr = "CANCELLED";

        //shared preferences
        SharedPreferences deliveryStatus = getSharedPreferences("deliveryStatus", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = deliveryStatus.edit();
        editor.putBoolean("DELIVERYSTATUS", delivering);
        editor.apply();

        //firebase
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
        DatabaseReference paymentRef = orderidRef.child("deliveryStatusStr");

        paymentRef.setValue(deliveryStatusStr);
    }

    private void updateDeliveryStatus2(){
        Boolean delivering = true;
        String deliveryStatusStr = "CONFIRMED";

        //shared preferences
        SharedPreferences deliveryStatus = getSharedPreferences("deliveryStatus", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = deliveryStatus.edit();
        editor.putBoolean("DELIVERYSTATUS", delivering);
        editor.apply();

        //firebase
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
        DatabaseReference paymentRef = orderidRef.child("deliveryStatusStr");

        paymentRef.setValue(deliveryStatusStr);
    }
}