//Michelle Goldman
//MDF3 1601
//Updated 1/20/2016

package com.michellegoldman.widget4.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class ContactWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ContactWidgetViewFactory(getApplicationContext(), intent);
    }
}
