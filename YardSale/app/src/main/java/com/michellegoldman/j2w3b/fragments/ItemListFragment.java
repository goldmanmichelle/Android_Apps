package com.michellegoldman.j2w3b.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.michellegoldman.j2w3b.Item;
import com.michellegoldman.j2w3b.ItemAdapter;
import com.michellegoldman.j2w3b.R;

import java.util.ArrayList;


public class ItemListFragment extends Fragment {

    //MEMBER VARIABLES//
    private final String TAG = "ITEMLISTFRAGMENT.TAG";

    private ArrayList<Item> mItemList;

    private ItemListener mListener;

    //CONSTRUCTORS//
    public ItemListFragment() {

    }

    //INTERFACES//
    public interface ItemListener{
        public void viewItem(int position);
        public void deleteItem(int position);
        public ArrayList<Item> getItems();
    }


    //FRAGMENT METHODS//
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (activity instanceof ItemListener) {
            mListener = (ItemListener) activity;
        } else {
            throw new IllegalArgumentException(" must implement the ItemListener");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_item_list, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ListView itemListView = (ListView) getView().findViewById(R.id.itemList);
        ItemAdapter itemAdapter = new ItemAdapter(getActivity(), mListener.getItems());
        itemListView.setAdapter(itemAdapter);

        itemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mListener.viewItem(position);
            }
        });

        itemListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                mListener.deleteItem(position);
                return true;
            }
        });

    }

    public void updateItemList(){
        ListView itemList = (ListView) getView().findViewById(R.id.itemList);
        BaseAdapter updateItemAdapter = (BaseAdapter) itemList.getAdapter();
        updateItemAdapter.notifyDataSetChanged();
    }


}
