package com.example.mythaikitchen;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.firebase.database.*;
import org.jetbrains.annotations.NotNull;

public class padthai extends AppCompatActivity {
    TextView stocktxt, costtxt, quantitytxt;
    String meatChoice = "default", quantityString, costString, stockSnapshot;
    int quantity, stock, stockSession;
    double cost;
    boolean shrimpcheck = false;
    int count = 0;
    FirebaseDatabase rootNode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_padthai);

        stocktxt = findViewById(R.id.stock);
        costtxt = findViewById(R.id.itemtotal);
        quantitytxt = findViewById(R.id.quantity);

        ImageButton back = findViewById(R.id.back);
        ImageButton shrimp = findViewById(R.id.shrimp);
        ImageButton increase = findViewById(R.id.plus);
        ImageButton decrease = findViewById(R.id.minus);
        ImageButton next = findViewById(R.id.next);
        ImageButton basket = findViewById(R.id.basket);
        ImageButton orders = findViewById(R.id.orders);

        retrieveStock();
        quantitytxt.setText("0");

        shrimp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (shrimpcheck == false){
                    shrimpcheck = true;
                    shrimp.setBackgroundResource(R.drawable.shrimp);
                    meatChoice = "shrimp";
                }
                else {
                    shrimpcheck = false;
                    shrimp.setBackgroundResource(R.drawable.shrimpfade);
                    meatChoice = "default";
                }
            }
        });
        increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (stockSession > 0 && stockSession <= stock) {
                    quantity = quantity + 1;
                    quantityString = String.valueOf(quantity);
                    quantitytxt.setText(quantityString);

                    cost = 8.95 * quantity;
                    cost = Math.round(cost * 100.0) / 100.0;
                    costString = String.valueOf(cost);
                    String itemCostFormat = String.format("£" + costString);
                    costtxt.setText(itemCostFormat);

                    stockSession--;
                    String stockString = String.valueOf(stockSession);
                    stocktxt.setText(stockString);
                }
            }
        });
        decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (stockSession >= 0 && stockSession < stock) {
                    quantity = quantity - 1;
                    quantityString = String.valueOf(quantity);
                    quantitytxt.setText(quantityString);

                    cost = 8.95 * quantity;
                    cost = Math.round(cost * 100.0) / 100.0;
                    costString = String.valueOf(cost);
                    String itemCostFormat = String.format("£" + costString);
                    costtxt.setText(itemCostFormat);

                    stockSession++;
                    String stockString = String.valueOf(stockSession);
                    stocktxt.setText(stockString);
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHome();
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                if (count == 2){
                    if (shrimpcheck == false){
                        Toast.makeText(padthai.this, "Select Meat", Toast.LENGTH_SHORT).show();
                    }else {
                        updateStock();
                        saveFood2();
                        openHome();
                    }
                }
                else {
                    Toast.makeText(padthai.this, "Press again to confirm", Toast.LENGTH_SHORT).show();
                }
            }
        });
        basket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBasket();
            }
        });
        orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openOrders();
            }
        });
    }
    private void updateStock() {
        rootNode = FirebaseDatabase.getInstance();
        DatabaseReference stockRef = rootNode.getReference("stock");
        DatabaseReference dayRef = stockRef.child("friday");
        DatabaseReference foodRef = dayRef.child("food2 pad thai");

        String stockString = String.valueOf(stockSession);
        foodRef.setValue(stockString);

    }

    private void retrieveStock() {
        rootNode = FirebaseDatabase.getInstance();
        DatabaseReference stockRef = rootNode.getReference("stock");
        DatabaseReference dayRef = stockRef.child("friday");

        dayRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                stockSnapshot = snapshot.child("food2 pad thai").getValue(String.class);

                stocktxt.setText(stockSnapshot);

                stock = Integer.parseInt(stockSnapshot);
                stockSession = stock;
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                System.out.println("Error: " + error.getMessage());
            }
        });
    }

    private void saveFood2() {
        SharedPreferences food2 = getSharedPreferences("food2", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = food2.edit();
        editor.putString("COST2", costString);
        editor.putString("QUANTITY2", quantityString);
        editor.putString("MEAT", meatChoice);
        editor.apply();
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
    public void openOrders(){
        Intent intent = new Intent(this, orders.class);
        startActivity(intent);
        overridePendingTransition(0,0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
    }
}
