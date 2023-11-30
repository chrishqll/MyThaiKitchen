package com.example.mythaikitchen;

import android.content.Context;
import android.content.Intent;

import android.location.Location;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import java.io.IOException;


public class mapView extends AppCompatActivity implements OnMapReadyCallback{

    String city, postcode, street, houseNumber, addressFormat;
    double longitude, latitude;
    TextView addresstxt;
    private GoogleMap mMap;
    private LatLng latLng;
    ImageButton back, next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_view);

        back = findViewById(R.id.back);
        next = findViewById(R.id.confirmBt);
        addresstxt = findViewById(R.id.address);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openEditUserSettings();
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivity();
            }
        });

        retrieveAddress();
        addressFormat = String.format(houseNumber + " " + street + " " + postcode + " " + city);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        try {
            geocode();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        addresstxt.setText("Showing For: " + addressFormat);
        makeLoggedIn();
    }

    private void retrieveAddress(){
        SharedPreferences address = getSharedPreferences("MyAddress", Context.MODE_PRIVATE);
        city = address.getString("CITY", "");
        postcode = address.getString("POSTCODE", "");
        street = address.getString("STREET", "");
        houseNumber = address.getString("HOUSENUMBER", "");
    }
    private void geocode() throws IOException {
        Geocoder geocoder = new Geocoder(this);
        //creating address object and geocoding it
        Address address = geocoder.getFromLocationName(addressFormat, 1).get(0);
        //extracting coordinates from geocode
        latLng = new LatLng(address.getLatitude(), address.getLongitude());

        latitude = latLng.latitude;
        longitude = latLng.longitude;
    }

    public void onMapReady(GoogleMap googleMap){
        mMap = googleMap;

        // Add a marker to the map at the specified latitude and longitude.
        Marker marker = mMap.addMarker(new MarkerOptions().position(latLng).title("Marker"));

        // Move the camera to the marker position.
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
    }

    private void openEditUserSettings(){
        Intent intent = new Intent(this, editUserSettings.class);
        startActivity(intent);
        overridePendingTransition(0,0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
    }

    private void openMainActivity(){
        Intent intent = new Intent(this, mainRelay.class);
        startActivity(intent);
        overridePendingTransition(0,0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
    }

    private void makeLoggedIn(){
        SharedPreferences login = getSharedPreferences("login", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = login.edit();
        editor.putBoolean("LOGGEDIN", true);
        editor.apply();
    }
}