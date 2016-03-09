//Michelle Goldman
//CPMD 1215
//November 30, 2015
//Updated December 17, 2015

package com.michellegoldman.userdata;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.michellegoldman.userdata.auxiliary.AlarmReceiver;
import com.michellegoldman.userdata.auxiliary.PollService;
import com.michellegoldman.userdata.fragments.MyCoursesFragment;
import com.parse.ParseUser;

import java.util.Calendar;

public class MyCoursesActivity extends AppCompatActivity implements ServiceConnection {

    PollService mService;
    boolean mBound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_courses);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction().add(R.id.container, new MyCoursesFragment()).commit();
        }

        scheduleAlarm();


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.courses, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here.
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            logoutUser();

        } else if (id == R.id.action_add) {
            Intent intent = new Intent(this, NewCourseActivity.class);
            startActivity(intent);
        } else if (id == R.id.action_update_courses) {
            //Run the manual update data using the update courses option in action bar
            MyCoursesFragment frag = (MyCoursesFragment) getFragmentManager().findFragmentById(R.id.container);
            frag.updateList();

        }
        return false;

    }



    @Override
    public void onStart() {
        super.onStart();

        //Create polling service to update parse data
        Intent i = new Intent(this, PollService.class);
        bindService(i, this, Context.BIND_AUTO_CREATE);
        i.putExtra("courseName", "timesTaken");
        startService(i);
    }



    @Override
    protected void onResume() {
        super.onResume();
        //Register for broadcast based on ACTION string
        IntentFilter filter = new IntentFilter(PollService.ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //Unregister listener when application is paused
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }

    //Create broadcast receiver
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int resultCode = intent.getIntExtra("resultCode", RESULT_CANCELED);
            if (resultCode == RESULT_OK) {

                MyCoursesFragment frag = (MyCoursesFragment) getFragmentManager().findFragmentById(R.id.container);
                frag.updateList();
                String resultValue = intent.getStringExtra("resultValue");
                Toast.makeText(MyCoursesActivity.this, resultValue, Toast.LENGTH_SHORT).show();
            }
        }
    };


    @Override
    protected void onStop() {
        super.onStop();
        if (mBound) {
            unbindService(this);
            mBound = false;
        }
    }

    //Define callbacks for service binding
    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        PollService.PollServiceBinder binder = (PollService.PollServiceBinder) iBinder;
        mService = binder.getService();
        mBound = true;
        mService.showMessage();
        mService.pollParse();
    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {
        mBound = false;
    }


    //Logout current user
    public void logoutUser() {
        //Log user out of Parse
        ParseUser.logOut();
        //Alert user they have been logged out
        Toast.makeText(getApplicationContext(), "Logout successful.", Toast.LENGTH_SHORT).show();
        //Return to login screen
        finish();
    }

    //Set up recurring alarm to poll Parse server for updates
    public void scheduleAlarm() {
        //Construct intent to execute the alarm receiver
        Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
        //Create pending intent to be triggered when alarm goes off
        final PendingIntent pendIntent = PendingIntent.getBroadcast(this, AlarmReceiver.REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        //Set up periodic alarm every 20 seconds
        Calendar calendar = Calendar.getInstance();
        AlarmManager alarm = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        alarm.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 1000 * 20, pendIntent);

    }
}
