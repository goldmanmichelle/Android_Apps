//Michelle Goldman
//CPMD 1215
//November 30, 2015
//Updated December 17, 2015

package com.michellegoldman.userdata.auxiliary;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver {

    public static final int REQUEST_CODE = 101;
    public static final String ACTION = "com.michellegoldman.userdata.alarm";

    //Triggeres by the alarm set to periodically run in MyCoursesActivity
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, PollService.class);
        i.putExtra("courseName", "timesTaken");
        context.startService(i);
    }
}
