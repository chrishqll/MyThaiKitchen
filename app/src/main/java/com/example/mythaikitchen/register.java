package com.example.mythaikitchen;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class register extends AppCompatActivity {
    public static String phoneNumber, fullName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        checkLogin(); //recheck this when finish

        EditText phoneNumberIp = findViewById(R.id.phoneNumber);
        EditText fullNameIp = findViewById(R.id.fullName);
        ImageButton confirmBt = findViewById(R.id.confirmBt);

        confirmBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //only proceeds if all fields of data is entered by the customer
                if (TextUtils.isEmpty(phoneNumberIp.getText().toString()) || TextUtils.isEmpty(fullNameIp.getText().toString())) {
                    Toast.makeText(register.this, "Missing Information", Toast.LENGTH_SHORT).show();
                } else {
                    fullName = fullNameIp.getText().toString();
                    phoneNumber = phoneNumberIp.getText().toString();
                    formatPhoneNumber();
                    saveUser();
                    openAuthentication();
                }
            }
        });
    }
    private void saveUser() {
        SharedPreferences address = getSharedPreferences("MyUser", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = address.edit();
        editor.putString("NAME", fullName);
        editor.putString("PHONENUMBER", phoneNumber);
        editor.apply();

    }
    private void checkLogin(){
        SharedPreferences login = getSharedPreferences("login", Context.MODE_PRIVATE);
        Boolean loggedIn = login.getBoolean("LOGGEDIN", false);
        if (loggedIn == true){
            openHome();
        }
    }
    private void openAuthentication(){
        Intent intent = new Intent(this, authentication.class); //change this to authentication activity later
        startActivity(intent);
        overridePendingTransition(0,0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
    }
    private void openHome(){
        Intent intent = new Intent(this, mainRelay.class);
        startActivity(intent);
        overridePendingTransition(0,0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
    }

    private void formatPhoneNumber() {
        //removes intial zero from phone number
        Pattern pattern = Pattern.compile("^0");
        Matcher matcher = pattern.matcher(phoneNumber);
        if (matcher.find()) {
            phoneNumber = phoneNumber.substring(1);
        }
        //adds country code
        phoneNumber = "+66" + phoneNumber;
    }
}