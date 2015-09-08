//Michelle Goldman
//Java 2 Week 4 1504
//April 28, 2015

package com.michellegoldman.multiapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.michellegoldman.multiapp.data.Survey;
import com.michellegoldman.multiapp.fragments.DetailFragment;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Created by mgoldman on 4/29/15.
 */
public class DetailActivity extends ActionBarActivity {

    //MEMBER VARIABLES//
    private int position;
    private ArrayList<Survey> mSurveyList = new ArrayList<>();
    private Survey mSurveyDetails = new Survey();
    private static final String FILENAME = "surveys.txt";
    Survey mSurvey;


    //ACTIVITY METHODS//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //Load fragment
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction().replace(R.id.container, new DetailFragment()).commit();

            //Get intent from main activity
            Intent intent = this.getIntent();
            mSurveyList = (ArrayList<Survey>) intent.getSerializableExtra("array");
            position = intent.getIntExtra("position", 0);
            mSurveyDetails = (Survey) intent.getSerializableExtra("survey_details");
        }
    }

    //Delete method: Sends updated survey list to main activity & saves to persistent data file.
    public void deleteSurvey() {
        //Remove survey list item by position
        mSurveyList.remove(position);

        //Create intent
        Intent intent = new Intent();
        intent.putExtra("array", mSurveyList);
        try {
            FileOutputStream output = openFileOutput(FILENAME, Context.MODE_PRIVATE);
            ObjectOutputStream stream = new ObjectOutputStream(output);
            for (int i = 0; i < mSurveyList.size(); i++) {
                mSurvey = mSurveyList.get(i);
                stream.writeObject(mSurvey);
            }
            stream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Send intent results back to main activity
        setResult(RESULT_OK, intent);

        //Return to main activity *
        finish();
    }

    public Survey setValues() {
        return mSurveyDetails;
    }


    @Override
    public void onBackPressed() {
        //Handle user pressing device back button. Send data back to main activity to update list.
        Intent intent = new Intent();
        intent.putExtra("array", mSurveyList);
        setResult(RESULT_OK, intent);
        finish();
    }

}
