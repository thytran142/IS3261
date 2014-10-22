package com.example.tranthy.project;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.widget.Toast;

/**
 * Created by Shake on 16/10/2014.
 */
public class ScheduleReceiver extends BroadcastReceiver {
    private LocationManager locManager;

    @Override
    public void onReceive(Context context, Intent i) {
        // For our recurring task, we'll just display a message
        Toast.makeText(context, "Received Broadcast in ScheduledReceiver", Toast.LENGTH_LONG).show();
        Intent locService = new Intent(context, LocationService.class);
        context.startService(locService);
    }







}
