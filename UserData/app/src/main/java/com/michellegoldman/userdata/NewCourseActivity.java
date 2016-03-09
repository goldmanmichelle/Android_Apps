//Michelle Goldman
//CPMD 1215
//November 30, 2015
//Updated December 17, 2015


package com.michellegoldman.userdata;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.michellegoldman.userdata.auxiliary.NetworkManager;
import com.michellegoldman.userdata.data.Course;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;


public class NewCourseActivity extends AppCompatActivity {

    private EditText courseName;
    private EditText numTimes;
    private Button saveButton;
    private Button cancelButton;
    private Course mCourse;
    private String courseId = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_course);


        // Fetch the courseId from the Extra data
        if (getIntent().hasExtra("ID")) {
            courseId = getIntent().getExtras().getString("ID");
        }

        //Set up course name editText
        courseName = (EditText) findViewById(R.id.course_editText);
        final TextInputLayout coursenameWrapper = (TextInputLayout) findViewById(R.id.new_course_wrapper);
        coursenameWrapper.setHint("Course Name");

        //Set up times taken editText
        numTimes = (EditText) findViewById(R.id.times_taken_editText);
        final TextInputLayout timestakenWrapper = (TextInputLayout) findViewById(R.id.times_taken_wrapper);
        timestakenWrapper.setHint("Times Taken");

        //Set up save button
        saveButton = (Button) findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Create editText field prompts
                TextView coursenamePrompt = (TextView) findViewById(R.id.course_name_prompt);
                TextView timestakenPrompt = (TextView) findViewById(R.id.password_prompt);

                if (courseName.length() == 0){
                     //Alert user courseName needs to be entered
                     courseName.setError("Please enter a course name!");


                 } else if (courseName.length() < 3 || courseName.length() > 24){
                     //Alert user courseName needs to be entered
                     courseName.setError("Course name must be between 3 - 24 characters long!");
                     coursenamePrompt.setVisibility(View.VISIBLE);


                 } else if (numTimes.length() == 0){
                     //Alert user timesTaken needs to be entered
                     numTimes.setError("Please enter # of times course has been taken!");

                 } else if (numTimes.length() > 2){
                     //Alert user timesTaken needs to be entered
                     numTimes.setError("Please enter # of times course has been taken!");
                     timestakenPrompt.setVisibility(View.VISIBLE);


                 } else {

                     //Check for network connection before saving course
                     if (NetworkManager.isConnected(NewCourseActivity.this)) {

                         //Save current course to Parse cloud
                         ParseObject courses = new ParseObject("Courses");
                         courses.put("userName", ParseUser.getCurrentUser().getUsername());
                         courses.put("courseName", courseName.getText().toString());
                         courses.put("timesTaken", Integer.parseInt(numTimes.getText().toString()));

                         courses.saveInBackground(new SaveCallback() {
                             @Override
                             public void done(ParseException e) {
                                 if (e == null) {
                                     //Alert user course was saved
                                     Toast.makeText(getApplicationContext(), "Course saved!", Toast.LENGTH_SHORT).show();

                                     //Take user to their home courses page and clear editText fields
                                     finish();
                                     //resetForm();

                                 } else {
                                     //Alert user course was not saved
                                     Toast.makeText(getApplicationContext(), "Course was NOT saved! Please try again!", Toast.LENGTH_SHORT).show();
                                 }

                             }
                         });

                     } else  {

                         //Save current course to Parse cloud when able
                         ParseObject courses = new ParseObject("Courses");
                         courses.put("userName", ParseUser.getCurrentUser().getUsername());
                         courses.put("courseName", courseName.getText().toString());
                         courses.put("timesTaken", Integer.parseInt(numTimes.getText().toString()));
                         courses.saveEventually();

                         //Alert user course was saved
                         Toast.makeText(getApplicationContext(), "No network connection detected. Course will be saved once you are connected to a network!", Toast.LENGTH_LONG).show();

                         //Take user to their home courses page and clear editText fields
                         finish();
                         //resetForm();
                     }
                 }


             }

        });

        //Set up cancel button
        cancelButton = (Button) findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Return user to their course list
                finish();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.new_course, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here.
        int id = item.getItemId();
        if (id == R.id.action_clear) {
            resetForm();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void resetForm() {

        courseName.setText("");
        numTimes.setText("");
    }



}
