//Michelle Goldman
//CPMD 1215
//November 30, 2015
//Updated December 17, 2015


package com.michellegoldman.userdata.auxiliary;

import android.app.Activity;
import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import com.michellegoldman.userdata.data.CourseAdapter;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;


public class PollService extends IntentService {

    public static final String TAG = "PollService";
    public static final String ACTION = "com.michellegoldman.userdata.PollService";


    protected List<ParseObject> mCourse;
    private CourseAdapter adapter;

    public PollService() {
        super("test-service");
    }

    public class PollServiceBinder extends Binder {
        public PollService getService() {
            return PollService.this;
        }
    }
    @Override
    public void onCreate() {
        super.onCreate();

        Log.i(TAG, "Poll service created.");


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //return super.onStartCommand(intent, flags, startId);
        Log.i(TAG, "Poll service started");
        pollParse();
        return Service.START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "Poll service binding.");
        return new PollServiceBinder();
        //return null;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.i(TAG, "Poll service running." );
        String courseValue = intent.getStringExtra("courseName");
        String timesValue = intent.getStringExtra("timesTaken");
        Intent i = new Intent(ACTION);
        i.putExtra("resultCode", Activity.RESULT_OK);
        i.putExtra("resultValue", "Updated data: " + courseValue + timesValue);
        LocalBroadcastManager.getInstance(this).sendBroadcast(i);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "Poll service ended");

    }

    public void showMessage() {
        Toast.makeText(this, "PollService message...", Toast.LENGTH_SHORT).show();
    }


    //Update course list
    public void pollParse() {

        //Load currentUser's courses from Parse
        final ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Courses");
        query.orderByDescending("createdAt");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> course, ParseException e) {
                if (e == null) {
                    Toast.makeText(getApplicationContext(), "Pulling data from Parse...", Toast.LENGTH_SHORT).show();

                    mCourse = course;
                    adapter = new CourseAdapter(getApplicationContext(), mCourse);
                    //setListAdapter(adapter);

                } else {
                    Toast.makeText(getApplicationContext(), "" + e, Toast.LENGTH_SHORT).show();
                }
            }
        });


    }



}
