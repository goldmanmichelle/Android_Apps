package com.michellegoldman.so;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends Activity {

    //Global Variables//
    EditText editText;
    String creationStr;
    TextView numOfShouts, lengthOfShouts;
    ArrayList<String> shoutArray = new ArrayList<>();

    //Private variables//
    private ListView listView;
    private ArrayAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize Views//
        listView = (ListView) findViewById(R.id.shoutListView);
        numOfShouts = (TextView) findViewById(R.id.numOfShoutsTextView);
        lengthOfShouts = (TextView) findViewById(R.id.lengthOfShoutsTextView);
        editText = (EditText) findViewById(R.id.creationText);

        //Create array adapter for plugging in data to be used//
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, shoutArray);

        //call setAdapter method which plugs in the array into the list view
        listView.setAdapter(adapter);

        //Set listener to allow referencing of selected row item//
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                //Create Alert Dialog Box//
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                //Set Dialog Message//
                dialog.setMessage(shoutArray.get(i) + " selected");
                //Set Alert Title//
                dialog.setTitle("My Shout List");
                //Set alert message icon
                dialog.setIcon(R.mipmap.ic_launcher);
                //Set onClick and Dismiss Buttons//
                dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                //Display Alert//
                dialog.show();
            }

        });


        //Check for duplicate entries and alert user when pressing Shout It button//
        final Button submitCreation = (Button) findViewById(R.id.shoutItButton);
        submitCreation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                creationStr = editText.getText().toString();

                if (editText.length() == 0) {

                    //Dismiss Keyboard Automatically after submitting creation//
                    InputMethodManager hideKeyboard = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    hideKeyboard.hideSoftInputFromWindow(editText.getWindowToken(), 0);

                    Toast alert = Toast.makeText(getApplicationContext(), "Please enter your creation first!", Toast.LENGTH_LONG);
                    alert.show();
                } else {

                    //Check for duplicate entries//
                    if (shoutArray.contains(editText.getText().toString())) {
                        editText.setText("");

                        //Dismiss Keyboard Automatically after submitting creation//
                        InputMethodManager hideKeyboard = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                        hideKeyboard.hideSoftInputFromWindow(editText.getWindowToken(), 0);

                        //Alert user that creation entry already exists//
                        Toast alert = Toast.makeText(getApplicationContext(), "That creation has already been entered. Please try another.", Toast.LENGTH_SHORT);
                        alert.show();
                    } else {

                        //Add to array
                        shoutArray.add(creationStr);

                        //Dismiss Keyboard Automatically after submitting creation//
                        InputMethodManager hideKeyboard = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                        hideKeyboard.hideSoftInputFromWindow(editText.getWindowToken(), 0);

                        //Set # of shouts created/saved//
                        numOfShouts.setText(getString(R.string.numOfShouts) + shoutArray.size());

                        //Set the average length of shouts added to data collection//
                        lengthOfShouts.setText(getString(R.string.lengthOfShouts) + getLengthOfShouts());

                        //Clear text creation field to prepare for new entry//
                        editText.setText("");

                        //Alert user that Shout was added
                        Toast alert = Toast.makeText(getApplicationContext(), creationStr + " has been added", Toast.LENGTH_SHORT);
                        alert.show();
                    }
                }
            }

        });
    }

 //Find the average # of Shouts

    private int getLengthOfShouts() {
        int numOfShouts = 0;

        Log.e("Num of Shouts", shoutArray.size() + "");

        for (String text : shoutArray) {
            numOfShouts += text.length();
            Log.e("Num of Shouts", numOfShouts + "");
        }
        numOfShouts /= shoutArray.size();
        return numOfShouts;

    }

}


