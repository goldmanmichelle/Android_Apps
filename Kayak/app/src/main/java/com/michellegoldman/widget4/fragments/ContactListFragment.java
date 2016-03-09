//Michelle Goldman
//MDF3 1601
//Updated 1/20/2016

package com.michellegoldman.widget4.fragments;

import android.app.Activity;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.michellegoldman.widget4.Contact;
import com.michellegoldman.widget4.ContactAdapter;
import com.michellegoldman.widget4.MainActivity;
import com.michellegoldman.widget4.R;
import com.michellegoldman.widget4.ViewActivity;

import java.util.ArrayList;

/**
 * Created by mgoldman on 10/13/15.
 */
public class ContactListFragment extends ListFragment{
    public static final String TAG = "CONTACTLISTFRAGMENT.TAG";
    public static final  String CONTACT_DETAILS = "com.michellegoldman.CONTACT_DETAILS";

    private Contact mContactLists = new Contact();
    private ArrayList<Contact> mContactArray = new ArrayList<>();
    private ArrayList<Contact> mContactList = new ArrayList<>();
    private final int CREATECODE = 101;
    private contactLists mLists;

    public ContactListFragment(){

    }

    //INTERFACES//
    public interface contactLists {
         void returnNewArray (ArrayList<Contact> contactArray);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (activity instanceof contactLists) {
            mLists = (contactLists) activity;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_contact_list, container, false);

        ((MainActivity) getActivity()).loadSavedData();

        return rootView;
    }



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final View view = getView();
        assert view != null;

        Log.v(TAG, "onActivityCreated() called loadSavedData()");


    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Intent detailView = new Intent(getActivity(), ViewActivity.class);
        int arrayPosition = position;
        mContactArray = ((MainActivity) getActivity()).getArray();
        mContactLists = ((MainActivity) getActivity()).getArray().get(position);
        detailView.putExtra(CONTACT_DETAILS, mContactLists);
        detailView.putExtra("array", mContactArray);
        detailView.putExtra("position", arrayPosition);
        startActivityForResult(detailView, CREATECODE);

        Log.v(TAG, "onListItemClick() called sending list item details to ContactDetailFragement");

    }


    public void updateList(){
        setListAdapter(new ContactAdapter(getActivity(), MainActivity.mContactList));
        Log.v(TAG, "updateList() called");

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CREATECODE){
            updateList();
            ContactListFragment frag = (ContactListFragment) getFragmentManager().findFragmentById(R.id.container);
            mContactList = (ArrayList<Contact>) data.getSerializableExtra("array");
            mLists.returnNewArray(mContactList);
            frag.setListAdapter(new ContactAdapter(getActivity(), mContactList));
        }
        Log.v(TAG, "onActivityResult() called" );

    }

}
