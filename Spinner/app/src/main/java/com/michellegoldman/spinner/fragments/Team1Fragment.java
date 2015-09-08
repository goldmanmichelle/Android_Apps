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
public class Team1Fragment extends Fragment {
    public static final String TAG = "Team1Fragment.TAG";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    // TODO: Rename and change types and number of parameters
    public static Team1Fragment newInstance(String param1, String param2) {
        Team1Fragment fragment = new Team1Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public Team1Fragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_team1, container, false);
    }

    @Override
    public void onActivityCreated(final Bundle _savedInstanceState) {
        super.onActivityCreated(_savedInstanceState);
        String[] team1 = {
                " Billie Ray, 1st Base",
                " Joal Hoade, 2nd Base",
                " Ronnie Eckart, 3rd Base",
                " Chris Barkley, Center",
                " Will Blakely, Right Field",
                " Jimmy Jonas, Left Field",
                " Gary Garner, Center Field",
                " Howie Mendel, Short Stop",
                " Perry Mathers, Pitcher",
        };
        ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1, team1);
        ListView lv =(ListView)getView().findViewById(R.id.team1List);
        lv.setAdapter(arrayAdapter);
    }


}
