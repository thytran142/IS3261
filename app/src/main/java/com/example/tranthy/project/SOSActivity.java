package com.example.tranthy.project;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.telephony.SmsManager;
import android.text.format.Time;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class SOSActivity extends Activity {
    CountDownTimer cdt;
    AlertDialog alert;
    contact_list cdb;
    MessageDB mdb;
    String addressText;
    String shortMessage;
    String[] receiver;
    Time today;
    final String EAMessage = "Emergency Alert!, Please contact/find me ASAP!";
    private LocationManager locationManager;
    private LocationListener locationListener;
    GMailSender sender;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sos);
        today = new Time(Time.getCurrentTimezone());
        final Intent i = new Intent(this, MainActivity.class);
        requestLocation();
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Emergency Alert trigger in");
        alertDialog.setMessage("00:10");
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
                cdt.cancel();
                locationManager.removeUpdates(locationListener);
                finish();
                startActivity(i);
            }
        });
        alert = alertDialog.create();
        alert.show();


        cdt = new CountDownTimer(11000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                alert.setMessage("00:"+ ((millisUntilFinished/1000)));
            }

            @Override
            public void onFinish() {
                alert.hide();
                mdb.open();
                ArrayList<String[]> receivers = new ArrayList<String[]>();
                try{
                    receivers=getAllReceivers();
                }catch(SQLException e){
                    e.printStackTrace();
                }
                for(int x = 0;x<receivers.size();x++) {
                    Log.e("Size of detected receivers", "" + receivers.size());
                    receiver = receivers.get(x);
                    today.setToNow();
                    sendBySMS(receiver[1],receiver[2],today);
                    sendMailTask smt = new sendMailTask();
                    smt.execute();










                    locationManager.removeUpdates(locationListener);
                    mdb.close();
                }



            }
        }.start();



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.so, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
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

    public void sendBySMS(String name,String number ,Time time){
        try {

            SmsManager sms = SmsManager.getDefault();
            sms.sendTextMessage(number,null, EAMessage+", "+shortMessage+", "+addressText, null, null);
            String success = "Emergency SMS Sent";
            long id = mdb.insertMsgHistory(name, success+"\n"+shortMessage+"\n"+addressText, time.toString().substring(0, 8) + "\n" + time.toString().substring(9, 13)) ;



        } catch (Exception e) {
            //if sms fail to send
            String error = "SMS Failed";
            //log into db
            long id = mdb.insertMsgHistory(name, error+"\n"+shortMessage+"\n"+addressText, time.toString().substring(0, 8) + "\n" + time.toString().substring(9, 13)) ;
            Log.e("SMS FAIL", e.toString());
        }
    }

    public void sendByEMAIL(String name, String email, Time time){
        try {
            sender = new GMailSender();
            sender.sendMail(EAMessage+"\n"+shortMessage+"\n"+addressText,email);
            String success = "EMAIL Sent";
            long id = mdb.insertMsgHistory(name,success+"\n"+shortMessage+"\n"+addressText,time.toString().substring(0, 8)+"\n"+time.toString().substring(9, 13)) ;

        } catch (Exception e) {
            //if fail to send
            String error = "EMAIL Failed";
            //log into db
            long id = mdb.insertMsgHistory(name,error+"\n"+shortMessage+"\n"+addressText,time.toString().substring(0, 8)+"\n"+time.toString().substring(9, 13)) ;
            Log.e("EMAIL FAIL", e.toString());
        }

    }

    public void requestLocation(){
        //progressDialog to disable view interaction when retrieving

        locationManager=(LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
        //Defines a listener that responds to location updates
        locationListener=new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

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

                shortMessage = "Lati:"+ location.getLatitude()+ " Long:" + location.getLongitude();

            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {}

            @Override
            public void onProviderEnabled(String s) {}

            @Override
            public void onProviderDisabled(String s) {}
        };
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,0,locationListener);

    }

    class sendMailTask extends AsyncTask<Void, Void, Void> {

        sendMailTask() {}

        @Override
        protected Void doInBackground(Void... arg0) {
            today.setToNow();
            sendByEMAIL(receiver[1],receiver[3],today);
            return null;
        }

    }
}






