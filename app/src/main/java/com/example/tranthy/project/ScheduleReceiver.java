package com.example.tranthy.project;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by Shake on 16/10/2014.
 */
public class ScheduleReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent i) {
        // For our recurring task, we'll just display a message
        Toast.makeText(context,"Received Broadcast in ScheduledReceiver,"+ " value received in Schedule Receiver:" + i.getStringExtra("key"), Toast.LENGTH_LONG).show();

    }



}
