package com.example.tranthy.project;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;




public class LocationActivity extends Activity {

    private GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        map = ((MapFragment)getFragmentManager().findFragmentById(R.id.map)).getMap();


        Boolean locationEnabled;
        LocationManager manager = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
        if(!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)&&!manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
            locationEnabled = false;
            Toast.makeText(this,"Enable location services for accurate data",
            Toast.LENGTH_SHORT).show();
        }
        else locationEnabled = true;

        if(locationEnabled){
            LocationManager locationManager = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
            LocationListener locationListener = new LocationListener(){
                public void onLocationChanged(Location location) {
                    LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                    //Marker currentLoc = map.addMarker(new MarkerOptions().position(latLng).title("Current Location")
                            //.snippet("Here you are").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher)));
                    Toast.makeText(getBaseContext(), "This is your location - " + location.getLatitude() + ", " + location.getLongitude(), Toast.LENGTH_LONG).show();
                    //map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 30));
                    //map.animateCamera(CameraUpdateFactory.zoomTo(30), 2000, null);
                }
                public void onStatusChanged(String provider,int status,Bundle extras){}
                public void onProviderEnabled(String provider){}
                public void onProviderDisabled(String provider){}
                };
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,0,locationListener);
            }

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.location, menu);
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
}
