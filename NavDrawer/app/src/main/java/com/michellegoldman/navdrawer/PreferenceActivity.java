//Michelle Goldman
//Java 2 Week 4 1504
//April 29, 2015

package com.michellegoldman.navdrawer;

import android.app.Activity;
import android.os.Bundle;

import com.michellegoldman.navdrawer.fragments.PrefFragment;

/**
 * Created by mgoldman on 4/27/15.
 */
public class PreferenceActivity extends Activity{

    @Override
    public void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        setContentView(R.layout.fragment_settings);

        getFragmentManager().beginTransaction().add(R.id.settingContainer, new PrefFragment()).commit();
    }
}
