package com.example.tranthy.project;
/*This class is finished. However, how to output the string of the user' location, and when user turn off, needs to pass the data
to the activity main so they will inform the contact list of user */
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.app.ActionBar;
import android.telephony.SmsManager;
import android.text.format.Time;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.content.Intent;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;

import android.location.LocationManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MyLocation extends Activity {
    private GoogleMap map;
    TextView current_latitude;
    TextView current_longitude;
    TextView address_result;

    Button getButton;
    private Time today;
    private String addressText = null;
    private Boolean locationEnabled;

    private ArrayList<String> stringList = new ArrayList<String>();

    private ProgressDialog progressDialog;
    private LocationManager locationManager;
    private LocationListener locationListener;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_location);
        current_latitude = (TextView)findViewById(R.id.current_latitude);
        current_longitude = (TextView)findViewById(R.id.current_longitude);
        address_result = (TextView)findViewById(R.id.address_result);
        getButton = (Button)findViewById(R.id.getButton);

        LocationManager manager=(LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
        if(!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)&& !manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
            locationEnabled=false;
            Toast.makeText(this,"Enable location services for accurate data",Toast.LENGTH_SHORT).show();
        }
        else locationEnabled=true;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.my_location_actions,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);

    }
    public void sendSMS(View view){

        if(addressText != null) {
            SmsManager sms = SmsManager.getDefault();
            sms.sendTextMessage("90104024",
                    null, "Sending SMS to you Programmatically! " + addressText + " - " + today.toString().substring(0, 13), null, null);
        }
        else{Toast.makeText(this, "no address how to send sia", Toast.LENGTH_LONG).show();}
    }

    public void getCurrentLocation(View view){

        requestLocation();

        today = new Time(Time.getCurrentTimezone());
        today.setToNow();
        Toast.makeText(this, today.toString().substring(0,13), Toast.LENGTH_LONG).show();




    }

    public void requestLocation(){

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Retrieving Location Information");
        progressDialog.show();

        locationManager=(LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
        //Defines a listener that responds to location updates
        locationListener=new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                Double lat = location.getLatitude();
                Double lon = location.getLongitude();

                current_latitude.setText(lat.toString());
                current_longitude.setText(lon.toString());


                Geocoder geocoder = new Geocoder(getApplicationContext(),Locale.getDefault());
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
                    address_result.setText(addressText);
                    progressDialog.hide();
                    locationManager.removeUpdates(locationListener);
                }

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


}


