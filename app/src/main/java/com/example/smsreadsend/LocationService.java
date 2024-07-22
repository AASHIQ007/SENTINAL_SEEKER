package com.example.smsreadsend;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;

public class LocationService extends Service {
    private static final long LOCATION_INTERVAL = 500; // 5 seconds
    private Handler locationHandler;
    private Runnable locationRunnable;
    private LocationManager locationManager;

    @Override
    public void onCreate() {
        super.onCreate();
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationHandler = new Handler(Looper.getMainLooper());
        locationRunnable = new Runnable() {
            @Override
            public void run() {
                sendLocation();
                locationHandler.postDelayed(this, LOCATION_INTERVAL);
            }
        };
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        locationHandler.post(locationRunnable);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        locationHandler.removeCallbacks(locationRunnable);
        super.onDestroy();
    }

    private void sendLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if (location != null) {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            Main_page mainPage = Main_page.Instance();
            if (mainPage != null) {
                mainPage.updateLocation(latitude, longitude);
            }
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
