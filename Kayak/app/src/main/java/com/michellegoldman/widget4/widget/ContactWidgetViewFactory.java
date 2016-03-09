//Michelle Goldman
//MDF3 1601
//Updated 1/20/2016

package com.michellegoldman.widget4.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.michellegoldman.widget4.Contact;
import com.michellegoldman.widget4.ContactData;
import com.michellegoldman.widget4.R;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;


public class ContactWidgetViewFactory implements RemoteViewsService.RemoteViewsFactory {

    private final String TAG = "CONTACTWIDGETVIEWFACTORY.TAG";
    private static final String FILENAME = "contact.txt";

    private static final int ID_CONSTANT = 0x01010101;
    private List<Contact> mContactList;
    private int mAppWidgetId;
    private Contact mContact;
    private Context mContext;

    public ContactWidgetViewFactory(Context context, Intent intent) {
        mContext = context;
        mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        mContactList = new ArrayList<>();
    }

    @Override
    public void onCreate() {

        for (int i = 0; i < ContactData.mContactList.size(); i++) {
            mContactList = (ArrayList<Contact>) ContactData.mContactList;
        }

    }

    @Override
    public void onDataSetChanged() {
        loadSavedData();
    }

    //Load saved contacts from persistent storage.
    public void loadSavedData() {

        mContactList.clear();
        try{
            FileInputStream input = mContext.openFileInput(FILENAME);
            ObjectInputStream stream = new ObjectInputStream(input);
            while (input.available() != 0){
                mContact = (Contact) stream.readObject();
                mContactList.add(mContact);
            }
            stream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    @Override
    public void onDestroy() {
        mContactList.clear();
        mContactList = null;
    }

    @Override
    public int getCount() {

        return mContactList.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {

        Contact contact = mContactList.get(position);

        RemoteViews itemView = new RemoteViews(mContext.getPackageName(), R.layout.custom_list_contact);
        itemView.setTextViewText(R.id.spotParkNameView, contact.getTitle());
        itemView.setTextViewText(R.id.spotRatingView, contact.getAge());
        itemView.setTextViewText(R.id.spotCityNameView, contact.getFirstName());
        itemView.setTextViewText(R.id.spotStateNameView, contact.getLastName());

        Intent intent = new Intent();
        intent.putExtra(ContactWidgetProvider.CONTACT_DETAILS, contact);

        itemView.setOnClickFillInIntent(R.id.contact_item, intent);

        return itemView;

    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {

        return ID_CONSTANT + position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }


}
