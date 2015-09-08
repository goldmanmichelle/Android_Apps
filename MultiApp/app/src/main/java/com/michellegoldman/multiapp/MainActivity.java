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
import com.michellegoldman.multiapp.data.SurveyAdapter;
import com.michellegoldman.multiapp.fragments.MainFragment;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;


public class MainActivity extends ActionBarActivity implements MainFragment.surveyLists {

    //MEMBER VARIABLES//
    private final String TAG = "MAINACTIVITY.LOGTAG";
    private final int SURVEY_ACTIVITY_REQUEST = 101;
    public static ArrayList<Survey> mSurveyList = new ArrayList<>();
    private static final String FILENAME = "surveys.txt";
    Survey mSurvey;


    //ACTIVITY METHODS//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction().add(R.id.container, new MainFragment()).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here.
        int id = item.getItemId();
        if (id == R.id.action_add) {
            createSurvey();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void createSurvey() {
        //Create add survey activity intent expecting results back once survey is saved.
        Intent create = new Intent(this, SurveyAddActivity.class);
        create.putExtra("survey_detail", mSurveyList);
        startActivityForResult(create, SURVEY_ACTIVITY_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Receive survey detail results back from add survey activity.
        if (requestCode == SURVEY_ACTIVITY_REQUEST && resultCode == RESULT_OK){
            mSurveyList = (ArrayList<Survey>) data.getSerializableExtra("survey_detail");
            MainFragment frag = (MainFragment) getFragmentManager().findFragmentById(R.id.container);
            frag.setListAdapter(new SurveyAdapter(this, mSurveyList));
        }
    }

    public ArrayList<Survey> getArray(){
        return mSurveyList;
    }

    @Override
    public void returnNewArray(ArrayList<Survey> surveyArray) {
        mSurveyList = surveyArray;
    }

    //Load saved surveys from persistent storage.
    public void loadSavedData() {
        MainFragment frag = (MainFragment) getFragmentManager().findFragmentById(R.id.container);
        try{
            FileInputStream input = openFileInput(FILENAME);
            ObjectInputStream stream = new ObjectInputStream(input);
            while (input.available() != 0){
                mSurvey = (Survey) stream.readObject();
                mSurveyList.add(mSurvey);
            }
            stream.close();
            frag.setListAdapter(new SurveyAdapter(getApplicationContext(), mSurveyList));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //Update survey list
    public void updateData(){
        try {
            FileOutputStream output = openFileOutput(FILENAME, Context.MODE_PRIVATE);
            ObjectOutputStream stream = new ObjectOutputStream(output);
            for (int i = 0; i < mSurveyList.size(); i++){
                mSurvey = mSurveyList.get(i);
                stream.writeObject(mSurvey);
            }
            stream.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
