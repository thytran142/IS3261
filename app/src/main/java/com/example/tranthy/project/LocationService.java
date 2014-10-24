package com.example.tranthy.project;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.telephony.SmsManager;
import android.text.format.Time;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Shake on 22/10/2014.
 */
public class LocationService extends IntentService {

    MessageDB mdb;
    contact_list cdb;
    Double longitude;
    Double latitude;
    String addressText="";
    private LocationManager locManager;
    LocationListener locListener;
    Location location;
    NotificationManager notificationManager;
    Notification myNotification, failNotification;
    public LocationService() {
        super("LocationService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mdb = new MessageDB(this);
        cdb = new contact_list(this);
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

        //set notification to user about the sms activity
        myNotification = new NotificationCompat.Builder(getApplicationContext())
                .setContentTitle("Msg Sent!")
                .setContentText("Last Known Location has been send out")
                .setTicker("Notification!")
                .setWhen(System.currentTimeMillis())
                .setDefaults(Notification.DEFAULT_SOUND)
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.main_icon)
                .build();
        String message = "Last Known Location: " + addressText +"\n"+ "Lati:"
                + location.getLatitude()+ ",Long:" + location.getLongitude();
        addMessage(message);
        notificationManager.notify(1,myNotification);

    }

    public void addMessage(String message){
        //get the selected contact in db
        mdb.open();
        ArrayList<String[]> receivers = new ArrayList<String[]>();
        try{
            receivers=getAllReceivers();
        }catch(SQLException e){
            e.printStackTrace();
        }
        //send sms to contact respectively with loop
        for(int x = 0;x<receivers.size();x++){
            String[] receiver = receivers.get(x);
            String combine = receiver[1] + ": "+receiver[2];
            Time today = new Time(Time.getCurrentTimezone());
            today.setToNow();


            try {
                //-------disable for now to prevent unintentional activation-------------------------//
                //SmsManager sms = SmsManager.getDefault();
                //sms.sendTextMessage(receiver[2],null, "This a auto message sent by LocateMi to notify you the sender \n"+message, null, null);
                //log into message history
                //-----------------------------------------------------------------------------------//
                String success = "SMS successfully sent";
                long id = mdb.insertMsgHistory(combine,success,today.toString().substring(0, 8)+"\n"+today.toString().substring(9, 13)) ;

            } catch (Exception e) {
                //if sms fail to send
                String error = "SMS failed to send out";
                //log into db
                long id = mdb.insertMsgHistory(combine,error,today.toString().substring(0, 8)+"\n"+today.toString().substring(9, 13)) ;
                failNotification = new NotificationCompat.Builder(getApplicationContext())
                        .setContentTitle("Sending Failed")
                        .setContentText("SMS Failed")
                        .setTicker("Notification!")
                        .setWhen(System.currentTimeMillis())
                        .setDefaults(Notification.DEFAULT_SOUND)
                        .setAutoCancel(true)
                        .setSmallIcon(android.R.drawable.ic_dialog_alert)
                        .build();
                notificationManager.notify(1,failNotification);
                e.printStackTrace();
            }


        }

        mdb.close();

    }

    public ArrayList<String[]> getAllReceivers() throws SQLException {
        cdb.open();
        Cursor c = cdb.getAllContacts();
        ArrayList<String[]> contacts = new ArrayList<String[]>();
        if (c.moveToFirst()) {
            do {
                String[] contact = {c.getString(0), c.getString(1), c.getString(2),c.getString(3)};
                contacts.add(contact);
            } while (c.moveToNext());

        }
        cdb.close();
        return contacts;
    }
}
