package com.example.assignment_two.App;

import android.app.Application;

import com.example.assignment_two.Utilities.ImageLoader;
import com.example.assignment_two.Utilities.LocationManager;
import com.example.assignment_two.Utilities.SharedPreferencesManagerV3;
import com.example.assignment_two.Utilities.SignalManager;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        LocationManager.init(this,null);
        SharedPreferencesManagerV3.init(this);
        SignalManager.init(this);
        ImageLoader.initImageLoader(this);
    }
}
