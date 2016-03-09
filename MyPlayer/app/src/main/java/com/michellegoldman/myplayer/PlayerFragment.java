//Michelle Goldman
//MDFIII 1601
//January 8, 2016

package com.michellegoldman.myplayer;


import android.app.Activity;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class PlayerFragment extends Fragment  {


    private static final String LOGCAT = "MUSICFRAGMENT_LOG";
    public ImageView albumImage;
    public ImageButton playButton, pauseButton, stopButton, skipNextButton, skipPreviousButton;
    public TextView songTitleTV, startTime, stopTime;
    public CheckBox shuffleBox;
    MediaPlayer mMediaPlayer;
    public SeekBar seekBar;
    public Handler seekHandler = new Handler();
    public double elapsedTime = 0;
    public double endTime = 0;
    public int seekForward = 5000;
    public int seekBackward = 5000;


    MediaControls mMediaControls;

    public PlayerFragment() {
    }



    //MEDIA PLAYBACK CONTROLS INTERFACE
    public interface MediaControls {
        void play();
        void pause();
        void stop();
        void next();
        void previous();
        void shuffle();
        //void seekTo();
       // long getSeekBarPosition();
        //void position();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        setRetainInstance(true);

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_player, container, false);

        IntentFilter filter = new IntentFilter(PlayerService.ACTION);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(receiver, filter);

        //Initialize Views
        albumImage = (ImageView) rootView.findViewById(R.id.albumImage);
        playButton = (ImageButton) rootView.findViewById(R.id.play);
        pauseButton = (ImageButton) rootView.findViewById(R.id.pause);
        stopButton = (ImageButton) rootView.findViewById(R.id.stop);
        skipNextButton = (ImageButton) rootView.findViewById(R.id.skip_next);
        skipPreviousButton = (ImageButton) rootView.findViewById(R.id.skip_previous);
        shuffleBox = (CheckBox) rootView.findViewById(R.id.shuffleBox);
        //seekBar = (SeekBar) rootView.findViewById(R.id.seekBar);
        //seekBar.setOnSeekBarChangeListener((SeekBar.OnSeekBarChangeListener) this);
        //seekBar.setMax((int) endTime);
        //startTime = (TextView) rootView.findViewById(R.id.startTime);
        //stopTime = (TextView) rootView.findViewById(R.id.stopTime);
       // endTime = mMediaControls.duration();
        songTitleTV = (TextView) rootView.findViewById(R.id.songTitleTV);




        //Set Play Button OnClickListener
        playButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Log.v(LOGCAT, "PlayerFragment playButton pressed.");
                mMediaControls.play();
                //mMediaControls.getSeekBarPosition();
                //updateSeekBar();
                //need to implement mediacontrol method to run code below
                /*
                elapsedTime = mMediaControls.position();
                seekBar.setProgress((int) elapsedTime);
                seekHandler.postDelayed(updateSongProgress, 100);
                */
            }
        });

        //Set Pause Button OnClickListener
        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v(LOGCAT, "PlayerFragment pauseButton pressed.");
                mMediaControls.pause();

            }
        });

        //Set Stop Button OnClickListener
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v(LOGCAT, "PlayerFragment stopButton pressed.");
                mMediaControls.stop();

            }
        });

        //Set Skip Next Button OnClickListener
        skipNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v(LOGCAT, "PlayerFragment skipNextButton pressed.");
                mMediaControls.next();

            }
        });

        //Set Skip Previous Button OnClickListener
        skipPreviousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v(LOGCAT, "PlayerFragment skipPreviousButton pressed.");
                mMediaControls.previous();

            }
        });

        shuffleBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMediaControls.shuffle();
            }
        });

        return rootView;

    }

/*

    public void updateSeekBar() {
        seekHandler.postDelayed(updateSongProgress, 100);
        Log.v(LOGCAT, "updateSeekBar() called from FRAGMENT.");

    }

    private Runnable updateSongProgress = new Runnable() {
        @Override
        public void run() {
            elapsedTime = mMediaControls.position();
            seekBar.setProgress((int) elapsedTime);
            double timeLeft = endTime - elapsedTime;

            stopTime.setText(String.format("%d min, %d sec", TimeUnit.MILLISECONDS.toMinutes((long) timeLeft),
                    TimeUnit.MILLISECONDS.toSeconds((long) timeLeft) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) timeLeft))));
            seekHandler.postDelayed(this, 100);

        }
    };



    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
       // updateSeekBar();
        Log.v(LOGCAT, "onProgressChanged() called from FRAGMENT.");

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
       // updateSeekBar();
        Log.v(LOGCAT, "onStartTrackingTouch() called from FRAGMENT.");

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

       // updateSeekBar();
        Log.v(LOGCAT, "onStopTrackingTouch() called from FRAGMENT.");

    }
*/
    //UPDATE SONG TRACK NAME
    public void updateSongInfo(Intent intent) {

        String songTitle = intent.getStringExtra("name");
        songTitleTV.setText(songTitle);

        //Create album art for each track
        Drawable floaton = getResources().getDrawable(R.drawable.floaton);
        Drawable heaven = getResources().getDrawable(R.drawable.heaven);
        Drawable memory = getResources().getDrawable(R.drawable.memory);
        Drawable missu = getResources().getDrawable(R.drawable.missu);
        Drawable nightmare = getResources().getDrawable(R.drawable.nightmare);
        Drawable tragedies = getResources().getDrawable(R.drawable.tragedies);

        //Set album art based on track title
        if (songTitleTV.getText().equals("Float On")) {
            albumImage.setImageDrawable(floaton);
        } else if (songTitleTV.getText().equals("Heaven Help Us")) {
            albumImage.setImageDrawable(heaven);
        } else if (songTitleTV.getText().equals("Making A Memory")) {
            albumImage.setImageDrawable(memory);
        } else if (songTitleTV.getText().equals("I Miss You")) {
            albumImage.setImageDrawable(missu);
        } else if (songTitleTV.getText().equals("All American Nightmare")) {
            albumImage.setImageDrawable(nightmare);
        } else if (songTitleTV.getText().equals("I Write Sins Not Tragedies")) {
            albumImage.setImageDrawable(tragedies);
        }

        //String newTime = intent.getStringExtra("time");
        //stopTime.setText(newTime);

        Log.v(LOGCAT, " updateSongInfo() called from FRAGMENT.");

    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (activity instanceof MediaControls) {
            mMediaControls = (MediaControls) activity;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof MediaControls) {
            mMediaControls = (MediaControls) context;
        }

    }


    //Create broadcast receiver
    public BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            updateSongInfo(intent);
            }

    };

}
