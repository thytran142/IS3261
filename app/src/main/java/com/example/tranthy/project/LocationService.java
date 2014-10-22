package com.example.tranthy.project;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by Shake on 22/10/2014.
 */
public class LocationService extends IntentService {

    Double longitude;
    Double latitude;
    String addressText="";
    String lat=null;
    String lon=null;
    private LocationManager locManager;
    LocationListener locListener;
    Location location;
    NotificationManager notificationManager;
    Notification myNotification;
    public LocationService() {
        super("LocationService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        locManager=(LocationManager)this.getSystemService(Context.LOCATION_SERVICE);


        locListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                longitude = location.getLongitude();
                latitude = location.getLatitude();
            }
            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {}

            @Override
            public void onProviderEnabled(String s) {}

            @Override
            public void onProviderDisabled(String s) {}
        };

            locManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locListener);
            location = locManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);


        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        // Create a list to contain the result address
        List<Address> addresses = null;
        try {
            //get 3 addresses
            addresses = geocoder.getFromLocation(location.getLatitude(),
                    location.getLongitude(), 3);
        } catch (IOException e1) {
            Log.e("LocationSampleActivity",
                    "IO Exception in getFromLocation()");
            e1.printStackTrace();

        } catch (IllegalArgumentException e2) {
            // Error message to post in the log
            String errorString = "Illegal arguments " +
                    Double.toString(location.getLatitude()) +
                    " , " +
                    Double.toString(location.getLongitude()) +
                    " passed to address service";
            Log.e("LocationSampleActivity", errorString);
            e2.printStackTrace();
        }

        // If the reverse geocode returned an address
        if (addresses != null && addresses.size() > 0) {
            // Get the first address

            Address address = addresses.get(0);

            addressText = String.format(
                    "%s, %s, %s",
                    // If there's a street address, add it
                    address.getMaxAddressLineIndex() > 0 ?
                            address.getAddressLine(0) : "",
                    // Locality is usually a city
                    address.getLocality(),
                    // The country of the address
                    address.getCountryName());


        }


        myNotification = new NotificationCompat.Builder(getApplicationContext())
                .setContentTitle(addressText)
                .setContentText(location.getLongitude() +", "+location.getLatitude())
                .setTicker("Notification!")
                .setWhen(System.currentTimeMillis())
                .setDefaults(Notification.DEFAULT_SOUND)
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.ic_launcher)
                .build();

        notificationManager.notify(1,myNotification);


        //Toast.makeText(this, "Service Started by Broadcast receiver - " + addressText, Toast.LENGTH_LONG).show();
    }
}
