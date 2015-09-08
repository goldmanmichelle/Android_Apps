//Michelle Goldman
//Java 2 Week 4 1504
//April 28, 2015

package com.michellegoldman.multiapp.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.michellegoldman.multiapp.R;

/**
 * Created by mgoldman on 4/29/15.
 */
public class SurveyAddFragment extends Fragment {

    //MEMBER VARIABLES//
    public static final String TAG = "SurveyAddFragment.TAG";
    private TextView fName;
    private TextView lName;
    private TextView city;
    private TextView state;
    private surveyLists mLists;

    //CONSTRUCTORS//
    public SurveyAddFragment() {

    }

    //INTERFACES//
    public  interface surveyLists {

        public void details(String _fName, String _lname, String _city, String _state);
    }

    //FRAGMENT METHODS//
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_survey, container, false);
        return rootView;
    }

    public void onAttach(Activity activity){
        super.onAttach(activity);
        if (activity instanceof surveyLists){
            mLists = (surveyLists) activity;
        } else {
            throw new IllegalArgumentException("Must Implement Survey Detail view");
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //Instantiate textViews
        fName = (TextView) getActivity().findViewById(R.id.fNameText);
        lName = (TextView) getActivity().findViewById(R.id.lNameText);
        city = (TextView) getActivity().findViewById(R.id.cityText);
        state = (TextView) getActivity().findViewById(R.id.stateText);
    }

    public void createSurvey(){
        //Method used to create new surveys
        //Instantiate first name textView & set text
        fName = (TextView) getActivity().findViewById(R.id.fNameText);
        String firstName = fName.getText().toString();

        //Instantiate last name textView & set text
        lName = (TextView) getActivity().findViewById(R.id.lNameText);
        String lastName = lName.getText().toString();

        //Instantiate city textView & set text
        city = (TextView) getActivity().findViewById(R.id.cityText);
        String nCity = city.getText().toString();

        //Instantiate state textView & set text
        state = (TextView) getActivity().findViewById(R.id.stateText);
        String nState = state.getText().toString();

        //Set text for survey elements in details method
        mLists.details(firstName, lastName, nCity, nState);
        fName.setText("");
        lName.setText("");
        city.setText("");
        state.setText("");

        //Alert user that survey has been added
        Toast.makeText(getActivity(), "Your survey has been registered. Thank you for your time.", Toast.LENGTH_LONG).show();
    }

    public void resetForm(){
        //Method used to reset survey form
        //Get first name textView & clear text
        fName = (TextView) getActivity().findViewById(R.id.fNameText);
        fName.setText("");

        //Get last name textView & clear text
        lName = (TextView) getActivity().findViewById(R.id.lNameText);
        lName.setText("");

        //Get city textView & clear text
        city = (TextView) getActivity().findViewById(R.id.cityText);
        city.setText("");

        //Get state textView & clear text
        state = (TextView) getActivity().findViewById(R.id.stateText);
        state.setText("");
    }

}
