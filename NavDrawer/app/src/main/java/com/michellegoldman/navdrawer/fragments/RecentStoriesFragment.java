//Michelle Goldman
//Java 2 Week 4 1504
//April 29, 2015

package com.michellegoldman.navdrawer.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.michellegoldman.navdrawer.R;

/**
 * Created by mgoldman on 4/27/15.
 */
public class RecentStoriesFragment extends Fragment {

    public static final String TAG = "RecentStoriesFragment.TAG";

    public RecentStoriesFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recent_stories, container, false);
    }

    @Override
    public void onActivityCreated(final Bundle _savedInstanceState) {
        super.onActivityCreated(_savedInstanceState);

    }

}
