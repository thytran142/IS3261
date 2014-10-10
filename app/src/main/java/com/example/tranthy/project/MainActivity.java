package com.example.tranthy.project;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
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
import android.widget.Toast;
import android.media.AudioManager;
import android.media.MediaPlayer;

public class MainActivity extends Activity {
    Intent myIntent;
    private MediaPlayer mediaPlayer;

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
        if(mediaPlayer.isPlaying()){mediaPlayer.pause();}
        else {
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
    public void goToMessageSetting(View v){
        myIntent = new Intent(this,MessageSetting.class);
        startActivity(myIntent);
    }
    public void goToAlertSetting(View v){
        myIntent= new Intent(this,AlertSetting.class);
        startActivity(myIntent);
    }
    public void goToDangerAlarm(View v){
        //This function is to flash in flash out
        //To send mass messages
        //To make alarm sound
        Toast.makeText(this,"You just pressed the dangerous button ",Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mediaPlayer = MediaPlayer.create(this, R.raw.alarm_danger);



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
