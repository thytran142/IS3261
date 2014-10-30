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
import android.os.AsyncTask;
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
    String message;
    String shortMessage;
    String smsMessage;
    SmsManager sms;
    GMailSender sender;
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
        sms = SmsManager.getDefault();
        notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
//-------------notification builder--------------------------/
        myNotification = new NotificationCompat.Builder(getApplicationContext())
                .setContentTitle("Alert Sent!")
                .setContentText("Alert has been send out")
                .setTicker("Notification!")
                .setWhen(System.currentTimeMillis())
                .setDefaults(Notification.DEFAULT_SOUND)
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.main_icon)
                .build();

        failNotification = new NotificationCompat.Builder(getApplicationContext())
                .setContentTitle("Alert Sending Failed")
                .setContentText("Alert Failed")
                .setTicker("Notification!")
                .setWhen(System.currentTimeMillis())
                .setDefaults(Notification.DEFAULT_SOUND)
                .setAutoCancel(true)
                .setSmallIcon(android.R.drawable.ic_dialog_alert)
                .build();

//-------------------------------------------------------------------------------------------------//
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
        //set default message to be sent after getting the info
        message = "This a auto message sent by LocateMi to notify you the sender Last Known Location: \n" + addressText +"\n"+ "Lati:"
                + location.getLatitude()+ ",Long:" + location.getLongitude();

        shortMessage = "Lati:"+ location.getLatitude()+ " Long:" + location.getLongitude();
        smsMessage = addressText +" "+ shortMessage;

        //Obtain all the receivers info
        ArrayList<String[]> receivers = new ArrayList<String[]>();
        try{
            receivers=getAllReceivers();
        }catch(SQLException e){
            e.printStackTrace();
        }
        //for loop for operation
        for(int x = 0;x<receivers.size();x++){
            Log.e("Size of detected receivers",""+receivers.size());
            String[] receiver = receivers.get(x);
            String combine = receiver[1]+"\n"+receiver[2];
            Time today = new Time(Time.getCurrentTimezone());
            today.setToNow();
            mdb.open();

            if(receiver[4].equals("BOTH")){
                sendByEMAIL(receiver[1],receiver[3],receiver[5],today);
                sendBySMS(receiver[1],receiver[2],receiver[5],today);
            }
            else if(receiver[4].equals("SMS")){
                sendBySMS(receiver[1],receiver[2],receiver[5],today);
            }
            else if(receiver[4].equals("EMAIL")) {
                sendByEMAIL(receiver[1], receiver[3], receiver[5], today);
            }
        }
        mdb.close();
        locManager.removeUpdates(locListener);
        notificationManager.notify(1,myNotification);
    }

    public void sendBySMS(String name,String number, String addition,Time time){
            try {
                SmsManager sms = SmsManager.getDefault();
                sms.sendTextMessage(number,null, smsMessage+"\n"+addition, null, null);
                String success = "SMS Sent";
                long id = mdb.insertMsgHistory(name, success+"\n"+shortMessage+"\n"+addressText, time.toString().substring(0, 8) + " " + time.toString().substring(9, 13)) ;
            } catch (Exception e) {
                notificationManager.notify(1,failNotification);
                //if sms fail to send
                String error = "SMS Failed";
                //log into db
                long id = mdb.insertMsgHistory(name, error+"\n"+shortMessage+"\n"+addressText, time.toString().substring(0, 8) + " " + time.toString().substring(9, 13)) ;
                Log.e("SMS FAIL", e.toString());
            }
    }

    public void sendByEMAIL(String name, String email, String addition, Time time){
        try {
            sender = new GMailSender();
            sender.sendMail(message+"\n"+addition,email);
            String success = "EMAIL Sent";
            long id = mdb.insertMsgHistory(name,success+"\n"+shortMessage+"\n"+addressText,time.toString().substring(0, 8)+" "+time.toString().substring(9, 13)) ;
        } catch (Exception e) {
            notificationManager.notify(1,failNotification);
            //if fail to send
            String error = "EMAIL Failed";
            //log into db
            long id = mdb.insertMsgHistory(name,error+"\n"+shortMessage+"\n"+addressText,time.toString().substring(0, 8)+" "+time.toString().substring(9, 13)) ;
            Log.e("EMAIL FAIL", e.toString());
        }
    }

    public ArrayList<String[]> getAllReceivers() throws SQLException {
        cdb.open();
        Cursor c = cdb.getAllContacts();
        ArrayList<String[]> contacts = new ArrayList<String[]>();
        if (c.moveToFirst()) {
            do {
                String[] contact = {c.getString(0), c.getString(1), c.getString(2),c.getString(3),c.getString(4),c.getString(5)};
                contacts.add(contact);
            } while (c.moveToNext());
        }
        cdb.close();
        return contacts;
    }


}




