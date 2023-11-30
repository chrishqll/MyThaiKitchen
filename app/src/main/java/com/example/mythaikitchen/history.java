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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.*;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class history extends AppCompatActivity {
    int orderidInt;
    FirebaseDatabase rootNode;
    String totalCostString, food1, food2, food3, food4, food5, food6, quantity1str, quantity2str, quantity3str, quantity4str, quantity5str, quantity6str, meat1, meat2, meat3, timeFormat;
    TextView time1, food11, food12, food13, food14, food15, food16, quantity11, quantity12, quantity13, quantity14, quantity15, quantity16, totalcost1;
    TextView time2, food21, food22, food23, food24, food25, food26, quantity22, quantity21, quantity23, quantity24, quantity25, quantity26, totalcost2;
    String phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        time1 = findViewById(R.id.orderid1);
        food11 = findViewById(R.id.food11);
        food12 = findViewById(R.id.food12);
        food13 = findViewById(R.id.food13);
        food14 = findViewById(R.id.food14);
        food15 = findViewById(R.id.food15);
        food16 = findViewById(R.id.food16);
        quantity11 = findViewById(R.id.quantity11);
        quantity12 = findViewById(R.id.quantity12);
        quantity13 = findViewById(R.id.quantity13);
        quantity14 = findViewById(R.id.quantity14);
        quantity15 = findViewById(R.id.quantity15);
        quantity16 = findViewById(R.id.quantity16);
        totalcost1 = findViewById(R.id.totalcost1);

        time2 = findViewById(R.id.orderid2);
        food21 = findViewById(R.id.food21);
        food22 = findViewById(R.id.food22);
        food23 = findViewById(R.id.food23);
        food24 = findViewById(R.id.food24);
        food25 = findViewById(R.id.food25);
        food26 = findViewById(R.id.food26);
        quantity21 = findViewById(R.id.quantity21);
        quantity22 = findViewById(R.id.quantity22);
        quantity23 = findViewById(R.id.quantity23);
        quantity24 = findViewById(R.id.quantity24);
        quantity25 = findViewById(R.id.quantity25);
        quantity26 = findViewById(R.id.quantity26);
        totalcost2 = findViewById(R.id.totalcost2);

        ImageButton back = findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openOrders();
            }
        });

        retrieveOrderID();

        retrieveOrder1();
        setValue1();
        retrieveOrder2();
        setValue2();
    }

    private void setValue2() {
        time2.setText(timeFormat);
        food21.setText(food1);
        food22.setText(food2);
        food23.setText(food3);
        food24.setText(food4);
        food25.setText(food5);
        food26.setText(food6);
        quantity21.setText(quantity1str);
        quantity22.setText(quantity2str);
        quantity23.setText(quantity3str);
        quantity24.setText(quantity4str);
        quantity25.setText(quantity5str);
        String totalCostFormat = String.format("£" + totalCostString);
        totalcost2.setText(totalCostFormat);
    }

    private void setValue1() {
        time1.setText(timeFormat);
        food11.setText(food1);
        food12.setText(food2);
        food13.setText(food3);
        food14.setText(food4);
        food15.setText(food5);
        food16.setText(food6);
        quantity11.setText(quantity1str);
        quantity12.setText(quantity2str);
        quantity13.setText(quantity3str);
        quantity14.setText(quantity4str);
        quantity15.setText(quantity5str);
        String totalCostFormat = String.format("£" + totalCostString);
        totalcost1.setText(totalCostFormat);
    }

    private void retrieveOrderID() {
        SharedPreferences orderid = getSharedPreferences("orderid", Context.MODE_PRIVATE);
        orderidInt = orderid.getInt("ORDERID", 0);
    }

    private void retrieveOrder2() {
        orderidInt--;
        String orderidStr = String.valueOf(orderidInt);

        // Create a reference to the data you want to retrieve
        rootNode = FirebaseDatabase.getInstance();
        DatabaseReference usersRef = rootNode.getReference("users");
        DatabaseReference infoRef = usersRef.child(phoneNumber);
        DatabaseReference orderRef = infoRef.child("orders");
        DatabaseReference orderidRef = orderRef.child(orderidStr);

        // Attach an event listener to the reference
        orderidRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                // Get the data from the snapshot
                totalCostString = snapshot.child("totalCostString").getValue(String.class);
                food1 = snapshot.child("food1").getValue(String.class);
                food2 = snapshot.child("food2").getValue(String.class);
                food3 = snapshot.child("food3").getValue(String.class);
                food4 = snapshot.child("food4").getValue(String.class);
                food5 = snapshot.child("food5").getValue(String.class);
                food6 = snapshot.child("food6").getValue(String.class);
                quantity1str = snapshot.child("quantity1str").getValue(String.class);
                quantity2str = snapshot.child("quantity2str").getValue(String.class);
                quantity3str = snapshot.child("quantity3str").getValue(String.class);
                quantity4str = snapshot.child("quantity4str").getValue(String.class);
                quantity5str = snapshot.child("quantity5str").getValue(String.class);
                quantity6str = snapshot.child("quantity6str").getValue(String.class);
                meat1 = snapshot.child("meat1").getValue(String.class);
                meat2 = snapshot.child("meat2").getValue(String.class);
                meat3 = snapshot.child("meat3").getValue(String.class);
                timeFormat = snapshot.child("timeFormat").getValue(String.class);

                setValue2();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Handle the error
                System.out.println("Error: " + error.getMessage());
            }
        });
    }

    private void retrieveOrder1(){
        //getting important values
        SharedPreferences address = getSharedPreferences("MyUser", Context.MODE_PRIVATE);
        phoneNumber = address.getString("PHONENUMBER", "");
        SharedPreferences orderid = getSharedPreferences("orderid", Context.MODE_PRIVATE);
        orderidInt = orderid.getInt("ORDERID", 0);

        orderidInt--;
        String orderidStr = String.valueOf(orderidInt);

        // Create a reference to the data you want to retrieve
        rootNode = FirebaseDatabase.getInstance();
        DatabaseReference usersRef = rootNode.getReference("users");
        DatabaseReference infoRef = usersRef.child(phoneNumber);
        DatabaseReference orderRef = infoRef.child("orders");
        DatabaseReference orderidRef = orderRef.child(orderidStr);

        // Attach an event listener to the reference
        orderidRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                // Get the data from the snapshot
                totalCostString = snapshot.child("totalCostString").getValue(String.class);
                food1 = snapshot.child("food1").getValue(String.class);
                food2 = snapshot.child("food2").getValue(String.class);
                food3 = snapshot.child("food3").getValue(String.class);
                food4 = snapshot.child("food4").getValue(String.class);
                food5 = snapshot.child("food5").getValue(String.class);
                food6 = snapshot.child("food6").getValue(String.class);
                quantity1str = snapshot.child("quantity1str").getValue(String.class);
                quantity2str = snapshot.child("quantity2str").getValue(String.class);
                quantity3str = snapshot.child("quantity3str").getValue(String.class);
                quantity4str = snapshot.child("quantity4str").getValue(String.class);
                quantity5str = snapshot.child("quantity5str").getValue(String.class);
                quantity6str = snapshot.child("quantity6str").getValue(String.class);
                meat1 = snapshot.child("meat1").getValue(String.class);
                meat2 = snapshot.child("meat2").getValue(String.class);
                meat3 = snapshot.child("meat3").getValue(String.class);
                timeFormat = snapshot.child("timeFormat").getValue(String.class);

                setValue1();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Handle the error
                Toast.makeText(history.this, "Error" + error.getMessage(), Toast.LENGTH_SHORT).show();
                openOrders();
            }
        });
    }

    private void openOrders(){
        Intent intent = new Intent(this, orders.class);
        startActivity(intent);
        overridePendingTransition(0,0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
    }
}