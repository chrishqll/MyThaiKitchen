package com.example.mythaikitchen;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Timer;
import java.util.TimerTask;



public class orders extends AppCompatActivity {
    TextView totalCosttxt, addresstxt, orderIDtxt, paymentMethodtxt;
    String totalCostString, payment;
    boolean delivering;
    String city, postcode, street, houseNumber;
    int orderidInt;
    int count = 0;
    FirebaseDatabase rootNode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        ImageButton history = findViewById(R.id.history);
        ImageButton call = findViewById(R.id.call);
        ImageButton currentBasket = findViewById(R.id.currentbasket);
        ImageButton confirm = findViewById(R.id.confirm);
        ImageButton home = findViewById(R.id.home);
        ImageButton basket = findViewById(R.id.basket);

        totalCosttxt = findViewById(R.id.totalcost);
        addresstxt = findViewById(R.id.address);
        orderIDtxt = findViewById(R.id.orderid);
        paymentMethodtxt = findViewById(R.id.paymentMethod);
        addresstxt = findViewById(R.id.address);

        //display payment method
        retrievePayment();
        paymentMethodtxt.setText(payment);

        //display total cost
        retrieveTotalCost();
        String itemCostFormat = String.format("£" + totalCostString);
        totalCosttxt.setText(itemCostFormat);

        //displaying customer's address
        retrieveAddress();

        //displaying orderid
        retrieveOrderid();
        String orderidFormat = String.format("#" + orderidInt);
        orderIDtxt.setText(orderidFormat);

        checkDeliveryStatus();
        if (delivering == false){
            clearData();
        }

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(orders.this, "Call: 000 000 0000", Toast.LENGTH_SHORT).show();
            }
        });
        currentBasket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkDeliveryStatus();
                if (delivering == false){
                    Toast.makeText(orders.this, "There are no current orders", Toast.LENGTH_SHORT).show();
                }
                else{
                    openCurrentBasket();
                }
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                if (count == 2){
                    delivering = false;
                    updateDeliveryStatus();
                    count = 0;
                    clearData();
                    openHome();
                }
                else {
                    Toast.makeText(orders.this, "Press again to confirm", Toast.LENGTH_SHORT).show();
                }
            }
        });
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHistory();
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHome();
            }
        });
        basket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBasket();
            }
        });
    }
    private void checkDeliveryStatus(){
        SharedPreferences deliveryStatus = getSharedPreferences("deliveryStatus", Context.MODE_PRIVATE);
        delivering = deliveryStatus.getBoolean("DELIVERYSTATUS", false);
    }
    private void updateDeliveryStatus(){
        SharedPreferences deliveryStatus = getSharedPreferences("deliveryStatus", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = deliveryStatus.edit();
        editor.putBoolean("DELIVERYSTATUS", delivering);
        editor.apply();

        String deliveryStatusStr = "COMPLETED";

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
    private void retrieveAddress(){
        SharedPreferences address = getSharedPreferences("MyAddress", Context.MODE_PRIVATE);
        //getting sub-values in address
        city = address.getString("CITY", "");
        postcode = address.getString("POSTCODE", "");
        street = address.getString("STREET", "");
        houseNumber = address.getString("HOUSENUMBER", "");
        //formatting address into one large variable for output
        String addressFormat = String.format(city +", "+ postcode +", "+ street +", "+ houseNumber);
        addresstxt.setText(addressFormat);
    }
    private void retrievePayment(){
        SharedPreferences paymentMethod = getSharedPreferences("paymentmethod", Context.MODE_PRIVATE);
        payment = paymentMethod.getString("PAYMENT", "");
    }
    private void retrieveTotalCost(){
        SharedPreferences totalcost = getSharedPreferences("totalcost", Context.MODE_PRIVATE);
        totalCostString = totalcost.getString("TOTALCOST", "");
    }
    private void retrieveOrderid(){
        SharedPreferences orderid = getSharedPreferences("orderid", Context.MODE_PRIVATE);
        orderidInt = orderid.getInt("ORDERID", 0);
    }
    private void clearData(){
        totalCostString = "0.0";
        SharedPreferences totalcost = getSharedPreferences("totalcost", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = totalcost.edit();
        editor.putString("TOTALCOST", totalCostString);
        editor.apply();

        SharedPreferences paymentMethod = getSharedPreferences("paymentmethod", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor1 = paymentMethod.edit();
        editor1.putString("PAYMENT", "");
        editor1.apply();
    }
    private void openCurrentBasket(){
        Intent intent = new Intent(this, currentBasket.class);
        startActivity(intent);
        overridePendingTransition(0,0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
    }
    public void openHome(){
        Intent intent = new Intent(this, mainRelay.class);
        startActivity(intent);
        overridePendingTransition(0,0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
    }
    public void openBasket(){
        Intent intent = new Intent(this, basket.class);
        startActivity(intent);
        overridePendingTransition(0,0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
    }
    private void openHistory() {
        Intent intent = new Intent(this, history.class);
        startActivity(intent);
        overridePendingTransition(0,0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
    }

}