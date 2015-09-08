//Michelle Goldman
//Java 2 Week 4 1504
//April 26, 2015

package com.michellegoldman.spinner.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.michellegoldman.spinner.R;

/**
 * Created by mgoldman on 4/27/15.
 */
public class Team6Fragment extends Fragment {
    public static final String TAG = "Team6Fragment.TAG";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    // TODO: Rename and change types and number of parameters
    public static Team6Fragment newInstance(String param1, String param2) {
        Team6Fragment fragment = new Team6Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public Team6Fragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_team6, container, false);
    }

    @Override
    public void onActivityCreated(final Bundle _savedInstanceState) {
        super.onActivityCreated(_savedInstanceState);
        String[] team6 = {
                " Richie Thunder, 1st Base",
                " Edgar Booley, 2nd Base",
                " Vicktor Kharstan, 3rd Base",
                " Gil Rummis, Center",
                " Chad Morris, Right Field",
                " Moses Jerome, Left Field",
                " Kelly Carter, Center Field",
                " Luke Perry, Short Stop",
                " Stanley Duetsch, Pitcher",
        };
        ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1, team6);
        ListView lv =(ListView)getView().findViewById(R.id.team6List);
        lv.setAdapter(arrayAdapter);
    }


}
