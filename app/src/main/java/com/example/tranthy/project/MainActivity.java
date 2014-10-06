package com.example.tranthy.project;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.app.Activity;
import android.view.View;
import com.google.android.gms.maps.GoogleMap;
import android.content.Intent;

public class MainActivity extends Activity {
    Intent myIntent;


    //Declare function intent here
    public void goToContactSetting(View v){
        myIntent = new Intent(this,ContactSetting.class);//start ContactSetting
        startActivity(myIntent);
    }
    public void goToMyLocation(View v){
        myIntent=new Intent(this,MyLocation.class);
        startActivity(myIntent);
    }
    public void goToCreateAccount(View v){
        myIntent=new Intent(this,CreateAccount.class);
        startActivity(myIntent);
    }
    public void goToMessageHistory(View v){
        myIntent=new Intent(this,MessageHistory.class);
        startActivity(myIntent);
    }
    public void goToMessageSetting(View v){
        myIntent = new Intent(this,MessageSetting.class);
        startActivity(myIntent);
    }
    public void goToAlertSetting(View v){
        myIntent= new Intent(this,AlertSetting.class);
        startActivity(myIntent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
