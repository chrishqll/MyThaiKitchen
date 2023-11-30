package com.example.mythaikitchen;

import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class tuesday extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tuesday);

        ImageButton schedule = findViewById(R.id.schedule);
        ImageButton editUser = findViewById(R.id.editUser);
        ImageButton food1 = findViewById(R.id.food1);
        ImageButton food2 = findViewById(R.id.food2);
        ImageButton crab = findViewById(R.id.crabRangoons);
        ImageButton rice = findViewById(R.id.steamedRice);
        ImageButton springrolls = findViewById(R.id.springRolls);
        ImageButton home = findViewById(R.id.home);
        ImageButton basket = findViewById(R.id.basket);
        ImageButton orders = findViewById(R.id.orders);

        schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSchedule();
            }
        });
        editUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openUser();
            }
        });
        food1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFood1();
            }
        });
        food2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFood2();
            }
        });
        crab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCrab();
            }
        });
        rice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRice();
            }
        });
        springrolls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSpringrolls();
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

    public void openSchedule(){
        Intent intent = new Intent(this, schedule.class);
        startActivity(intent);
        overridePendingTransition(0,0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
    }
    public void openUser(){
        Intent intent = new Intent(this, editUserSettings.class);
        startActivity(intent);
        overridePendingTransition(0,0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
    }
    public void openFood1(){
        Intent intent = new Intent(this, penangcurry.class);
        startActivity(intent);
        overridePendingTransition(0,0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
    }
    public void openFood2(){
        Intent intent = new Intent(this, tomyum.class);
        startActivity(intent);
        overridePendingTransition(0,0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
    }
    public void openCrab(){
        Intent intent = new Intent(this, crabrangoons.class);
        startActivity(intent);
        overridePendingTransition(0,0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
    }
    public void openRice(){
        Intent intent = new Intent(this, rice.class);
        startActivity(intent);
        overridePendingTransition(0,0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
    }
    public void openSpringrolls(){
        Intent intent = new Intent(this, springrolls.class);
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
