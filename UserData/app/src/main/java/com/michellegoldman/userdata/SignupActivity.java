//Michelle Goldman
//CPMD 1215
//November 30, 2015
//Updated December 17, 2015

package com.michellegoldman.userdata;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.michellegoldman.userdata.auxiliary.NetworkManager;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;


public class SignupActivity extends AppCompatActivity  {

    String usernameString;
    String passwordString;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        //Set up username field
        final TextInputLayout usernameWrapper = (TextInputLayout) findViewById(R.id.username_wrapper);
        usernameWrapper.setHint("Username");
        final EditText username = (EditText) findViewById(R.id.username_textView);

        //Set up password field
        final TextInputLayout passwordWrapper = (TextInputLayout) findViewById(R.id.password_wrapper);
        passwordWrapper.setHint("Password");
        final EditText password = (EditText) findViewById(R.id.password_textView);


        //Set up sign up button
        Button signup = (Button) findViewById(R.id.signup_button);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //Create editText field prompts
                TextView usernamePrompt = (TextView) findViewById(R.id.username_prompt);
                TextView passwordPrompt = (TextView) findViewById(R.id.password_prompt);

                //Check for network connection before attempting signup
                if (NetworkManager.isConnected(SignupActivity.this)) {

                    //Create Parse User
                    ParseUser user = new ParseUser();

                    //Enter username
                    if (username.length() == 0) {
                        //Alert user to enter username
                        username.setError("Please enter a username!");

                    } else if (username.length() < 4 || username.length() > 10) {
                        //Alert user to invalid username
                        usernamePrompt.setVisibility(View.VISIBLE);
                        username.setError("Username must be between 4 - 10 characters.");

                    } else if (username.getText().toString().contains("%,./?!*&$#@()-_=+")){
                        //Alert user to enter username
                        Toast.makeText(getApplicationContext(), "Special characters are not allowed in username.", Toast.LENGTH_SHORT).show();

                    } else {
                        //Set Parse username
                        user.setUsername(username.getText().toString());

                    }

                    //Enter password
                    if (password.length() == 0) {
                        //Alert user to enter password
                        password.setError("Please enter a password!");


                    } else if (password.length() < 8 || password.length() > 8) {
                        //Alert user to invalid password
                        passwordPrompt.setVisibility(View.VISIBLE);
                        password.setError("Password must be 8 numbers.");

                    }  else {
                        //Set Parse password
                        user.setPassword(password.getText().toString());

                    }

                    //Create progress indicator for login process
                    final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this, R.style.AppTheme_Dark_Dialog);
                    progressDialog.setIndeterminate(true);
                    progressDialog.setMessage("Creating account...");

                    //Sign Up In Background
                    user.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {

                                progressDialog.show();

                                Toast.makeText(getApplicationContext(), "Signup successful!", Toast.LENGTH_SHORT).show();
                                finish();

                            } else {
                                //Dismiss progress dialog
                                new android.os.Handler().postDelayed(
                                        new Runnable() {
                                            @Override
                                            public void run() {
                                                progressDialog.dismiss();
                                            }
                                        }, 3000);


                                //Toast.makeText(getApplicationContext(), "Please try again!!", Toast.LENGTH_SHORT).show();
                               // Toast.makeText(getApplicationContext(), ("Parse Error: " + e.getMessage()), Toast.LENGTH_SHORT).show();

                            }
                        }
                    });

                } else {
                    //Alert user there is no valid data connection
                    //Toast.makeText(getApplicationContext(), "Network error. Unable to process sign up. Please try again later or turn on WIFI in settings.", Toast.LENGTH_LONG).show();
                }

            }

        });

    }




}
