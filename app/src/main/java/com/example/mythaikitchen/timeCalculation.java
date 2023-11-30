package com.example.mythaikitchen;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.SharedPreferences;
import com.google.android.gms.maps.model.LatLng;
import org.jetbrains.annotations.NotNull;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;

import java.io.IOException;

public class timeCalculation extends AppCompatActivity {

    String kitchenAddress;
    String city, postcode, street, houseNumber, addressFormat;
    double kitchenLatitude, kitchenLongitude, destLatitude, destLongitude;
    int timeMin;
    private LatLng latLngOrigin, latLngDestination;
    private GoogleMap mMap;
    TextView addresstxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_calculation);

        kitchenAddress = "60 ButtercupWay TQ12 Newton Abbot";
        addressFormat = "7490 Wolborough TQ12 Newton Abbot";

        addresstxt = findViewById(R.id.address);

        //retrieveAddress();
        //addressFormat = String.format(houseNumber + " " + street + " " + postcode + " " + city);

        try {
            geocodeKitchen();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            geocodeAddress();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        tempCalc();
        addresstxt.setText(timeMin);
        storeTime();
        //openOrders();
    }

    private void retrieveAddress() {
        SharedPreferences address = getSharedPreferences("MyAddress", Context.MODE_PRIVATE);
        city = address.getString("CITY", "");
        postcode = address.getString("POSTCODE", "");
        street = address.getString("STREET", "");
        houseNumber = address.getString("HOUSENUMBER", "");

    }

    private void geocodeKitchen() throws IOException {
        Geocoder geocoder = new Geocoder(this);
        //creating address object and geocoding it
        Address address = geocoder.getFromLocationName(kitchenAddress, 1).get(0);
        //extracting coordinates from geocode
        latLngOrigin = new LatLng(address.getLatitude(), address.getLongitude());

    }

    private void geocodeAddress() throws IOException {
        Geocoder geocoder = new Geocoder(this);
        //creating address object and geocoding it
        Address address = geocoder.getFromLocationName(addressFormat, 1).get(0);
        //extracting coordinates from geocode
        latLngDestination = new LatLng(address.getLatitude(), address.getLongitude());

    }
    //Temp method: finding direct distance, dividing it by avg speed of delivery vehicle.
    private void tempCalc(){
        kitchenLatitude = latLngOrigin.latitude;
        kitchenLongitude = latLngOrigin.longitude;
        destLatitude = latLngDestination.latitude;
        destLongitude = latLngDestination.longitude;

        float[] result = new float[1];
        Location.distanceBetween(kitchenLatitude, kitchenLongitude, destLatitude, destLongitude, result);

        double distancedb = result[0];
        double timeSeconds = (distancedb / 3.57632) + 600 + 800;
        //added 600 seconds for traffic estimation
        //added 800 seconds for extra cooking and processing time
        //3.572 = 8mph (average deliveroo deliver speed);
        double timeMindb = timeSeconds / 60;
        timeMin = (int) Math.round(timeMindb);
    }

    private void storeTime(){
        //creating sharedPreferences directory address
        String timeMinString = Integer.toString(timeMin);

        SharedPreferences deliveryTime = getSharedPreferences("DeliveryTime", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = deliveryTime.edit();
        //adding sub-values into address
        editor.putString("DELIVERYTIME", timeMinString);
        editor.apply();
    }
    private void openOrders(){
        Intent intent = new Intent(this, orders.class);
        startActivity(intent);
        overridePendingTransition(0,0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
    }



//Google Matrix API not working at the moment.
}
