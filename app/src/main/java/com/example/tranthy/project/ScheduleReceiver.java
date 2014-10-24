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


    @Override
    public void onReceive(Context context, Intent i) {
        // this will trigger the service
        Intent locService = new Intent(context, LocationService.class);
        context.startService(locService);
    }







}
