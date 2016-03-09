//Michelle Goldman
//CPMD 1215
//November 30, 2015
//Updated December 17, 2015

package com.michellegoldman.userdata;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.michellegoldman.userdata.auxiliary.NetworkManager;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginActivity extends AppCompatActivity  {

    public EditText username;
    public EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Enable Parse Local Datastore
        Parse.enableLocalDatastore(getApplicationContext());
        Parse.initialize(this, "hEqtK4W8wNZo68HuqmqEMa7JEKn5qv5VtuYOTge6", "4Pfe6C9WAATAIiJgGSzX9CBKMh4M56Ap6O4Z6DKz");
        ParseACL defaultACL = new ParseACL();
        ParseACL.setDefaultACL(defaultACL, true);

        //check for network connection before trying to load user's courses
        if (NetworkManager.isConnected(LoginActivity.this)) {
            //Set Parse current user
            ParseUser currentUser = ParseUser.getCurrentUser();
            if (currentUser != null) {
                //Go directly to courses if current user is already logged in
                Intent intent = new Intent(this, MyCoursesActivity.class);
                startActivity(intent);
            }
        }



        //Set up username field
        final TextInputLayout usernameWrapper = (TextInputLayout) findViewById(R.id.username_wrapper);
        usernameWrapper.setHint("Username");
        final EditText username = (EditText) findViewById(R.id.username_textView);

        //Set up password field
        final TextInputLayout passwordWrapper = (TextInputLayout) findViewById(R.id.password_wrapper);
        passwordWrapper.setHint("Password");
        final EditText password = (EditText) findViewById(R.id.password_textView);

        //Set up login button
        Button login = (Button) findViewById(R.id.login_button);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Create progress indicator for login process
                final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this, R.style.AppTheme_Dark_Dialog);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Logging in...");

                //Check for network connection before attempting login
                if (NetworkManager.isConnected(LoginActivity.this)) {

                    if (username.length() == 0){
                        //Alert user to enter username
                        username.setError("Please enter a username!");

                    } else if (username.length() < 4 || username.length() > 10){
                        //Alert user to enter username
                        username.setError("Username must be between 4 - 10 characters.");

                    } else if (username.getText().toString().contains("%,./?!*&$#@()-_=+")){
                        //Alert user to enter username
                        username.setError("Special characters are not allowed in username.");

                    } else if (password.length() == 0) {
                        //Alert user to enter password
                        password.setError("Please enter a password!");

                    } else if (password.length() < 8 || password.length() > 8) {
                        //Alert user to enter password
                        password.setError("Password must be 8 numbers.");


                    }

                   //Log user into Parse
                    ParseUser.logInInBackground(username.getText().toString(), password.getText().toString(), new LogInCallback() {


                        @Override
                        public void done(ParseUser user, ParseException e) {
                            if (user != null && e == null) {

                                progressDialog.show();

                                Intent intent = new Intent(LoginActivity.this, MyCoursesActivity.class);
                                startActivity(intent);

                                //Dismiss progress dialog
                                new android.os.Handler().postDelayed(
                                        new Runnable() {
                                            @Override
                                            public void run() {
                                                progressDialog.dismiss();

                                            }
                                        }, 3000);


                            } else {
                                //Alert user of login failure
                              //  Toast.makeText(getApplicationContext(), ("Parse Error: " + e.getMessage() + "." + " Please check your username and password and try again."), Toast.LENGTH_SHORT).show();

                                //Dismiss progress dialog
                                new android.os.Handler().postDelayed(
                                        new Runnable() {
                                            @Override
                                            public void run() {
                                                progressDialog.dismiss();

                                            }
                                        }, 0);
                                //resetFields();
                            }


                        }
                        //??????? WHERE CAN I RESET THE EDITTEXT FIELDS SO THAT THEY ARE CLEARED SURING THE LOGIN PROCESS. RIGHT NOW AFTER
                        // LOGGING IN AND THEN LOGGING OUT, THE FIELDS STILL CONTAIN THE LOGIN INFO.???/////
                    });

                }

            }

        });




        //Set up signup link
        Button signup = (Button) findViewById(R.id.signup_button);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });

    }



    //Reset text fields
    private void resetFields() {
        username.setText("");
        password.setText("");

    }



}
