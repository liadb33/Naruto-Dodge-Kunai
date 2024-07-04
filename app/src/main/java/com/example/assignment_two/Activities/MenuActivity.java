package com.example.assignment_two.Activities;



import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;


import com.example.assignment_two.Activities.MainActivity;
import com.example.assignment_two.R;
import com.example.assignment_two.Utilities.LocationManager;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

public class MenuActivity extends AppCompatActivity {
    private static final int MODE_BUTTON = 0;
    private static final int MODE_SENSOR = 1;
    private static final int RECORD_ACTIVITY = 2;

    private ExtendedFloatingActionButton menu_button_mode;
    private ExtendedFloatingActionButton menu_sensor_mode;
    private ExtendedFloatingActionButton menu_record_screen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        LocationManager.getInstance().requestLocationPermission(this);
        findViews();
        initViews();

    }

    private void initViews() {
        menu_button_mode.setOnClickListener(v -> changeActivity(MODE_BUTTON));
        menu_sensor_mode.setOnClickListener(v -> changeActivity(MODE_SENSOR));
        menu_record_screen.setOnClickListener(v -> changeActivity(RECORD_ACTIVITY));


    }

    private void changeActivity(int mode){
        Intent intent;
        if(mode == MODE_BUTTON || mode == MODE_SENSOR){
            intent = new Intent(this, MainActivity.class);
            intent.putExtra(MainActivity.KEY_MODE,mode);
        }else {
            intent = new Intent(this, RecordActivity.class);
        }
        startActivity(intent);
        finish();
    }



    private void findViews() {
        menu_button_mode = findViewById(R.id.menu_button_mode);
        menu_sensor_mode = findViewById(R.id.menu_sensors_mode);
        menu_record_screen = findViewById(R.id.menu_record_screen);
    }
}
