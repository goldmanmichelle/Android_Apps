//Michelle Goldman
//MDF3 1601
//Updated 1/20/2016

package com.michellegoldman.widget4;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.michellegoldman.widget4.fragments.ContactListFragment;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class MainActivity extends ActionBarActivity implements ContactListFragment.contactLists, View.OnClickListener {

    private final String LOGCAT = "MAINACTIVITY.LOGCAT";
    private final int FORM_ACTIVITY_REQUEST = 101;
    public static final  String CONTACT_DETAILS = "com.michellegoldman.CONTACT_DETAILS";

    public static ArrayList<Contact> mContactList = new ArrayList<>();
    private static final String FILENAME = "contact.txt";
    Contact mContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction().add(R.id.container, new ContactListFragment()).commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_form, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here.
        int id = item.getItemId();

        //Access Contact Form
        if (id == R.id.action_form) {
            createForm();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

    }

    private void createForm() {
        //Create add contact form activity intent expecting results back once form is saved.
        Intent create = new Intent(this, FormActivity.class);
        create.putExtra(CONTACT_DETAILS, mContactList);
        startActivityForResult(create, FORM_ACTIVITY_REQUEST);
        
        Log.v(LOGCAT, "createForm() called");

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Receive contact detail results back from add contact form activity.
        if (requestCode == FORM_ACTIVITY_REQUEST && resultCode == RESULT_OK) {
            mContactList = (ArrayList<Contact>) data.getSerializableExtra(CONTACT_DETAILS);
            ContactListFragment frag = (ContactListFragment) getFragmentManager().findFragmentById(R.id.container);
            frag.setListAdapter(new ContactAdapter(this, mContactList));
        }

        Log.v(LOGCAT, "onActivityResult() called");

    }

    public ArrayList<Contact> getArray(){
        Log.v(LOGCAT, "getArray() called");

        return mContactList;

    }

    @Override
    public void returnNewArray(ArrayList<Contact> contactArray) {
        Log.v(LOGCAT, "returnNewArray() called");

        mContactList = contactArray;

    }

    //Load saved contacts from persistent storage.
    public void loadSavedData() {
        mContactList.clear();

        ContactListFragment frag = (ContactListFragment) getFragmentManager().findFragmentById(R.id.container);

        try{
            FileInputStream input = openFileInput(FILENAME);
            ObjectInputStream stream = new ObjectInputStream(input);
            while (input.available() != 0){
                mContact = (Contact) stream.readObject();
                mContactList.add(mContact);
            }
            stream.close();
            frag.setListAdapter(new ContactAdapter(getApplicationContext(), mContactList));
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.v(LOGCAT, "loadSavedData() called");

    }



}
