//Michelle Goldman
//Java 2 Week 4 1504
//April 28, 2015

package com.michellegoldman.multiapp.fragments;

import android.app.Activity;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.michellegoldman.multiapp.DetailActivity;
import com.michellegoldman.multiapp.MainActivity;
import com.michellegoldman.multiapp.R;
import com.michellegoldman.multiapp.data.Survey;
import com.michellegoldman.multiapp.data.SurveyAdapter;

import java.util.ArrayList;

/**
 * Created by mgoldman on 4/29/15.
 */
public class MainFragment extends ListFragment {

    //MEMBER VARIABLES//
    public static final String TAG = "MainFragment.TAG";
    private Survey mSurveyLists = new Survey();
    private ArrayList<Survey> mSurveyArray = new ArrayList<Survey>();
    private ArrayList<Survey> mSurveyList = new ArrayList<>();
    private final int CREATECODE = 101;
    private surveyLists mLists;
    private ActionMode mAction;
    private int mSurveySelect = -1;

    //CONSTRUCTORS//
    public  MainFragment() {

    }

    //INTERFACES//
    public interface surveyLists {
        public void returnNewArray (ArrayList<Survey> surveyArray);
    }


    //FRAGMENT METHODS//
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof surveyLists){
            mLists = (surveyLists) activity;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        ListView sList = (ListView) rootView.findViewById(android.R.id.list);
        sList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if(mAction != null){
                    return false;
                }
                mSurveySelect = position;
                mAction = getActivity().startActionMode(mActionCallback);
                return true;
            }
        });
        return rootView;
    }

    //ACTIONMODE METHOD//
    private ActionMode.Callback mActionCallback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MenuInflater inflate = mode.getMenuInflater();
            inflate.inflate(R.menu.survey_delete, menu);
            return true;
        }
        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }
        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_delete:
                    Log.i(TAG, "User: " + mSurveySelect);
                    deleteSurvey();
                    mode.finish();
                    return true;
                default:
                    return false;
            }
        }
        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mAction = null;
        }
    };

    public void deleteSurvey(){
        MainActivity.mSurveyList.remove(mSurveySelect);
        ((MainActivity) getActivity()).updateData();
        updateList();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final View view = getView();
        assert view != null;
        ((MainActivity) getActivity()).loadSavedData();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Intent detailView = new Intent(getActivity(), DetailActivity.class);
        int arrayPosition = position;
        mSurveyArray = ((MainActivity) getActivity()).getArray();
        mSurveyLists = ((MainActivity) getActivity()).getArray().get(position);
        detailView.putExtra("survey_details", mSurveyLists);
        detailView.putExtra("array", mSurveyArray);
        detailView.putExtra("position", arrayPosition);
        startActivityForResult(detailView, CREATECODE);
    }


    public void updateList(){
        setListAdapter(null);
        setListAdapter(new SurveyAdapter(getActivity(), MainActivity.mSurveyList));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CREATECODE){
            updateList();
            MainFragment frag = (MainFragment) getFragmentManager().findFragmentById(R.id.container);
            mSurveyList = (ArrayList<Survey>) data.getSerializableExtra("array");
            mLists.returnNewArray(mSurveyList);
            frag.setListAdapter(new SurveyAdapter(getActivity(), mSurveyList));
        }
    }
}
