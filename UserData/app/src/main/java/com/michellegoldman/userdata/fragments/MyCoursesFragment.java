//Michelle Goldman
//CPMD 1215
//November 30, 2015
//Updated December 17, 2015


package com.michellegoldman.userdata.fragments;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.michellegoldman.userdata.LoginActivity;
import com.michellegoldman.userdata.R;
import com.michellegoldman.userdata.auxiliary.NetworkManager;
import com.michellegoldman.userdata.auxiliary.PollService;
import com.michellegoldman.userdata.data.CourseAdapter;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

/**
 * Created by mgoldman on 11/30/15.
 */
public class MyCoursesFragment extends ListFragment {

    private static final String TAG = "MyCoursesFragment";
    private TextView welcome;
    private TextView userName;
    protected List<ParseObject> mCourse;
    private ListView courseList;
    private CourseAdapter adapter;

    public MyCoursesFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_my_courses, container, false);

        //Check for network connection before trying to load courses
        if (NetworkManager.isConnected(getActivity())) {

            //Check to see if currentUser is already logged in
            ParseUser currentUser = ParseUser.getCurrentUser();
            if (currentUser != null) {
                welcome = (TextView)rootView.findViewById(R.id.welcome_textView);
                welcome.setText(getString(R.string.welcome_back_message));
                userName = (TextView) rootView.findViewById(R.id.currentUser_textView);
                userName.setText(ParseUser.getCurrentUser().getUsername());

                //Load currentUser's courses from Parse
                final ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Courses");
                query.orderByDescending("createdAt");
                query.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> course, ParseException e) {
                        if (e == null) {

                            mCourse = course;
                            adapter = new CourseAdapter(getActivity(), mCourse);
                            setListAdapter(adapter);
                        }

                    }
                });

            } else {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }

            //Set long click listener for courses to be deleted
            courseList = (ListView) rootView.findViewById(android.R.id.list);
            courseList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                    //((ParseObject) parent.getAdapter().getItem(position)).deleteInBackground();
                    ((ParseObject) parent.getAdapter().getItem(position)).deleteEventually();

                    adapter.notifyDataSetChanged();
                    updateList();

                    //Alert user course was successfully deleted
                    Toast.makeText(getActivity(), "Course deleted!", Toast.LENGTH_SHORT).show();

                    return true;
                }


            });

        } else {
            //Alert user there is no network connection
            Toast.makeText(getActivity(), "No network connection detected! Please check your WIFI connection in settings.", Toast.LENGTH_SHORT).show();

        }

        return rootView;
    }

    //Update course list
    public void updateList() {

        //Check for network connectivity before updating course list
        if (NetworkManager.isConnected(getActivity())) {
            //Load currentUser's courses from Parse
            final ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Courses");
            query.orderByDescending("createdAt");
            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> course, ParseException e) {
                    if (e == null) {
                        mCourse = course;
                        adapter = new CourseAdapter(getActivity(), mCourse);
                        setListAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                }
            });
        } else {
            //Alert user there is no valid data connection
            Toast.makeText(getActivity(), "No network connection detected! Please check your WIFI connection in settings.", Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        //Stop polling service to update parse data
        Intent i = new Intent(getActivity(), PollService.class);
        getActivity().stopService(i);
    }


}
