//Michelle Goldman
//APD2 1602
//February 27, 2016

package com.michellegoldman.truthortale;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.facebook.FacebookSdk;

public class GameOverActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "GameOverActivity";

    ImageButton playAgainButton, quitButton, shareButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        //Initialize Facebook
        FacebookSdk.sdkInitialize(getApplicationContext());


        //Instantiate Play, Rules & About Buttons. Set OnClickListeners.
        playAgainButton = (ImageButton)findViewById(R.id.playAgainButton);
        playAgainButton.setOnClickListener(this);

        quitButton = (ImageButton)findViewById(R.id.quitButton);
        quitButton.setOnClickListener(this);

        shareButton = (ImageButton)findViewById(R.id.shareButton);
        shareButton.setOnClickListener(this);

    }



    @Override
    public void onClick(View view) {

        if (view == playAgainButton) {
            Log.d(TAG, "Play Again button clicked");

            //Load Category Activity
            Intent catIntent = new Intent(this, CategoryActivity.class);
            startActivity(catIntent);

        } else if (view == quitButton) {
            Log.d(TAG, "Quit button clicked");

            //Load Home Screen Activity
            Intent homeIntent = new Intent(this, MainActivity.class);
            startActivity(homeIntent);

        } else if (view == shareButton) {
            Log.d(TAG, "Share button clicked");

            //Load Sharing Activity
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, "Join me playing Truth or Tale");
            startActivity(Intent.createChooser(shareIntent, "Download and try it now!"));


        }
    }
}
