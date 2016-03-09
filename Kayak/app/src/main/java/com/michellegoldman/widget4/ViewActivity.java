//Michelle Goldman
//MDF3 1601
//Updated 1/20/2016

package com.michellegoldman.widget4;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import com.michellegoldman.widget4.fragments.ContactDetailFragment;

import java.util.ArrayList;

public class ViewActivity extends ActionBarActivity  {

    private final String TAG = "CONTACTVIEWACTIVITY.TAG";
    public static final  String CONTACT_DETAILS = "com.michellegoldman.CONTACT_DETAILS";


    private ArrayList<Contact> mContactList = new ArrayList<>();
    private Contact mContactDetails = new Contact();
    private int position;
    Contact mContact;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction().add(R.id.container, new ContactDetailFragment()).commit();
        }

        Intent detailIntent = this.getIntent();
        mContactList = (ArrayList<Contact>) detailIntent.getSerializableExtra("array");
        position = detailIntent.getIntExtra("position", 0);
        mContactDetails = (Contact) detailIntent.getSerializableExtra(CONTACT_DETAILS);

        Log.v(TAG, "onCreate() detailIntent.getIntent called");


    }


    public Contact setValues() {
        return mContactDetails;
    }


    @Override
    public void onBackPressed() {
        //Handle user pressing device back button. Send data back to main activity to update list.
        Intent intent = new Intent();
        intent.putExtra("array", mContactList);
        setResult(RESULT_OK, intent);
        finish();
    }

}
