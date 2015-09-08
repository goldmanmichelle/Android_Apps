package com.michellegoldman.j2w3b;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.michellegoldman.j2w3b.fragments.ItemViewFragment;

//ACTIVITY GOALS: 1. View item detail.  2. Ability to delete item and update list and stored data.
// 3. Return to list activity via button.  4. Share the item via button using implicit intent.

public class ItemViewActivity extends Activity implements ItemViewFragment.ItemViewListener {

    //MEMBER VARIABLES//
    private final String TAG = "ITEMVIEWACTIVITY.TAG";

    private Item mItem;
    private int mDelete;

    public static final String ITEMEXTRA = "com.michellegoldman.j2w3b.Item";
    public static final String DELETEEXTRA = "com.michellegoldman.j2w3b.DeleteE";

    //INTERFACES//
    @Override
    public Item getItem() {
        return mItem;
    }

    @Override
    public int getDelete(){
        return mDelete;
    }

    @Override
    public void deleteItem() {
        Intent returnDeleteIntent = new Intent();
        returnDeleteIntent.putExtra(ItemListActivity.DELETEITEMEXTRA, mDelete);
        setResult(RESULT_OK, returnDeleteIntent);
        finish();
    }

    //ACTIVITY METHODS//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_view);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction().add(R.id.container, new ItemViewFragment()).commit();
        }

        Intent viewIntent = getIntent();
        mItem = (Item) viewIntent.getSerializableExtra(ITEMEXTRA);
        mDelete = viewIntent.getIntExtra(DELETEEXTRA, 0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.item_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //INTENTS//
    public void goToListActivity(View view) {

        //Explicit intent to load Add Item Activity//
        Intent intent = new Intent(this, ItemListActivity.class);
        startActivity(intent);
    }

    public void implicitSendText (View view) {

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, "Check out my yard sale item!");
        intent.setType("text/plain");
        startActivity(intent);
    }

}
