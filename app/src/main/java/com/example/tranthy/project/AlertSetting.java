package com.example.tranthy.project;
/* This class is to handle the alert setting: such as sound, email, sms, priority...*/
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.IntentFilter;
import android.os.Bundle;
import android.app.ActionBar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

public class AlertSetting extends Activity {

    private PendingIntent pendingIntent;
    private Intent AlertIntent;
    private IntentFilter AlertIntentFilter;
    private AlarmManager manager;
    private ScheduleReceiver receiver = new ScheduleReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alert_setting);

        // Retrieve a PendingIntent that will perform a broadcast
        registerReceiver(receiver, new IntentFilter("Send_ALERT_MSG") );
        pendingIntent = PendingIntent.getBroadcast(this, 0, new Intent("Send_ALERT_MSG"), 0);





    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.alert_setting_actions,menu);
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

    public void startAlert(View view) {

        manager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        int interval = 20000;
        manager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+interval, interval, pendingIntent);
        Toast.makeText(this, "ALERT SET", Toast.LENGTH_SHORT).show();

    }

    public void cancelAlert(View view) {
        if (manager != null) {
            manager.cancel(pendingIntent);
            Toast.makeText(this, "AlERT Canceled", Toast.LENGTH_SHORT).show();
        }
    }


}


