package com.michellegoldman.j2w3b;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.michellegoldman.j2w3b.fragments.AddItemFragment;

//ACTIVITY GOALS: 1. Enter data for new item.  2. Save item while updating list and stored data.
// 3. Refresh listview.  4. Provide ability to save the state bundle to be reused if necessary.

public class AddItemActivity extends Activity implements AddItemFragment.ItemSaveListener {

    //MEMBER VARIABLES//
    private final String TAG = "ADDITEMACTIVITY.TAG";

    public static final String EXTRA_STRING_NAME = "com.michellegoldman.EXTRA_STRING_NAME";
    public static final String EXTRA_STRING_DESCRIPTION = "com.michellegoldman.EXTRA_STRING_DESCRIPTION";
    public static final String EXTRA_STRING_PRICE = "com.michellegoldman.EXTRA_STRING_PRICE";

    public static final String ADDITEM = "com.michellegoldman.j23wb.Add";

    //INTERFACES//
    @Override
    public void saveItem() {

    }

    //FRAGMENT METHODS//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction().add(R.id.container, new AddItemFragment()).commit();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        // Return to item list activity//
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //INTENTS//
    private void finishActivity() {
        Intent returnAddIntent = new Intent();
        returnAddIntent.putExtra(ADDITEM, "value");
        setResult(RESULT_OK, returnAddIntent);
        finish();
    }


}
