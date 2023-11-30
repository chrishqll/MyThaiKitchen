package com.example.mythaikitchen;

import android.content.Intent;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import java.util.Calendar;

public class mainRelay extends AppCompatActivity {
    public static int dayOfWeek;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_relay);

        Calendar calendar = Calendar.getInstance();
        dayOfWeek= calendar.get(Calendar.DAY_OF_WEEK);

        // assigning an activity to the specific day of week.
        //using switch statement to speed up process
        switch (dayOfWeek) {

            case 9:
                Toast.makeText(mainRelay.this, "Sorry, The Kitchen is Closed on Sundays", Toast.LENGTH_LONG).show();
                break;
            case 1,7,2:
                Intent intent2 = new Intent(this, monday.class);
                startActivity(intent2);
                overridePendingTransition(0,0);
                intent2.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                break;
            case 3:
                Intent intent3 = new Intent(this, tuesday.class);
                startActivity(intent3);
                overridePendingTransition(0,0);
                intent3.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                break;

            case 4:
                Intent intent4 = new Intent(this, wednesday.class);
                startActivity(intent4);
                overridePendingTransition(0,0);
                intent4.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                break;

            case 5:
                Intent intent5 = new Intent(this, thursday.class);
                startActivity(intent5);
                overridePendingTransition(0,0);
                intent5.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                break;
            case 6:
                Intent intent6 = new Intent(this, friday.class);
                startActivity(intent6);
                overridePendingTransition(0,0);
                intent6.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                break;
            case 8:
                Toast.makeText(mainRelay.this, "Sorry, The Kitchen is Closed on Saturdays", Toast.LENGTH_LONG).show();
                break;

        }
    }
}