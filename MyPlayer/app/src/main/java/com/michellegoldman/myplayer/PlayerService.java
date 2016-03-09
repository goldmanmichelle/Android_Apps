//Michelle Goldman
//MDFIII 1601
//January 8, 2016

package com.michellegoldman.myplayer;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.SeekBar;

import java.io.IOException;
import java.util.Random;

/**
 * Created by mgoldman on 1/5/16.
 */
public class PlayerService extends Service implements  MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener, SeekBar.OnSeekBarChangeListener{

    private static final String LOGCAT = "PLAYERSERVICE_LOG";
    public static final String ACTION = "com.michellegoldman.myplayer.PlayerService";
    final int STANDARD_NOTIFICATION_ID = 1;
    private final String URIBase = "android.resource://com.michellegoldman.myplayer/";
    NotificationManager mNotificationManager;

    public int state; //Idle = 0, Prepared = 1, Started = 2, Paused = 3, Stopped = 4
    public String trackName;
    public String newTime;
    public int albumImage;
    public static MediaPlayer mMediaPlayer;
    public int[] albumArt = new int[6];
    public int[] tracks = new int[6];
    public int currentTrack = 0;
    public SeekBar seekBar;
    public Handler seekHandler = new Handler();
    public double elapsedTime = 0;
    public double endTime = 0;
    private long totalDuration;
    public int seekPosition;
    public boolean isShuffling = false;




    @Override
    public void onCreate() {
        super.onCreate();
        Log.v(LOGCAT, "Service onCreate() called.");

        //startTime = mMediaPlayer.getDuration();
        //endTime = mMediaPlayer.getCurrentPosition();

        //seekPosition = 0;
        //Create Audio Tracks from Raw Resources

        tracks[0] = R.raw.floaton;
        tracks[1] = R.raw.heaven;
        tracks[2] = R.raw.memory;
        tracks[3] = R.raw.missu;
        tracks[4] = R.raw.nightmare;
        tracks[5] = R.raw.tragedies;

        //Create album art for each track

        albumArt[0] = R.drawable.floaton;
        albumArt[1] = R.drawable.heaven;
        albumArt[2] = R.drawable.memory;
        albumArt[3] = R.drawable.missu;
        albumArt[4] = R.drawable.nightmare;
        albumArt[5] = R.drawable.tragedies;

        state = 0;//Idle State
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setOnPreparedListener(this);
        mMediaPlayer.setOnCompletionListener(this);

        //seekBar.setOnSeekBarChangeListener(this);

        initMediaPlayer();
        //endTime = mMediaPlayer.getDuration();
        //seekBar.setMax((int) endTime);

        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
/*
        new Thread (new Runnable) {
            @Override
            public void run() {
                int currentPosition = 0;
                while (true) {
                    try {
                        Thread.sleep(500);
                        currentPosition = getCurrentPosition();
                        if (mMediaPlayer != null) {
                            seekBar.setProgress(mMediaPlayer.getCurrentPosition());
                            Log.d(LOGCAT, "update seekBar from FRAGMENT");
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        return;
                    } catch (Exception e) {
                        return;
                    }

                    elapsedTime = mMediaPlayer.getCurrentPosition();
                    seekBar.setProgress((int) elapsedTime);
                    seekBar.setProgress(currentPosition);
                }
            }
        };
*/
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.v(LOGCAT, "Service onStartCommand() called.");
        updateTrackName();
        createNotification();

        return Service.START_NOT_STICKY;
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.v(LOGCAT, "Service onBind() called.");
        updateTrackName();
        setTrackTitle();

        return new PlayerBinder();
    }




    //MEDIAPLAYER - INITIALIZED STATE
    private void initMediaPlayer() {
        Log.v(LOGCAT, "Service initMediaPlayer() called.");
        mMediaPlayer.reset();
        state = 1;//Prepared State
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        try {
            mMediaPlayer.setDataSource(getApplicationContext(), Uri.parse(URIBase + tracks[currentTrack]));

        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            mMediaPlayer.prepareAsync();

        } catch(IllegalStateException e) {

        }

        setTrackTitle();
        updateTrackName();
        createNotification();
    }


    //SET TRACK TITLE
    public String setTrackTitle() {
        Log.v(LOGCAT, "Service setTrackTitle() " + trackName + albumImage + "called from SERVICE.");

        if (currentTrack == 0) {
            trackName = ("Float On");
            albumImage = R.drawable.floaton;
        } else if (currentTrack == 1) {
            trackName = ("Heaven Help Us");
            albumImage = R.drawable.heaven;
        } else if (currentTrack == 2) {
            trackName = ("Making A Memory");
            albumImage = R.drawable.memory;
        } else if (currentTrack == 3) {
            trackName = ("I Miss You");
            albumImage = R.drawable.missu;
        } else if (currentTrack == 4) {
            trackName = ("All American Nightmare");
            albumImage = R.drawable.nightmare;
        } else if (currentTrack == 5) {
            trackName = ("I Write Sins Not Tragedies");
            albumImage = R.drawable.tragedies;
        }
        return trackName;
    }

    //UPDATE SONG TRACK NAME
    public void updateTrackName() {
        Log.v(LOGCAT, "Service updateTrackName() called from SERVICE.");

        //update track name in fragment textfield
        if(mMediaPlayer.isPlaying()){
            setTrackTitle();
            Intent i = new Intent(ACTION);
            i.putExtra("resultCode", Activity.RESULT_OK);
            i.putExtra("resultValue", "name" + "image" + "time");
            i.putExtra("name", trackName);
            i.putExtra("image", albumImage);
            i.putExtra("time", newTime);
            LocalBroadcastManager.getInstance(this).sendBroadcast(i);

        }
    }

