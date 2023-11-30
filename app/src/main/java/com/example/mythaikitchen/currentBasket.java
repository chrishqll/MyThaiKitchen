package com.example.mythaikitchen;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Calendar;

public class currentBasket extends AppCompatActivity {

    String food1, food2, food3, food4, food5, food6, quantity1str, quantity2str, quantity3str, quantity4str, quantity5str, quantity6str, cost1str, cost2str, cost3str, cost4str, cost5str, cost6str;
    double cost1db, cost2db, cost3db, cost4db, cost5db, cost6db;
    TextView food1txt, food2txt, food3txt, food4txt, food5txt, food6txt;
    TextView quantity1txt, quantity2txt, quantity3txt, quantity4txt, quantity5txt, quantity6txt;
    TextView cost1txt, cost2txt, cost3txt, cost4txt, cost5txt, cost6txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_basket);

        ImageButton back = findViewById(R.id.back);

        food1txt = findViewById(R.id.food1);
        food2txt = findViewById(R.id.food2);
        food3txt = findViewById(R.id.food3);
        food4txt = findViewById(R.id.food4);
        food5txt = findViewById(R.id.food5);
        food6txt = findViewById(R.id.food6);

        cost1txt = findViewById(R.id.cost1);
        cost2txt = findViewById(R.id.cost2);
        cost3txt = findViewById(R.id.cost3);
        cost4txt = findViewById(R.id.cost4);
        cost5txt = findViewById(R.id.cost5);
        cost6txt = findViewById(R.id.cost6);

        quantity1txt =  findViewById(R.id.quantity1);
        quantity2txt =  findViewById(R.id.quantity2);
        quantity3txt =  findViewById(R.id.quantity3);
        quantity4txt =  findViewById(R.id.quantity4);
        quantity5txt =  findViewById(R.id.quantity5);
        quantity6txt =  findViewById(R.id.quantity6);

        food4 = "Crab Rangoons";
        food4txt.setText(food4);
        food5 = "Steamed Rice";
        food5txt.setText(food5);
        food6 = "Spring Rolls";
        food6txt.setText(food6);

        quantity1str = "0";
        quantity1txt.setText(quantity1str);
        quantity2str = "0";
        quantity2txt.setText(quantity2str);
        quantity3str = "0";
        quantity3txt.setText(quantity3str);
        quantity4str = "0";
        quantity4txt.setText(quantity4str);
        quantity5str = "0";
        quantity5txt.setText(quantity5str);
        quantity6str = "0";
        quantity6txt.setText(quantity6str);

        cost1str = "£0.0";
        cost1txt.setText(cost1str);
        cost2str = "£0.0";
        cost2txt.setText(cost2str);
        cost3str = "£0.0";
        cost3txt.setText(cost3str);
        cost4str = "£0.0";
        cost4txt.setText(cost4str);
        cost5str = "£0.0";
        cost5txt.setText(cost5str);
        cost6str = "£0.0";
        cost6txt.setText(cost6str);

        Calendar calendar = Calendar.getInstance();
        int dayOfWeek= calendar.get(Calendar.DAY_OF_WEEK);
        switch (dayOfWeek){

            case 2:
                food1 = "Masaman Curry";
                food1txt.setText(food1);
                food2 = "Fried Rice";
                food2txt.setText(food2);
                break;
            case 3:
                food1 = "Penang Curry";
                food1txt.setText(food1);
                food2 = "Tom Yum";
                food2txt.setText(food2);
                break;
            case 4:
                food1 = "Chicken Satay";
                food1txt.setText(food1);
                food2 = "Stir Fried Meat";
                food2txt.setText(food2);
                break;
            case 5:
                food1 = "Khao Soi";
                food1txt.setText(food1);
                food2 = "Cashew Nut Chicken";
                food2txt.setText(food2);
                food3 = "Pad Krapow";
                food3txt.setText(food3);
                quantity3str = "0";
                quantity3txt.setText(quantity3str);
                break;
            case 6:
                food1 = "Pad Thai";
                food1txt.setText(food1);
                food2 = "Tom Kha";
                food2txt.setText(food2);
                break;
        }

        //retrieving and displaying individual costs and quantities
        retrieveFood1();
        String cost1format = String.format("£" + cost1str);
        cost1txt.setText(cost1format);
        quantity1txt.setText(quantity1str);

        retrieveFood2();
        String cost2format = String.format("£" + cost2str);
        cost2txt.setText(cost2format);
        quantity2txt.setText(quantity2str);

        retrieveFood3();
        String cost3format = String.format("£" + cost3str);
        cost3txt.setText(cost3format);
        quantity3txt.setText(quantity3str);

        retrieveFood4();
        String cost4format = String.format("£" + cost4str);
        cost4txt.setText(cost4format);
        quantity4txt.setText(quantity4str);

        retrieveFood5();
        String cost5format = String.format("£" + cost5str);
        cost5txt.setText(cost5format);
        quantity5txt.setText(quantity5str);

        retrieveFood6();
        String cost6format = String.format("£" + cost6str);
        cost6txt.setText(cost6format);
        quantity6txt.setText(quantity6str);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openOrders();
            }
        });
    }
    private void retrieveFood1(){
        SharedPreferences food1copy = getSharedPreferences("food1copy", Context.MODE_PRIVATE);
        cost1str = food1copy.getString("COST1", "0.0");
        cost1db = Double.parseDouble(cost1str);
        quantity1str = food1copy.getString("QUANTITY1", "0");
    }
    private void retrieveFood2(){
        SharedPreferences food2copy = getSharedPreferences("food2copy", Context.MODE_PRIVATE);
        cost2str = food2copy.getString("COST2", "0.0");
        cost2db = Double.parseDouble(cost2str);
        quantity2str = food2copy.getString("QUANTITY2", "0");
    }
    private void retrieveFood3(){
        SharedPreferences food3copy = getSharedPreferences("food3copy", Context.MODE_PRIVATE);
        cost3str = food3copy.getString("COST3", "0.0");
        cost3db = Double.parseDouble(cost3str);
        quantity3str = food3copy.getString("QUANTITY3", "0");
    }
    private void retrieveFood4(){
        SharedPreferences food4copy = getSharedPreferences("food4copy", Context.MODE_PRIVATE);
        cost4str = food4copy.getString("COST4", "0.0");
        cost4db = Double.parseDouble(cost4str);
        quantity4str = food4copy.getString("QUANTITY4", "0");
    }
    private void retrieveFood5(){
        SharedPreferences food5copy = getSharedPreferences("food5copy", Context.MODE_PRIVATE);
        cost5str = food5copy.getString("COST5", "0.0");
        cost5db = Double.parseDouble(cost5str);
        quantity5str = food5copy.getString("QUANTITY5", "0");
    }
    private void retrieveFood6(){
        SharedPreferences food6copy = getSharedPreferences("food6copy", Context.MODE_PRIVATE);
        cost6str = food6copy.getString("COST6", "0.0");
        cost6db = Double.parseDouble(cost6str);
        quantity6str = food6copy.getString("QUANTITY6", "0");
    }

    public void openOrders(){
        onBackPressed();
    }
}