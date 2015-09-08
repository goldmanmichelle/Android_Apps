//Michelle Goldman
//Java 2 Week 4 1504
//April 28, 2015

package com.michellegoldman.multiapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.michellegoldman.multiapp.data.Survey;
import com.michellegoldman.multiapp.fragments.SurveyAddFragment;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Created by mgoldman on 4/29/15.
 */
public class SurveyAddActivity extends ActionBarActivity implements SurveyAddFragment.surveyLists {

    //MEMBER VARIABLES//
    private final String TAG = "SURVEYADDACTIVITY.TAG";
    private static final String FILENAME = "surveys.txt";
    private ArrayList<Survey> mSurveyList = new ArrayList<>();
    Survey mSurvey;


    //ACTIVITY METHODS//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_add);

        //Load fragment
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction().add(R.id.container, new SurveyAddFragment()).commit();
        }

        //Get intent from main activity
        Intent intent = this.getIntent();
        mSurveyList = (ArrayList<Survey>) intent.getSerializableExtra("survey_detail");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.survey_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Handle menu click actions
        if (item.getItemId() == android.R.id.home){
            saveSurvey();

            //Save current survey form
        } else if (item.getItemId() == R.id.action_save){
            SurveyAddFragment frag = (SurveyAddFragment) getFragmentManager().findFragmentById(R.id.container);
            frag.createSurvey();

        //Reset current survey form
        } else if (item.getItemId() == R.id.action_reset){
            SurveyAddFragment frag = (SurveyAddFragment) getFragmentManager().findFragmentById(R.id.container);
            frag.resetForm();
        }
        return  false;
    }

    private void saveSurvey() {
        //Create intent to pass to main activity, update list and save to persistent data file.
        Intent intent = new Intent(this, MainActivity.class);

        //Save survey details to file
        intent.putExtra("survey_detail", mSurveyList);
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

        //Return to main activity *!! not working !!**
        finish();
    }

    @Override
    public void details(String _fName, String _lName, String _city, String _state) {
        mSurveyList.add(new Survey(_fName, _lName, _city, _state));
    }

    //set device back button to run saveAndFinish
    @Override
    public void onBackPressed() {
        saveSurvey();
    }


}
