package com.michellegoldman.j2w3b;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.michellegoldman.j2w3b.fragments.AddItemFragment;
import com.michellegoldman.j2w3b.fragments.ItemListFragment;

import java.util.ArrayList;

//ACTIVITY GOALS: 1. View list of items.  2. Ability to delete item from list.  3. All items are stored to local data.

public class ItemListActivity extends Activity implements ItemListFragment.ItemListener, AddItemFragment.ItemSaveListener{

    //MEMBER VARIABLES//
    private final String TAG = "ITEMLISTACTIVITY.TAG";

    public static final int DELETEITEMREQUEST = 1;
    public static final String DELETEITEMEXTRA = "com.michellegoldman.j23wb.DeleteIE";
    public static final String SAVEITEM = "com.michellegoldman.j23wb.Save";


    private ArrayList<Item> mItemDataList;

    //INTERFACE METHODS//
    @Override
    public ArrayList<Item> getItems() {
        return mItemDataList;
    }

    @Override
    public void viewItem(int position) {
        //Load view fragment with all item elements//
        Intent viewIntent = new Intent(this, ItemViewActivity.class);
        viewIntent.putExtra(ItemViewActivity.ITEMEXTRA, mItemDataList.get(position));
        startActivity(viewIntent);

    }


    @Override
    public void deleteItem(int position){
        Intent deleteIntent = new Intent(this, ItemViewActivity.class);
        deleteIntent.putExtra(ItemViewActivity.ITEMEXTRA, mItemDataList.get(position));
        deleteIntent.putExtra(ItemViewActivity.DELETEEXTRA, position);
        startActivityForResult(deleteIntent, DELETEITEMREQUEST);
    }

    @Override
    public void saveItem() {

    }

    //ACTIVITY METHODS//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction().add(R.id.container, new ItemListFragment()).commit();
        }

        mItemDataList = new ArrayList<>();
        mItemDataList.add(new Item("Ukele", "Made of maple. Excellent condition.", "$100"));

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.item_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


   //ACTIVITY RESULTS//
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && requestCode == DELETEITEMREQUEST) {
            mItemDataList.remove(data.getIntExtra(DELETEITEMEXTRA, 0));
            ItemListFragment listFrag = (ItemListFragment) getFragmentManager().findFragmentById(R.id.container);
            listFrag.updateItemList();
        }
    }

    //INTENTS//
    public void implicitViewURL(View view) {
        //Implicit intent to load yardsalesearch website//
        String url = "http://www.yardsalesearch.com/";
        Intent loadURL = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(loadURL);
    }

    public void goToAddItemActivity(View view) {
        //Explicit intent to load Add Item Activity and receive data back//
        Intent addItemIntent = new Intent(this, AddItemActivity.class);
        startActivity(addItemIntent);
    }


}
