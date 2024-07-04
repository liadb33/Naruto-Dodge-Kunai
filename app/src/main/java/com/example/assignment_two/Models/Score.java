package com.example.assignment_two.Models;

import com.example.assignment_two.Utilities.TimeFormatter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Score {

    private double latitude;
    private double longitude;

    private long timeStamp;
    private String address;
    private int value;


    public Score(int value, double latitude,double longitude,String address){
        this.latitude = latitude;
        this.longitude = longitude;
        this.value = value;
        this.address = address;
        timeStamp = System.currentTimeMillis();
    }


    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getFormattedTime(){
        return TimeFormatter.formatTimestamp(timeStamp);
    }

    public String getAddress() {
        return address;
    }
}
