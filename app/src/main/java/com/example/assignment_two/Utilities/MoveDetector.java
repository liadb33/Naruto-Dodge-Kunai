package com.example.assignment_two.Utilities;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.example.assignment_two.Interfaces.MoveCallback;

public class MoveDetector {
    private static final int RIGHT_MOVEMENT = 1;
    private static final int LEFT_MOVEMENT = -1;
    private static final int NO_MOVE = 0;

    private SensorManager sensorManager;
    private Sensor sensor;
    private SensorEventListener sensorEventListener;
    private int moveX;
    private long moveY;
    private MoveCallback moveCallback;
    private long timestamp = 0l;

    public MoveDetector(Context context,MoveCallback moveCallback){
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        this.moveCallback = moveCallback;
        initEventListener();
    }

    private void initEventListener() {
        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float x = event.values[0];
                float y = event.values[1];

                calculateMove(x,y);

            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
                //pass
            }
        };
    }

    private void calculateMove(float x, float y) {
        if(System.currentTimeMillis() - timestamp > 400){
            timestamp = System.currentTimeMillis();

            moveX = x > 4.5 ? LEFT_MOVEMENT : x < -4.5 ? RIGHT_MOVEMENT : NO_MOVE;
            moveY = y > 2.5 ? 100L : y < -2.5 ? -100L : 0;

            if (moveCallback != null) {
                moveCallback.changeMovementX();
                moveCallback.changeMovementY();
            }
        }
    }

    public void startSensor(){
        sensorManager.registerListener(
                sensorEventListener,
                sensor,
                SensorManager.SENSOR_DELAY_NORMAL
        );
    }

    public void stopSensor(){
        sensorManager.unregisterListener(
                sensorEventListener,
                sensor
        );
    }


    public int getMoveX() {
        return moveX;
    }

    public long getMoveY() {
        return moveY;
    }
}
