package com.example.assignment_two.Activities;

import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.assignment_two.Fragments.ListFragment;
import com.example.assignment_two.Fragments.MapFragment;
import com.example.assignment_two.Interfaces.MapZoomCallback;
import com.example.assignment_two.R;

public class RecordActivity extends AppCompatActivity {
    private FrameLayout record_FRAME_list;
    private FrameLayout record_FRAME_map;
    private ListFragment listFragment;
    private MapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);


        findViews();
        initViews();
    }

    private void initViews() {
        listFragment = new ListFragment(new MapZoomCallback() {
            @Override
            public void onClickZoomListener(double latitude, double longitude) {
                mapFragment.onMapZoom(latitude,longitude);
            }
        });
        getSupportFragmentManager().beginTransaction().add(R.id.record_FRAME_list,listFragment).commit();
        mapFragment = new MapFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.record_FRAME_map,mapFragment).commit();

    }

    private void findViews() {
        record_FRAME_list = findViewById(R.id.record_FRAME_list);
        record_FRAME_map = findViewById(R.id.record_FRAME_map);
    }
}