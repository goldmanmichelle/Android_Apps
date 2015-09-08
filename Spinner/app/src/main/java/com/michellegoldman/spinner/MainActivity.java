//Michelle Goldman
//Java 2 Week 4 1504
//April 26, 2015

package com.michellegoldman.spinner;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.widget.ArrayAdapter;

import com.michellegoldman.spinner.fragments.Team1Fragment;
import com.michellegoldman.spinner.fragments.Team2Fragment;
import com.michellegoldman.spinner.fragments.Team3Fragment;
import com.michellegoldman.spinner.fragments.Team4Fragment;
import com.michellegoldman.spinner.fragments.Team5Fragment;
import com.michellegoldman.spinner.fragments.Team6Fragment;


public class MainActivity extends ActionBarActivity implements ActionBar.OnNavigationListener {

    /**
     * The serialization (saved instance state) Bundle key representing the
     * current dropdown position.
     */
    private static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up the action bar to show a dropdown list.
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);


        // Set up the dropdown list navigation in the action bar.
        actionBar.setListNavigationCallbacks(
                // Specify a SpinnerAdapter to populate the dropdown list.
                new ArrayAdapter<String>(
                        actionBar.getThemedContext(),
                        android.R.layout.simple_list_item_1,
                        android.R.id.text1,
                        new String[]{
                                getString(R.string.title_section1),
                                getString(R.string.title_section2),
                                getString(R.string.title_section3),
                                getString(R.string.title_section4),
                                getString(R.string.title_section5),
                                getString(R.string.title_section6),
                        }),
                this);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        // Restore the previously serialized current dropdown position.
        if (savedInstanceState.containsKey(STATE_SELECTED_NAVIGATION_ITEM)) {
            getSupportActionBar().setSelectedNavigationItem(
                    savedInstanceState.getInt(STATE_SELECTED_NAVIGATION_ITEM));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        // Serialize the current dropdown position.
        outState.putInt(STATE_SELECTED_NAVIGATION_ITEM,
                getSupportActionBar().getSelectedNavigationIndex());
    }


    //set fragment to team in actionbar spinner
    @Override
    public boolean onNavigationItemSelected(int position, long id) {
        // When the given dropdown item is selected, show its contents in the
        // container view.
        Team1Fragment team1Frag = new Team1Fragment();
        Team2Fragment team2Frag = new Team2Fragment();
        Team3Fragment team3Frag = new Team3Fragment();
        Team4Fragment team4Frag = new Team4Fragment();
        Team5Fragment team5Frag = new Team5Fragment();
        Team6Fragment team6Frag = new Team6Fragment();

        FragmentManager fm = getFragmentManager();

        if (position == 0) {
            fm.beginTransaction().replace(R.id.container, team1Frag, Team1Fragment.TAG).commit();

        } else if (position == 1) {
            fm.beginTransaction().replace(R.id.container, team2Frag, Team2Fragment.TAG).commit();

        } else if (position == 2) {
            fm.beginTransaction().replace(R.id.container, team3Frag, Team3Fragment.TAG).commit();

        } else if (position == 3) {
            fm.beginTransaction().replace(R.id.container, team4Frag, Team4Fragment.TAG).commit();

        } else if (position == 4) {
            fm.beginTransaction().replace(R.id.container, team5Frag, Team5Fragment.TAG).commit();

        } else if (position == 5) {
            fm.beginTransaction().replace(R.id.container, team6Frag, Team6Fragment.TAG).commit();

        }

        return true;
    }


}
