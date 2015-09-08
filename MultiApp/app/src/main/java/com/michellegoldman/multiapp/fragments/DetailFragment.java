//Michelle Goldman
//Java 2 Week 4 1504
//April 28, 2015

package com.michellegoldman.multiapp.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.michellegoldman.multiapp.DetailActivity;
import com.michellegoldman.multiapp.R;
import com.michellegoldman.multiapp.data.Survey;

/**
 * Created by mgoldman on 4/29/15.
 */
public class DetailFragment extends Fragment {

    //MEMBER VARIABLES//
    public static final String TAG = "DetailFragment.TAG";
    private Survey mSurveyDetails = new Survey();

    //CONSTRUCTORS//
    public  DetailFragment (){
    }

    //INTERFACES//


    //FRAGMENT METHODS//
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mSurveyDetails = ((DetailActivity) getActivity()).setValues();

        //Instantiate first name view & set text
        TextView firstName = (TextView) getActivity().findViewById(R.id.dvFirstName);
        firstName.setText("Participant: " + mSurveyDetails.getfName());

        //Instantiate last name view & set text
        TextView lastName = (TextView) getActivity().findViewById(R.id.dvLastName);
        lastName.setText( mSurveyDetails.getlName());

        //Instantiate city view & set text
        TextView nCity = (TextView) getActivity().findViewById(R.id.dvCity);
        nCity.setText("From: " + mSurveyDetails.getCity() + ", ");

        //Instantiate state view & set text
        TextView nState = (TextView) getActivity().findViewById(R.id.dvState);
        nState.setText( mSurveyDetails.getState());

        //Instantiate delete survey item button & its onClickListener
        Button deleteButton = (Button) getActivity().findViewById(R.id.delete);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((DetailActivity) getActivity()).deleteSurvey();
            }
        });

    }

}
