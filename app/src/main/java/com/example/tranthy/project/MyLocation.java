package com.example.tranthy.project;
/*This class is finished. However, how to output the string of the user' location, and when user turn off, needs to pass the data
to the activity main so they will inform the contact list of user */
import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.app.ActionBar;
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
import android.widget.Toast;

import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
public class MyLocation extends Activity {
    private GoogleMap map;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_location);

        //declare the map part
        //find the fragment which brings id map to contain the map
        map=((MapFragment)getFragmentManager().findFragmentById(R.id.map)).getMap();
        //Android API allows a way to check if the user has disabled location services
        Boolean locationEnabled;
        LocationManager manager=(LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
        if(!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)&& !manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
            locationEnabled=false;
            Toast.makeText(this,"Enable location services for accurate data",Toast.LENGTH_SHORT).show();
        }
        else locationEnabled=true;
        if(locationEnabled){
            LocationManager locationManager=(LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
            //Defines a listener that responds to location updates
            LocationListener locationListener=new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    LatLng latLng=new LatLng(location.getLatitude(),location.getLongitude());
                    Marker currentLoc=map.addMarker(new MarkerOptions().position(latLng).title("Current Location").snippet("This is where you are right now").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_action_place)));
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,30));
                    map.animateCamera(CameraUpdateFactory.zoomTo(30),2000,null);
                }

                @Override
                public void onStatusChanged(String s, int i, Bundle bundle) {}

                @Override
                public void onProviderEnabled(String s) {}

                @Override
                public void onProviderDisabled(String s) {}
            };
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,0,locationListener);
        }//end locationEnable
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
}


