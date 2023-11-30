package com.example.mythaikitchen;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.*;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.List;

import static androidx.core.location.LocationManagerCompat.requestLocationUpdates;


public class myLocation extends AppCompatActivity implements LocationListener {

    private FusedLocationProviderClient fusedLocationProviderClient;
    private Geocoder geocoder;
    private String addressString;
    TextView addresstxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_location);

        addresstxt = findViewById(R.id.address);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        geocoder = new Geocoder(this);

        getLastKnownLocation();
        addresstxt.setText("Showing For: " + addressString);
        saveAddress();
        //openMapView();

    }

    private void getLastKnownLocation() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.getLastLocation()
                .addOnSuccessListener(this, location -> {
                    getAddressString(location);
                })
                .addOnFailureListener(this, e -> {
                    // Handle failure
                });
    }
    @Override
    public void onLocationChanged(@NonNull Location location) {
        getAddressString(location);
    }
    private void getAddressString(Location location){
        try {
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            if (addresses != null && addresses.size() > 0) {
                addressString = addresses.get(0).getAddressLine(0);
            }
        } catch (IOException e) {
            // Handle failure
        }
    }
    private void saveAddress() {
        //creating sharedPreferences directory address
        SharedPreferences address = getSharedPreferences("MyLatLng", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = address.edit();
        //adding sub-values into address
        editor.putString("REVERSEADDRESS", addressString);
        editor.apply();
    }

    private void openMapView() {
        Intent intent = new Intent(this, mapView.class);
        startActivity(intent);
        overridePendingTransition(0,0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
    }

}