    public void pause() {
        Log.v(LOGCAT, "Service pause() called.");
        state = 3;//Paused State
        if (mMediaPlayer.isPlaying() && state == 3) {
            mMediaPlayer.pause();
        }
    }

    public void play() {
        Log.v(LOGCAT, "Service play() called.");
        state = 2;//Started State
        if(!mMediaPlayer.isPlaying() && state == 2){
           // seekBar.setMax(mMediaPlayer.getDuration());
            mMediaPlayer.start();

        } else if (!mMediaPlayer.isPlaying() && state == 0){
            initMediaPlayer();
            //seekBar.setMax(mMediaPlayer.getDuration());
            mMediaPlayer.start();
        }

        //Set Track Title
        setTrackTitle();
    }

    public void stop() {
        Log.v(LOGCAT, "Service stop() called.");
        state = 4;//Stopped State
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.stop();
        }
    }

    public void previous() {
        Log.v(LOGCAT, "Service previous() called.");
        mMediaPlayer.reset();
        if (isShuffling) {
            Random rand = new Random();
            currentTrack = rand.nextInt((tracks.length - 1) - 0 + 1) + 0;
            createNotification();
            play();
        } else {
            if (currentTrack == 0){
                createNotification();

                play();
            } else {
                currentTrack--;
                createNotification();

                play();
            }
        }

    }

    public void next() {
        Log.v(LOGCAT, "Service next() called.");
        mMediaPlayer.reset();
        if (isShuffling){
            Random rand = new Random();
            currentTrack = rand.nextInt((tracks.length - 1) - 0 + 1) + 0;
            createNotification();
            play();
        } else {
            if (currentTrack < tracks.length - 1){
                currentTrack++;
            } else {
                currentTrack = 0;
            }
            createNotification();
            play();
        }

    }

    public void shuffle() {
        if (isShuffling) {
            isShuffling = false;
            Log.v(LOGCAT, "Service shuffle() is shuffling = FALSE called.");

        } else {
            isShuffling = true;

            Log.v(LOGCAT, "Service shuffle() is shuffling = TRUE called.");

        }
    }

    public int getduration() {
        return mMediaPlayer.getDuration();
    }

    public int getCurrentPosition() {
        return mMediaPlayer.getCurrentPosition();
    }

    public void position() {
        mMediaPlayer.getCurrentPosition();
    }

    public int getSeekPosition() {
        return seekPosition;
    }

    public void setSeekPosition(int pos) {
        seekPosition = pos;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        //updateSeekBar();
        Log.v(LOGCAT, "onProgressChanged() called from FRAGMENT.");

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        //updateSeekBar();
        Log.v(LOGCAT, "onStartTrackingTouch() called from FRAGMENT.");

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

        //updateSeekBar();
        Log.v(LOGCAT, "onStopTrackingTouch() called from FRAGMENT.");

    }

/*

    public void updateSeekBar() {
        seekHandler.postDelayed(updateSongProgress, 100);
        Log.v(LOGCAT, "updateSeekBar() called from FRAGMENT.");

    }



    private Runnable updateSongProgress = new Runnable() {
        @Override
        public void run() {

            elapsedTime = mMediaPlayer.getCurrentPosition();
            //seekBar.setProgress((int) elapsedTime);

            double timeLeft = endTime - elapsedTime;

            //need to update fragment UI time field here
            /*
            stopTime.setText(String.format("%d min, %d sec", TimeUnit.MILLISECONDS.toMinutes((long) timeLeft),
                    TimeUnit.MILLISECONDS.toSeconds((long) timeLeft) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) timeLeft))));

            seekHandler.postDelayed(this, 100);

        }
    };
public void seekTo(int posn) {
        mMediaPlayer.seekTo(posn);
    }

    public int getPosition() {
        return mMediaPlayer.getCurrentPosition();
    }




    */
    public class PlayerBinder extends Binder {
        public PlayerService getService() {
            return PlayerService.this;
        }
    }
/*
    public void getDurationTotal() {
        mMediaPlayer.getDuration();
    }

  public void seekTo() {
        mMediaPlayer.seekTo();
    }


    public long getTotalDuration() {
        return totalDuration;
    }
*/
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.v(LOGCAT, "Service onDestroy() called.");
        if (mMediaPlayer != null) {
            if (mMediaPlayer.isPlaying() && state == 2) {
                mMediaPlayer.stop();
            }
            mMediaPlayer.release();
            cancelNotification();

        }
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        Log.v(LOGCAT, "Service onCompletion() called.");

        updateTrackName();
        initMediaPlayer();
    }


    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        Log.v(LOGCAT, "Service onPrepared() called.");
        //mMediaPlayer.seekTo(seekPosition);
        //totalDuration = mMediaPlayer.getDuration();
        state = 1;//Prepared State
        mediaPlayer.start();
        updateTrackName();

    }


    public void createNotification() {
        Log.v(LOGCAT, "Service createNotification() called.");
        //Create Pending Intent with Notification So User Can Return to App from Notification
        PendingIntent pi = PendingIntent.getActivity(getApplicationContext(), 0, new Intent(getApplicationContext(), MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());
        builder.setSmallIcon(R.mipmap.notification);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), albumImage));
        builder.setContentTitle("Music Player");
        builder.setContentText(trackName + " is now playing");
        builder.setAutoCancel(true);
        builder.setOngoing(true);
        builder.setContentIntent(pi);
        startForeground(STANDARD_NOTIFICATION_ID, builder.build());
    }



    public void cancelNotification() {
        Log.v(LOGCAT, "Service cancelNotification() method called.");
        stopForeground(true);
    }

}
