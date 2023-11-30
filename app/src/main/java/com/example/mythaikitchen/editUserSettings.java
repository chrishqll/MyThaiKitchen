package com.example.mythaikitchen;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class editUserSettings extends AppCompatActivity {
    String city, postcode, street, houseNumber;
    TextView citypref, postcodepref, streetpref, houseNumberpref;
    String phoneNumber;
    FirebaseDatabase rootNode;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_settings);

        retrievePhoneNumber();

        ImageButton confirmBt = findViewById(R.id.confirmBt);
        EditText cityIp = findViewById(R.id.city);
        EditText postcodeIp = findViewById(R.id.postcode);
        EditText streetIp = findViewById(R.id.street);
        EditText houseNumIp = findViewById(R.id.houseNumber);

        citypref = findViewById(R.id.citypretxt);
        postcodepref = findViewById(R.id.postcodepretxt);
        streetpref = findViewById(R.id.streetpretxt);
        houseNumberpref = findViewById(R.id.houseNumberpretxt);

        showAddress();

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
                    Toast.makeText(editUserSettings.this, "Missing Information", Toast.LENGTH_SHORT).show();
                }
                else{
                    if (postcode.equals("TQ12")) {
                        // TODO: connect database, send to database: customerData
                        saveAddress();
                        uploadData();
                        openMainActivity();
                    } else {
                        Toast.makeText(editUserSettings.this, "Invalid Postcode, must be TQ12", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
    private void retrievePhoneNumber(){
        SharedPreferences address = getSharedPreferences("MyUser", Context.MODE_PRIVATE);
        phoneNumber = address.getString("PHONENUMBER", "");
}
    private void uploadData(){
        rootNode = FirebaseDatabase.getInstance();
        DatabaseReference usersRef = rootNode.getReference("users");
        DatabaseReference infoRef = usersRef.child(phoneNumber);
        DatabaseReference addressRef = infoRef.child("address");

        String addressFormat = (city +" " + postcode +" " +street +" "+houseNumber);

        addressRef.setValue(addressFormat);
    }
    private void saveAddress() {
        SharedPreferences address = getSharedPreferences("MyAddress", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = address.edit();
        editor.putString("CITY", city);
        editor.putString("POSTCODE", postcode);
        editor.putString("STREET", street);
        editor.putString("HOUSENUMBER", houseNumber);
        editor.apply();
    }

    private void showAddress(){
        SharedPreferences address = getSharedPreferences("MyAddress", Context.MODE_PRIVATE);
        city = address.getString("CITY", "");
        postcode = address.getString("POSTCODE", "");
        street = address.getString("STREET", "");
        houseNumber = address.getString("HOUSENUMBER", "");

        String cityFormat = String.format(city+"");
        String postcodeFormat = String.format(postcode+"");
        String streetFormat = String.format(street+"");
        String houseNumFormat = String.format(houseNumber+"");

        citypref.setText(cityFormat);
        postcodepref.setText(postcodeFormat);
        streetpref.setText(streetFormat);
        houseNumberpref.setText(houseNumFormat);
    }

    public void openMainActivity(){
        Intent intent = new Intent(this, mapView.class);
        startActivity(intent);
        overridePendingTransition(0,0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
    }
}