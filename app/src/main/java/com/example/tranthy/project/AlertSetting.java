package com.example.tranthy.project;
/* This class is to handle the alert setting: email, sms, priority...*/

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.BatteryManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class AlertSetting extends Activity implements AdapterView.OnItemSelectedListener {

    PendingIntent pendingIntent;
    AlarmManager manager;
    View background;
    String interval;
    Spinner intervalSpinner;
    Button activate,deactivate;
    TextView alertStatus;
    TextView batteryText;
    ImageView battery_image;
    ImageView status_image;
    Intent alertIntent;
    public static final String MY_ACTION = "ALERT_ACTIVATE";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alert_setting);

        background = (View) findViewById(R.id.AlertSetting);
        activate = (Button)findViewById(R.id.activation);
        deactivate = (Button)findViewById(R.id.deactivation);
        alertStatus = (TextView)findViewById(R.id.alertStatus);
        status_image = (ImageView)findViewById(R.id.status_image);
        batteryText = (TextView)findViewById(R.id.batteryStatus);
        battery_image = (ImageView)findViewById(R.id.battery_image);
        background.setBackgroundResource(R.drawable.background3);
        alertIntent = new Intent(MY_ACTION );
        manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);


        //set interval spinner
        intervalSpinner = (Spinner) findViewById(R.id.interval_spinner);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this,
                R.array.interval_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        intervalSpinner.setAdapter(adapter);
        intervalSpinner.setOnItemSelectedListener(this);

        boolean alarmUp = (PendingIntent.getBroadcast(this, 1,
                alertIntent,PendingIntent.FLAG_NO_CREATE) != null);

    if(alarmUp){
        intervalSpinner.setEnabled(false);
        activate.setClickable(false);
        alertStatus.setText("ALERT STATUS: ON");
        status_image.setBackgroundResource(R.drawable.status_on);
    }else{
        alertStatus.setText("ALERT STATUS: OFF");
        status_image.setBackgroundResource(R.drawable.status_off);
    }
        //check battery status
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = this.registerReceiver(null, ifilter);
        int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

        float batteryPct = (level / (float)scale) * 100;

        if(batteryPct<=40.0){
            battery_image.setBackgroundResource(R.drawable.warning);
            batteryText.setText("Power is not in recommended range");
        }
        else{
            battery_image.setBackgroundResource(R.drawable.ok_good);
            batteryText.setText("Power is within recommended range");
        }


    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        interval = intervalSpinner.getSelectedItem().toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.alert_setting_actions, menu);
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

    public void activateAlert(View view) {


        pendingIntent = PendingIntent.getBroadcast(this, 1, alertIntent, 0);
        int finalInterval = Integer.parseInt(interval) * 60000 ;
        manager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+finalInterval, finalInterval, pendingIntent);
        Toast.makeText(this, "ALERT Activated", Toast.LENGTH_SHORT).show();
        intervalSpinner.setEnabled(false);
        activate.setClickable(false);
        deactivate.setClickable(true);
        status_image.setBackgroundResource(R.drawable.status_on);
        alertStatus.setText("ALERT STATUS: ON");

    }

    public void deactivateAlert(View view) {

        if (manager != null) {
            pendingIntent = PendingIntent.getBroadcast(this, 1, alertIntent, 0);
            manager.cancel(pendingIntent);
            pendingIntent.cancel();
            PendingIntent.getBroadcast(this, 0, alertIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT).cancel();
            intervalSpinner.setEnabled(true);
            activate.setClickable(true);
            status_image.setBackgroundResource(R.drawable.status_off);
            Toast.makeText(this, "ALERT Deactivated", Toast.LENGTH_SHORT).show();
            alertStatus.setText("ALERT STATUS: OFF");
        }
    }



}














