//Michelle Goldman
//MDF3 1601
//Updated 1/20/2016

package com.michellegoldman.widget4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class ContactAdapter extends BaseAdapter {

    private static final long CONSTANT_ID = 0x011145020L;

    Context mContext;
    ArrayList<Contact> mContactList;

    public ContactAdapter(Context mContext,  ArrayList<Contact> mContactList) {
        this.mContext = mContext;
        this.mContactList = mContactList;
    }


    @Override
    public int getCount() {
        return mContactList.size();
    }

    @Override
    public Object getItem(int position) {
        return mContactList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return CONSTANT_ID + position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.custom_list_contact, parent, false);
        }


        Contact contact = (Contact) getItem(position);

        TextView titleTV = (TextView) convertView.findViewById(R.id.spotParkNameView);
        TextView firstNameTV = (TextView) convertView.findViewById(R.id.spotCityNameView);
        TextView lastNameTv = (TextView) convertView.findViewById(R.id.spotStateNameView);
        TextView ageTV = (TextView) convertView.findViewById(R.id.spotRatingView);

        titleTV.setText(contact.getTitle());
        firstNameTV.setText(contact.getFirstName());
        lastNameTv.setText(contact.getLastName());
        ageTV.setText(contact.getAge());

        return convertView;
    }
}
