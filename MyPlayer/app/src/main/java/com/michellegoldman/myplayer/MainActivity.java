//Michelle Goldman
//MDFIII 1601
//January 8, 2016

package com.michellegoldman.myplayer;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements ServiceConnection, PlayerFragment.MediaControls {

    private static final String LOGCAT = "MAINACTIVITY_LOG";
    public PlayerService mService;
    boolean mBound;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction().add(R.id.container, new PlayerFragment()).commit();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.v(LOGCAT, "Main Activity Fragment - startService intent - called...");
        Intent i = new Intent(this, PlayerService.class);
        bindService(i, this, Context.BIND_AUTO_CREATE);
        i.putExtra("name", "image");
        startService(i);
    }


    @Override
    protected void onStop() {
        super.onStop();
        Log.v(LOGCAT, "MainActivity onStop() called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.v(LOGCAT, "MainActivity onResume() called");
        //Bind service
        mBound = false;
        Intent i = new Intent(this, PlayerService.class);
        bindService(i, this, Context.BIND_AUTO_CREATE);
        startService(i);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.v(LOGCAT, "MainActivity onPause() called");
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        Log.v(LOGCAT, "MainActivity onSaveInstanceState() called");

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.v(LOGCAT, "MainActivity onRestoreInstanceState() called");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v(LOGCAT, "MainActivity onDestroy() called");
        //Unregister service binder
        mBound = false;
        Intent i = new Intent(this, PlayerService.class);
        stopService(i);

    }

    //DEFINE CALLBACKS FOR SERVICE BINDING
    @Override
    public void onServiceConnected(ComponentName name, IBinder iBinder) {
        Log.v(LOGCAT, "MainActivity onServiceConnected() called");
        mBound = true;
        PlayerService.PlayerBinder binder = (PlayerService.PlayerBinder)iBinder;
        mService = binder.getService();
        mService.updateTrackName();

    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {
        Log.v(LOGCAT, "MainActivity onServiceDisconnected() called");
        mBound = false;
    }


    //MEDIA PLAYBACK CONTROLS LINKED TO SERVICE
    @Override
    public void play() {
        mService.play();
    }

    @Override
    public void pause() {
        mService.pause();

    }

    @Override
    public void stop() {
        mService.stop();

    }

    @Override
    public void next() {
        mService.next();
    }

    @Override
    public void previous() {
        mService.previous();
    }

    public void shuffle() {
        mService.shuffle();
    }

    public void seekTo() {
        //mService.seekTo();
    }
/*

    public void position() {
        mService.position();
    }

    @Override
    public long getSeekBarPosition() {
        return mService.getSeekBarPosition();
    }


    public void duration() {
        mService.getTotalDuration();
    }






    */
}
