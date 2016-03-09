//Michelle Goldman
//APD2 1602
//February 27, 2016

package com.michellegoldman.truthortale;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    ImageButton playButton, rulesButton, aboutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
*/
        //Instantiate Play, Rules & About Buttons. Set OnClickListeners.
        playButton = (ImageButton) findViewById(R.id.playButton);
        playButton.setOnClickListener(this);

        rulesButton = (ImageButton) findViewById(R.id.rulesButton);
        rulesButton.setOnClickListener(this);

        aboutButton = (ImageButton) findViewById(R.id.aboutButton);
        aboutButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        if (view == playButton) {
            //Load QuizActivity
            Intent quizIntent = new Intent(this, CategoryActivity.class);
            startActivity(quizIntent);

        } else if (view == rulesButton) {
            //Load Rules Screen
            Intent rulesIntent = new Intent(this, RulesActivity.class);
            startActivity(rulesIntent);

        } else if (view == aboutButton) {
            //Load About Screen
            Intent aboutIntent = new Intent(this, AboutActivity.class);
            startActivity(aboutIntent);

        }
    }
}
