//Michelle Goldman
//Java 2 Week 4 1504
//April 29, 2015

package com.michellegoldman.navdrawer.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.widget.TextView;

import com.michellegoldman.navdrawer.R;

/**
 * Created by mgoldman on 4/27/15.
 */
public class PrefFragment extends PreferenceFragment implements Preference.OnPreferenceClickListener{

    public static final String TAG = "PreferenceFragment.TAG";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Load preferences from the settings_pref.xml file//
        //Adding preferences from an XML replaces onCreateView//
        addPreferencesFromResource(R.xml.settings);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Preference prefClick = findPreference("DISABLE_DATA");
        prefClick.setOnPreferenceClickListener(this);
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {

        SharedPreferences cachePref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        TextView ctv = (TextView) getActivity().findViewById(R.id.prefTextView);
        ctv.setText(cachePref.getString("DISABLE_DATA", "Mobile data disabled!"));

        return true;
    }


}
