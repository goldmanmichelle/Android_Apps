//Michelle Goldman
//MDF3 1601
//Updated 1/20/2016

package com.michellegoldman.widget4.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.michellegoldman.widget4.Contact;
import com.michellegoldman.widget4.FormActivity;
import com.michellegoldman.widget4.R;
import com.michellegoldman.widget4.ViewActivity;

/**
 * Created by mgoldman on 10/14/15.
 */
public class ContactWidgetProvider extends AppWidgetProvider {

    public  static final  String ACTION_VIEW_DETAILS = "com.michellegoldman.ACTION_VIEW_DETAILS";
    public static final  String CONTACT_DETAILS = "com.michellegoldman.CONTACT_DETAILS";
    public  static final  String ACTION_ADD_CONTACT = "com.michellegoldman.ACTION_ADD_CONTACT";


    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        //Intent to get contact details
        if (intent.getAction().equals(ACTION_VIEW_DETAILS)) {
            Contact contact = (Contact) intent.getSerializableExtra(CONTACT_DETAILS);

            if (contact != null) {
                Intent details = new Intent(context, ViewActivity.class);
                details.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                details.putExtra(ViewActivity.CONTACT_DETAILS, contact);
                context.startActivity(details);
            }
        }

        //NEWLY ADDED
        //Intent to get form details
        if (intent.getAction().equals(ACTION_ADD_CONTACT)) {
            Contact contact = (Contact) intent.getSerializableExtra(CONTACT_DETAILS);

            Intent form = new Intent(context, ViewActivity.class);
            form.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            form.putExtra(ViewActivity.CONTACT_DETAILS, contact);
            context.startActivity(form);

        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);

        for (int i = 0; i < appWidgetIds.length; i++) {

            int widgetId = appWidgetIds[i];
            appWidgetManager = AppWidgetManager.getInstance(context);
            ComponentName mWidget = new ComponentName(context, ContactWidgetProvider.class);
            appWidgetIds = appWidgetManager.getAppWidgetIds(mWidget);

            Intent intent = new Intent(context, ContactWidgetService.class);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);

            RemoteViews widgetView = new RemoteViews(context.getPackageName(), R.layout.widget_contact);
            widgetView.setRemoteAdapter( R.id.contact_List, intent);
            widgetView.setEmptyView(R.id.contact_List, R.id.empty);
            appWidgetManager.notifyAppWidgetViewDataChanged(widgetId, R.layout.custom_list_contact);

            //Intent to view contact details
            Intent detailIntent = new Intent(ACTION_VIEW_DETAILS);
            PendingIntent pendIntent = PendingIntent.getBroadcast(context, 0, detailIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            widgetView.setPendingIntentTemplate(R.id.contact_List, pendIntent);

            //Intent to load add contact form
            widgetView.setOnClickPendingIntent(R.id.formButton, formIntent(context));
            appWidgetManager.updateAppWidget(widgetId, widgetView);

            //NEWLY ADDED. WAS COMMENTED OUT BEFORE
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.contact_List);

        }
    }


    public static PendingIntent formIntent(Context context) {
        //Intent to launch add contact form
        Intent formIntent = new Intent(context, FormActivity.class);
        formIntent.setAction(ACTION_ADD_CONTACT);
        return PendingIntent.getActivity(context, 0, formIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

}
