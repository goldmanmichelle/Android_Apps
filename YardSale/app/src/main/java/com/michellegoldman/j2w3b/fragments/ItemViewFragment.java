package com.michellegoldman.j2w3b.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.michellegoldman.j2w3b.Item;
import com.michellegoldman.j2w3b.ItemListActivity;
import com.michellegoldman.j2w3b.R;


public class ItemViewFragment extends Fragment {

    //MEMBER VARIABLES//
    private final String TAG = "ITEMVIEWFRAGMENT.TAG";

    private ItemViewListener mListener;

    private Button mButton = null;


    //CONSTRUCTORS//
    public ItemViewFragment() {

    }

    //INTERFACES//
    public interface ItemViewListener{
        public Item getItem();
        public int getDelete();
        public void deleteItem();
    }


    //FRAGMENT METHODS//
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ItemViewListener) {
            mListener = (ItemViewListener) activity;
        } else {
            throw new IllegalArgumentException(" must implement the ItemViewListener");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Inflate item list fragment//
        View rootView = inflater.inflate(R.layout.fragment_item_view, container, false);
        return rootView;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //Set item name view//
        TextView ntv = (TextView) getView().findViewById(R.id.itemName);
        ntv.setText(mListener.getItem().getiName());

        //Set item description view//
        TextView dtv = (TextView) getView().findViewById(R.id.itemDesc);
        dtv.setText(mListener.getItem().getiDescription());

        //Set item price view//
        TextView ptv = (TextView) getView().findViewById(R.id.itemPrice);
        ptv.setText(mListener.getItem().getiPrice());

        if(mListener.getDelete() > 0){
            Button deleteButton = (Button) getView().findViewById(R.id.deleteButton);
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.deleteItem();
                }
            });
        }

        //Create Return to Item List button & listener//
        mButton = (Button) getActivity().findViewById(R.id.returnToItemList);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), ItemListActivity.class);
                startActivity(i);
            }
        });
    }

}
