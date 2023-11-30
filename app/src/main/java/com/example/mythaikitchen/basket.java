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
import java.util.Calendar;

public class basket extends AppCompatActivity {

    String totalCostString = "0", food1, food2, food3, food4, food5, food6, quantity1str, quantity2str, quantity3str, quantity4str, quantity5str, quantity6str, cost1str, cost2str, cost3str, cost4str, cost5str, cost6str, meat1, meat2, meat3, timeFormat, deliveringStatusStr, payment;
    double cost1db, cost2db, cost3db, cost4db, cost5db, cost6db;
    TextView food1txt, food2txt, food3txt, food4txt, food5txt, food6txt;
    TextView quantity1txt, quantity2txt, quantity3txt, quantity4txt, quantity5txt, quantity6txt;
    TextView cost1txt, cost2txt, cost3txt, cost4txt, cost5txt, cost6txt, totalCosttxt;
    TextView addresstxt;
    String city, postcode, street, houseNumber;
    double totalCostRounded;
    FirebaseDatabase rootNode;
    int orderidInt;
    int count = 0;
    boolean delivering;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basket);

        ImageButton next = findViewById(R.id.next);
        ImageButton home = findViewById(R.id.home);
        ImageButton orders = findViewById(R.id.orders);

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
        totalCosttxt = findViewById(R.id.totalcost);

        quantity1txt =  findViewById(R.id.quantity1);
        quantity2txt =  findViewById(R.id.quantity2);
        quantity3txt =  findViewById(R.id.quantity3);
        quantity4txt =  findViewById(R.id.quantity4);
        quantity5txt =  findViewById(R.id.quantity5);
        quantity6txt =  findViewById(R.id.quantity6);

        addresstxt = findViewById(R.id.address);

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
                food2 = "Stir Fried " + meat2;
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

        //displaying customer's address
        retrieveAddress();
        String addressFormat = String.format(city +", "+ postcode +", "+ street +", "+ houseNumber);
        addresstxt.setText(addressFormat);

        double totalCost = cost1db + cost2db + cost3db + cost4db + cost5db + cost6db;
        totalCostRounded = Math.round(totalCost * 100.0) / 100.0;
        totalCostString = String.valueOf(totalCostRounded);
        String itemCostFormat = String.format("£" + totalCostString);
        totalCosttxt.setText(itemCostFormat);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHome();
            }
        });
        orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openOrders();
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkDeliveryStatus();
                if (delivering == false){
                    count = 0;
                    updateDeliveryStatus();
                    getTime();
                    makeOrderID();
                    storeCost();
                    copyValues();
                    uploadData();
                    resetValues();
                    openConfirm();
                }
                else {
                    Toast.makeText(basket.this, "Cannot have more than 1 order", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void retrieveAddress(){
        SharedPreferences address = getSharedPreferences("MyAddress", Context.MODE_PRIVATE);
        city = address.getString("CITY", "");
        postcode = address.getString("POSTCODE", "");
        street = address.getString("STREET", "");
        houseNumber = address.getString("HOUSENUMBER", "");
    }
    private void retrieveFood1(){
        SharedPreferences food1 = getSharedPreferences("food1", Context.MODE_PRIVATE);
        cost1str = food1.getString("COST1", "0.0");
        cost1db = Double.parseDouble(cost1str);
        quantity1str = food1.getString("QUANTITY1", "0");
        meat1 = food1.getString("MEAT", "");
    }
    private void retrieveFood2(){
        SharedPreferences food2 = getSharedPreferences("food2", Context.MODE_PRIVATE);
        cost2str = food2.getString("COST2", "0.0");
        cost2db = Double.parseDouble(cost2str);
        quantity2str = food2.getString("QUANTITY2", "0");
        meat2 = food2.getString("MEAT2", "");
    }
    private void retrieveFood3(){
        SharedPreferences food3 = getSharedPreferences("food3", Context.MODE_PRIVATE);
        cost3str = food3.getString("COST3", "0.0");
        cost3db = Double.parseDouble(cost3str);
        quantity3str = food3.getString("QUANTITY3", "0");
        meat3 = food3.getString("MEAT3", "");
    }
    private void retrieveFood4(){
        SharedPreferences food4 = getSharedPreferences("food4", Context.MODE_PRIVATE);
        cost4str = food4.getString("COST4", "0.0");
        cost4db = Double.parseDouble(cost4str);
        quantity4str = food4.getString("QUANTITY4", "0");
    }
    private void retrieveFood5(){
        SharedPreferences food5 = getSharedPreferences("food5", Context.MODE_PRIVATE);
        cost5str = food5.getString("COST5", "0.0");
        cost5db = Double.parseDouble(cost5str);
        quantity5str = food5.getString("QUANTITY5", "0");
    }
    private void retrieveFood6(){
        SharedPreferences food6 = getSharedPreferences("food6", Context.MODE_PRIVATE);
        cost6str = food6.getString("COST6", "0.0");
        cost6db = Double.parseDouble(cost6str);
        quantity6str = food6.getString("QUANTITY6", "0");
    }
    private void updateDeliveryStatus(){
        delivering = true;

        SharedPreferences deliveryStatus = getSharedPreferences("deliveryStatus", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = deliveryStatus.edit();
        editor.putBoolean("DELIVERYSTATUS", delivering);
        editor.apply();
    }
    private void checkDeliveryStatus(){
        SharedPreferences deliveryStatus = getSharedPreferences("deliveryStatus", Context.MODE_PRIVATE);
        delivering = deliveryStatus.getBoolean("DELIVERYSTATUS", false);
    }
    private void getTime(){
        // Get the current calendar instance
        Calendar calendar = Calendar.getInstance();

        // Get the current date
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Get the current time
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);

        timeFormat = String.format(day + "/" + month + "/" + year + " " + hour + ":" + minute + ":" + second);
    }
    private void copyValues(){
        SharedPreferences food1copy = getSharedPreferences("food1copy", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = food1copy.edit();
        editor.putString("COST1", cost1str);
        editor.putString("QUANTITY1", quantity1str);
        editor.apply();

        SharedPreferences food2copy = getSharedPreferences("food2copy", Context.MODE_PRIVATE);
        editor = food2copy.edit();
        editor.putString("COST2", cost2str);
        editor.putString("QUANTITY2", quantity2str);
        editor.apply();

        SharedPreferences food3copy = getSharedPreferences("food3copy", Context.MODE_PRIVATE);
        editor = food3copy.edit();
        editor.putString("COST3", cost3str);
        editor.putString("QUANTITY3", quantity3str);
        editor.apply();

        SharedPreferences food4copy = getSharedPreferences("food4copy", Context.MODE_PRIVATE);
        editor = food4copy.edit();
        editor.putString("COST4", cost4str);
        editor.putString("QUANTITY4", quantity4str);
        editor.apply();

        SharedPreferences food5copy = getSharedPreferences("food5copy", Context.MODE_PRIVATE);
        editor = food5copy.edit();
        editor.putString("COST5", cost5str);
        editor.putString("QUANTITY5", quantity5str);
        editor.apply();

        SharedPreferences food6copy = getSharedPreferences("food6copy", Context.MODE_PRIVATE);
        editor = food6copy.edit();
        editor.putString("COST6", cost6str);
        editor.putString("QUANTITY6", cost6str);
        editor.apply();
    }
    private void resetValues(){
        SharedPreferences food1 = getSharedPreferences("food1", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = food1.edit();
        editor.putString("COST1", "0");
        editor.putString("QUANTITY1", "0");
        editor.apply();

        SharedPreferences food2 = getSharedPreferences("food2", Context.MODE_PRIVATE);
        editor = food2.edit();
        editor.putString("COST2", "0");
        editor.putString("QUANTITY2", "0");
        editor.apply();

        SharedPreferences food3 = getSharedPreferences("food3", Context.MODE_PRIVATE);
        editor = food3.edit();
        editor.putString("COST3", "0");
        editor.putString("QUANTITY3", "0");
        editor.apply();

        SharedPreferences food4 = getSharedPreferences("food4", Context.MODE_PRIVATE);
        editor = food4.edit();
        editor.putString("COST4", "0");
        editor.putString("QUANTITY4", "0");
        editor.apply();

        SharedPreferences food5 = getSharedPreferences("food5", Context.MODE_PRIVATE);
        editor = food5.edit();
        editor.putString("COST5", "0");
        editor.putString("QUANTITY5", "0");
        editor.apply();

        SharedPreferences food6 = getSharedPreferences("food6", Context.MODE_PRIVATE);
        editor = food6.edit();
        editor.putString("COST6", "0");
        editor.putString("QUANTITY6", "0");
        editor.apply();

    }
    private void storeCost(){
        SharedPreferences totalcost = getSharedPreferences("totalcost", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = totalcost.edit();
        editor.putString("TOTALCOST", totalCostString);
        editor.apply();
    }
    private void uploadData(){
        SharedPreferences address = getSharedPreferences("MyUser", Context.MODE_PRIVATE);
        String phoneNumber = address.getString("PHONENUMBER", "");

        rootNode = FirebaseDatabase.getInstance();
        DatabaseReference usersRef = rootNode.getReference("users");
        DatabaseReference infoRef = usersRef.child(phoneNumber);
        DatabaseReference orderRef = infoRef.child("orders");
        String orderidStr = String.valueOf(orderidInt);
        DatabaseReference orderidRef = orderRef.child(orderidStr);
        //firebase directory: users > phone number> orders > order id

        payment = "PLACEHOLDER";
        deliveringStatusStr = "CONFIRMED";

        basketHelperClass helperClass = new basketHelperClass(totalCostString, food1, food2, food3, food4, food5, food6, quantity1str, quantity2str, quantity3str, quantity4str, quantity5str, quantity6str, meat1, meat2, meat3, timeFormat, deliveringStatusStr, payment);
        orderidRef.setValue(helperClass);
    }
    private void makeOrderID(){
        SharedPreferences orderid = getSharedPreferences("orderid", Context.MODE_PRIVATE);
        orderidInt = orderid.getInt("ORDERID", 0);

        SharedPreferences.Editor editor = orderid.edit();

        orderidInt++;

        editor.putInt("ORDERID", orderidInt);
        editor.apply();
    }
    public void openConfirm(){
        Intent intent = new Intent(this, confirmOrder.class);
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
    public void openOrders(){
        Intent intent = new Intent(this, orders.class);
        startActivity(intent);
        overridePendingTransition(0,0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
    }
}