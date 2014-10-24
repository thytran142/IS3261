package com.example.tranthy.project;
/* This class is to handle the alert setting: such as sound, email, sms, priority...*/
import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class AlertSetting extends Activity implements AdapterView.OnItemSelectedListener {

    private PendingIntent pendingIntent;
    private AlarmManager manager;
    private ScheduleReceiver receiver = new ScheduleReceiver();
    private View background;
    private String interval;
    Spinner intervalSpinner;
    Button activate,deactivate;
    TextView alertStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alert_setting);
        background = (View) findViewById(R.id.AlertSetting);
        activate = (Button)findViewById(R.id.activation);
        deactivate = (Button)findViewById(R.id.deactivation);
        alertStatus = (TextView)findViewById(R.id.alertStatus);
        deactivate.setClickable(false);
        background.setBackgroundResource(R.drawable.background3);
        // Retrieve a PendingIntent that will perform a broadcast
        registerReceiver(receiver, new IntentFilter("Send_ALERT_MSG"));
        pendingIntent = PendingIntent.getBroadcast(this, 0, new Intent("Send_ALERT_MSG"), 0);

        //set interval spinner
        intervalSpinner = (Spinner) findViewById(R.id.interval_spinner);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this,
                R.array.interval_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        intervalSpinner.setAdapter(adapter);
        intervalSpinner.setOnItemSelectedListener(this);
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

        manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        int finalInterval = Integer.parseInt(interval) * 60000 ;
        manager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + finalInterval, finalInterval, pendingIntent);
        Toast.makeText(this, "ALERT Activated with " + finalInterval + "Interval" , Toast.LENGTH_SHORT).show();
        intervalSpinner.setEnabled(false);
        activate.setClickable(false);
        deactivate.setClickable(true);
        alertStatus.setText("ALERT STATUS: ON\nALERT INTERVAL: " + interval + " min");

    }

    public void deactivateAlert(View view) {
        if (manager != null) {
            manager.cancel(pendingIntent);
            intervalSpinner.setEnabled(true);
            activate.setClickable(true);
            deactivate.setClickable(false);
            Toast.makeText(this, "AlERT Deactivated", Toast.LENGTH_SHORT).show();
            alertStatus.setText("ALERT STATUS: OFF");
        }
    }

    public void testMail(View view) {
        AccountManager manager = AccountManager.get(this);
        Account[] accounts = manager.getAccountsByType("com.google");
        Toast.makeText(this, "There are " + accounts.length +", "+accounts[0].name, Toast.LENGTH_SHORT).show();

    }


}







