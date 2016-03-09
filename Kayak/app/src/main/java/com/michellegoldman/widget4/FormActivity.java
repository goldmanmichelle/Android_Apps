//Michelle Goldman
//MDF3 1601
//Updated 1/20/2016

package com.michellegoldman.widget4;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.michellegoldman.widget4.fragments.AddContactFragment;
import com.michellegoldman.widget4.fragments.ContactListFragment;
import com.michellegoldman.widget4.widget.ContactWidgetProvider;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class FormActivity extends ActionBarActivity implements AddContactFragment.contactLists {

    private final String LOGCAT = "FORMACTIVITY.LOGCAT";
    private static final String FILENAME = "contact.txt";
    private static final  String CONTACT_DETAILS = "com.michellegoldman.CONTACT_DETAILS";
    public  static final  String ACTION_ADD_CONTACT = "com.michellegoldman.ACTION_ADD_CONTACT";
    private final int FORM_ACTIVITY_REQUEST = 101;

    private  ArrayList<Contact> mContactList = new ArrayList<>();
    private ArrayList<Contact> mContactArray = new ArrayList<>();

    private Contact mContact;
    ContactAdapter adapter;
    private Button cancelButton;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction().add(R.id.container, new AddContactFragment()).commit();
        }

        cancelButton = (Button) findViewById(R.id.cancelButton);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Handle menu click actions

        //Save Contact
        if (item.getItemId() == R.id.action_save_form){

            //Load contact data from file
            loadContacts();
            Log.v(LOGCAT, "loadContacts() called");

            //Create the contact
            createNewContact();
            Log.v(LOGCAT, "createContact() called");

            //Save new contact
            saveNewContact();
            Log.v(LOGCAT, "saveNewContact() called");

            //Send contact detail intent results back to main activity
            sendToMain();
            Log.v(LOGCAT, "sendToMain() called");

            //Update widget
            updateWidget();
            Log.v(LOGCAT, "updateWidget() called");

            //Return to MainActivity
            finish();

            //Reset Form
            } else  if (item.getItemId() == R.id.action_reset_form) {
            AddContactFragment frag = (AddContactFragment) getFragmentManager().findFragmentById(R.id.container);
            frag.resetForm();
        }

        return  false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Receive contact detail results back from add contact form activity.
        if (requestCode == FORM_ACTIVITY_REQUEST && resultCode == RESULT_OK) {
            ContactListFragment frag = (ContactListFragment) getFragmentManager().findFragmentById(R.id.container);
            mContactList = (ArrayList<Contact>) data.getSerializableExtra(CONTACT_DETAILS);
            frag.setListAdapter(new ContactAdapter(this, mContactList));
            adapter.notifyDataSetChanged();
            frag.updateList();
        }

        Log.v(LOGCAT, "onActivityResult() called");
    }

    //Load saved contacts from persistent storage.
    public void loadContacts() {
        try{
            FileInputStream input = openFileInput(FILENAME);
            ObjectInputStream stream = new ObjectInputStream(input);
            while (input.available() != 0){
                mContact = (Contact) stream.readObject();
                mContactList.add(mContact);
            }
            stream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.v(LOGCAT, "load contacts from file");

    }


    public void createNewContact() {
        AddContactFragment frag = (AddContactFragment) getFragmentManager().findFragmentById(R.id.container);
        frag.createContact();
        Log.v(LOGCAT, "createContact()called from menu Save button");
    }

    public void saveNewContact() {
        try {
            FileOutputStream output = openFileOutput(FILENAME, Context.MODE_PRIVATE);
            ObjectOutputStream stream = new ObjectOutputStream(output);
            for (int i = 0; i < mContactList.size(); i++) {
                mContact = mContactList.get(i);
                stream.writeObject(mContact);
            }
            stream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.v(LOGCAT, "save contact to file");
    }



    public void sendToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(CONTACT_DETAILS, mContactList);
        setResult(RESULT_OK, intent);
    }


    @Override
    public void details(String _title, String _firstName, String _lastName, String _age) {

        mContactList.add(new Contact(_title, _firstName, _lastName, _age));

        Log.v(LOGCAT, "details() called using mContactlist.add");
    }


    //set device back button to run saveAndFinish
    @Override
    public void onBackPressed() {
        finish();
        Log.v(LOGCAT, "onBackPressed() called");

    }

    public void updateWidget(){
        //Update Widget
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] ids = appWidgetManager.getAppWidgetIds(new ComponentName(this, ContactWidgetProvider.class));

        if (ids.length > 0) {
            new ContactWidgetProvider().onUpdate(this, appWidgetManager, ids);
        }

        appWidgetManager.notifyAppWidgetViewDataChanged(ids, R.id.contact_List);

        Log.v(LOGCAT, "updateWidget() called");

    }

    public void cancelSpot(View view){
        //Explicit intent to return to home screen
        Intent addItemIntent = new Intent(this, MainActivity.class);
        startActivity(addItemIntent);
    }

}
