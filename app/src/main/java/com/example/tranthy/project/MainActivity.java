package com.example.tranthy.project;


import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.media.AudioManager;
import android.media.MediaPlayer;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends FragmentActivity
        implements Help.HelpInterface{
    Intent myIntent;
    boolean flashOn;
    private MediaPlayer mediaPlayer;
    Camera cam;
    Parameters p;
    flashOnTask onTask;
    flashOffTask offTask;
    Timer oneTimer;
    Timer twoTimer;
    //Declare function intent here
    public void goToContactSetting(View v){
        myIntent = new Intent(this,ContactSetting.class);//start ContactSetting
        startActivity(myIntent);
    }
    public void goToMyLocation(View v){
        myIntent=new Intent(this,MyLocation.class);
        startActivity(myIntent);
    }
    public void goToAlarmSound(View v){
        //check availability of the flashlight
        boolean fl = this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
        if(fl){
                Log.e("flashlight", "available");}
        else{Log.e(" no flashlight", "null");}

        //toggle flashlight
        if(flashOn){
            oneTimer.cancel();
            twoTimer.cancel();
            cam.stopPreview();
            cam.release();
            flashOn = false;
        }else{flashlight();
            Log.e("flashlight", "start");}

        //toggle alarm sound
        if(mediaPlayer.isPlaying()){
            mediaPlayer.pause();

        }else {
            AudioManager audio = (AudioManager) this.getSystemService(this.AUDIO_SERVICE);
            int max = audio.getStreamMaxVolume(AudioManager.STREAM_NOTIFICATION);
            audio.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
            audio.setStreamVolume(AudioManager.STREAM_RING, max, AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
            mediaPlayer.start();
            mediaPlayer.setLooping(true);
        }

    }
    public void goToMessageHistory(View v){
        myIntent=new Intent(this,MessageHistory.class);
        startActivity(myIntent);
    }
    public void goToEmergencyContact(View v){
        myIntent = new Intent(this,EmergencyContact.class);
        startActivity(myIntent);
    }
    public void goToAlertSetting(View v){
        myIntent= new Intent(this,AlertSetting.class);
        startActivity(myIntent);
    }
    public void goToHelp(View v){
        FragmentManager fragmentManager=getSupportFragmentManager();
        Help help= new Help();
        help.setDialogTitle("Help");
        help.setCancelable(false);
        help.show(getFragmentManager(),"dialog");
    }
    public void goToDangerAlarm(View v){
        myIntent = new Intent(this, SOSActivity.class);
        startActivity(myIntent);
    }
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mediaPlayer = MediaPlayer.create(this, R.raw.alarm_danger);
        onTask = new flashOnTask();
        offTask = new flashOffTask();
        oneTimer = new Timer();
        twoTimer = new Timer();
        flashOn = false;
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

    public void flashlight(){
        flashOn = true;
        oneTimer.schedule(onTask, 1000, 2000);
        twoTimer.schedule(offTask, 2000, 2000);
    }

    class flashOnTask extends TimerTask {
        public void run() {
            cam = Camera.open();
            p = cam.getParameters();
            p.setFlashMode(Parameters.FLASH_MODE_TORCH);
            cam.setParameters(p);
            cam.startPreview();
        }
    }

    class flashOffTask extends TimerTask {
        public void run() {
            cam.stopPreview();
            cam.release();
        }
    }


}
