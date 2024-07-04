package com.example.assignment_two.Utilities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.example.assignment_two.Interfaces.LocationUpdateCallback;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class LocationManager {


    private static volatile LocationManager instance = null;
    private Context context;
    private FusedLocationProviderClient fusedLocationClient;
    private LocationCallback locationCallback;
    private double latitude;
    private double longitude;

    private LocationUpdateCallback locationUpdateCallback;

    public LocationManager(Context context, LocationUpdateCallback locationUpdateCallback) {
        this.context = context;
        this.locationUpdateCallback = locationUpdateCallback;
        this.fusedLocationClient = LocationServices.getFusedLocationProviderClient(context);

    }

    public static LocationManager getInstance(){
        return  instance;
    }

    public static LocationManager init(Context context, LocationUpdateCallback locationUpdateCallback){
        if(instance == null){
            synchronized (LocationManager.class){
                if(instance == null)
                    instance = new LocationManager(context,locationUpdateCallback);
            }
        }
        if(locationUpdateCallback != null)
            instance.setLocationUpdateCallback(locationUpdateCallback);

        return getInstance();
    }

    public void setLocationUpdateCallback(LocationUpdateCallback callback) {
        this.locationUpdateCallback = callback;
    }

    public void getDeviceLocation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                LocationRequest locationRequest = LocationRequest.create();
                locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                locationRequest.setInterval(10000); // 10 seconds
                locationRequest.setFastestInterval(5000); // 5 seconds

                locationCallback = new LocationCallback() {
                    @Override
                    public void onLocationResult(@NonNull LocationResult locationResult) {
                        if (locationResult != null && locationResult.getLocations().size() > 0) {
                            int index = locationResult.getLocations().size() - 1;
                            latitude = locationResult.getLocations().get(index).getLatitude();
                            longitude = locationResult.getLocations().get(index).getLongitude();
                            Log.d("location", "onLocationResult: lat:" + latitude + " lon:" + longitude);

                            if (locationUpdateCallback != null) {
                                locationUpdateCallback.onLocationUpdated(latitude, longitude);
                            }
                            stopLocationUpdates();
                        }
                    }
                };

                fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
            } else {
                requestLocationPermission(context);
            }
        }
    }

    public void requestLocationPermission(Context context){
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions((Activity) context,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
            Log.e("LocationManager", "Location permission not granted");
        }
    }

    public String getAddress(Context context, double lat, double lon) {
        String fullAdd = null;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            try {

                Geocoder geocoder = new Geocoder(context, Locale.getDefault());
                List<Address> addressList = geocoder.getFromLocation(lat, lon, 1);

                if (addressList.size() > 0 && addressList != null) {
                    Address address = addressList.get(0);
                    fullAdd = address.getLocality() + ", " + address.getCountryName();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }
        return fullAdd;
    }


    public void stopLocationUpdates() {
        if (fusedLocationClient != null && locationCallback != null) {
            fusedLocationClient.removeLocationUpdates(locationCallback);
        }
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

}
