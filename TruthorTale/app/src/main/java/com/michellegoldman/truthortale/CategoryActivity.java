//Michelle Goldman
//APD2 1602
//February 27, 2016

package com.michellegoldman.truthortale;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;

import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "CategoryActivity";

    ArrayList<Category> categoryList = new ArrayList<>();
    ArrayAdapter adapter;
    ImageButton startButton;
    Spinner mSpinner;
    public String selectedCategory;
    public boolean playingGame = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
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
        //Enable Toolbar Back Button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Add Categories to Array for Spinner Drop Down List

        categoryList.add(new Category("Art & Literature"));
        categoryList.add(new Category("Music"));
        categoryList.add(new Category("Science & Nature"));
        categoryList.add(new Category("Geography"));
        categoryList.add(new Category("History & Holidays"));
        categoryList.add(new Category("People & Places"));
        categoryList.add(new Category("Sports & Leisure"));
        categoryList.add(new Category("Food & Drink"));


        //Instantiate Spinner & ArrayAdapter. Set OnItemSelected Listener.
        mSpinner = (Spinner) findViewById(R.id.category_spinner);
        adapter = ArrayAdapter.createFromResource(this, R.array.game_categories, android.R.layout.simple_spinner_item);
        mSpinner.setAdapter(adapter);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                selectedCategory = categoryList.get(position).toString();
                Log.d(TAG, "You just tapped " + String.valueOf(mSpinner.getSelectedItem()));

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //Instantiate Start Button. Set OnClickListener
        startButton = (ImageButton) findViewById(R.id.startButton);
        startButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {

      if (view == startButton) {
          playingGame = true;
          //Create Intent
          Intent gameIntent = new Intent(this, QuizActivity.class);
          //Send Category Name to Quiz Activity
          gameIntent.putExtra("categoryName", String.valueOf(mSpinner.getSelectedItem()));
          //Load New Game
          startActivity(gameIntent);
          Log.d(TAG, "Sending " + String.valueOf(mSpinner.getSelectedItem() + " to Quiz Activity"));

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        playingGame = false;
        finish();
    }



}
