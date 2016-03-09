//Michelle Goldman
//MDF3 1601
//Updated 1/20/2016

package com.michellegoldman.widget4.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.michellegoldman.widget4.Contact;
import com.michellegoldman.widget4.R;
import com.michellegoldman.widget4.ViewActivity;

/**
 * Created by mgoldman on 10/13/15.
 */
public class ContactDetailFragment extends Fragment{

    private final String LOGCAT = "CONTACTDETAILFRAGMENT.LOGCAT";

    private Contact mContactDetails = new Contact();

    public ContactDetailFragment(){

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Inflate item list fragment//
        View rootView = inflater.inflate(R.layout.fragment_contact_detail, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //Set contact title  view//

        mContactDetails = ((ViewActivity) getActivity()).setValues();

        ImageView spotImage = (ImageView) getActivity().findViewById(R.id.image);
        spotImage.setImageResource(R.drawable.kayaking);

        TextView titleTV = (TextView) getActivity().findViewById(R.id.spotParkNameView);
        titleTV.setText(mContactDetails.getTitle());

        //Set contact first name //
        TextView dtv = (TextView) getActivity().findViewById(R.id.spotCityNameView);
        dtv.setText(mContactDetails.getFirstName());

        //Set contact last name //
        TextView ptv = (TextView) getActivity().findViewById(R.id.spotStateNameView);
        ptv.setText(mContactDetails.getLastName());

        //Set contact age
        TextView ageTV = (TextView) getActivity().findViewById(R.id.spotRatingView);
        ageTV.setText(mContactDetails.getAge());

        //Log.v(LOGCAT, "onActivityCreated() called - mContactDetails uses to setValues()");


    }


}
