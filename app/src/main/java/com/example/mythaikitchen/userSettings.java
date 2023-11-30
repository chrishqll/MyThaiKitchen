package com.example.mythaikitchen;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import android.widget.Toast;



public class userSettings extends AppCompatActivity {
    String city, postcode, street, houseNumber;
    TextView citypref, postcodepref, streetpref, houseNumberpref;
    FirebaseDatabase rootNode;
    DatabaseReference reference;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_settings);

        ImageButton confirmBt = findViewById(R.id.confirmBt);
        EditText cityIp = findViewById(R.id.city);
        EditText postcodeIp = findViewById(R.id.postcode);
        EditText streetIp = findViewById(R.id.street);
        EditText houseNumIp = findViewById(R.id.houseNumber);

        citypref = findViewById(R.id.citypretxt);
        postcodepref = findViewById(R.id.postcodepretxt);
        streetpref = findViewById(R.id.streetpretxt);
        houseNumberpref = findViewById(R.id.houseNumberpretxt);

        confirmBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postcode = postcodeIp.getText().toString();
                city = cityIp.getText().toString();
                street = streetIp.getText().toString();
                houseNumber = houseNumIp.getText().toString();

                //only proceeds if the postcode entered is correct. Ensures a close delivery radius.
                postcode = postcode.toUpperCase();
                if (TextUtils.isEmpty(cityIp.getText().toString()) || TextUtils.isEmpty(postcodeIp.getText().toString()) || TextUtils.isEmpty(streetIp.getText().toString()) || TextUtils.isEmpty(houseNumIp.getText().toString())) {
                    Toast.makeText(userSettings.this, "Missing Information", Toast.LENGTH_SHORT).show();
                }
                else{
                    if (postcode.equals("TQ12")) {
                        // TODO: connect database, send to database: customerData
                        resetValues();
                        saveAddress();
                        uploadData();
                        reset();
                        openMainActivity();
                    } else {
                        Toast.makeText(userSettings.this, "Invalid Postcode, must be TQ12", Toast.LENGTH_SHORT).show();
                    }
                }
            }
                });

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
    private void saveAddress() {
        //creating sharedPreferences directory address
        SharedPreferences address = getSharedPreferences("MyAddress", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = address.edit();
        //adding sub-values into address
        editor.putString("CITY", city);
        editor.putString("POSTCODE", postcode);
        editor.putString("STREET", street);
        editor.putString("HOUSENUMBER", houseNumber);
        editor.apply();
    }

    public void openMainActivity(){
        Intent intent = new Intent(this, mapView.class);
        startActivity(intent);
        overridePendingTransition(0,0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
    }

    private void uploadData(){
        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("users");

        //Get all values
        SharedPreferences address = getSharedPreferences("MyUser", Context.MODE_PRIVATE);
        String fullName = address.getString("NAME", "");
        String phoneNumber = address.getString("PHONENUMBER", "");
        String addressFormat = (city +" " + postcode +" " +street +" "+houseNumber);

        userHelper helperClass = new userHelper(fullName, phoneNumber, addressFormat);

        reference.child(phoneNumber).setValue(helperClass);
    }

    private void reset(){
        SharedPreferences deliveryStatus = getSharedPreferences("deliveryStatus", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = deliveryStatus.edit();
        editor.putBoolean("DELIVERYSTATUS", false);
        editor.apply();
    }
}