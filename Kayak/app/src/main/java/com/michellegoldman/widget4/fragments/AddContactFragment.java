//Michelle Goldman
//MDF3 1601
//Updated 1/20/2016

package com.michellegoldman.widget4.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.michellegoldman.widget4.R;

public class AddContactFragment extends Fragment{

    private final String TAG = "ADDCONTACTFRAGMENT.TAG";

    private TextView title;
    private TextView firstName;
    private TextView lastName;
    private TextView age;
    private contactLists mLists;

    public AddContactFragment () {

    }

    public interface contactLists {
        void details(String _title, String _firstName, String _lastName, String _age);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Inflate item list fragment//
        View rootView = inflater.inflate(R.layout.fragment_contact_form, container, false);

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.v(TAG, "onAttach() mLists called");

        if (activity instanceof contactLists) {
            mLists = (contactLists) activity;
        } else {
            throw new IllegalArgumentException(" must implement the Contact Detail View");
        }
    }

    @Override
    public void onActivityCreated(Bundle _savedInstanceState) {
        super.onActivityCreated(_savedInstanceState);

        title = (TextView) getActivity().findViewById(R.id.editTextTitle);
        firstName = (TextView) getActivity().findViewById(R.id.editTextFirstName);
        lastName = (TextView) getActivity().findViewById(R.id.editTextLastName);
        age = (TextView) getActivity().findViewById(R.id.editTextAge);

        Log.v(TAG, "onActivityCreated() called loading views" );

    }

    //Method used to create new contacts
    public void createContact(){
        //Instantiate first name textView & set text
        title = (TextView) getActivity().findViewById(R.id.editTextTitle);
        String nTitle = title.getText().toString();

        //Instantiate last name textView & set text
        firstName = (TextView) getActivity().findViewById(R.id.editTextFirstName);
        String nFirstName = firstName.getText().toString();

        //Instantiate city textView & set text
        lastName = (TextView) getActivity().findViewById(R.id.editTextLastName);
        String nLastname = lastName.getText().toString();

        //Instantiate state textView & set text
        age = (TextView) getActivity().findViewById(R.id.editTextAge);
        String nAge = age.getText().toString();

        //Set text for contact elements in details method
        title.setText(nTitle);
        firstName.setText(nFirstName);
        lastName.setText(nLastname);
        age.setText(nAge);

        mLists.details(nTitle, nFirstName, nLastname, nAge);


        //Alert user that contact has been added
        Toast.makeText(getActivity(), "Your new spot has been saved.", Toast.LENGTH_LONG).show();

        Log.v(TAG, "createContact() called");

    }


    public void resetForm() {

        title.setText("");
        firstName.setText("");
        lastName.setText("");
        age.setText("");
    }

}
