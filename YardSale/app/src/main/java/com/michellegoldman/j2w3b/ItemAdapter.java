package com.michellegoldman.j2w3b;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by mgoldman on 4/23/15.
 */
public class ItemAdapter extends BaseAdapter {

    //Create variables//
    private static final long ID_CONSTANT = 3110092458L;
    Context mContext;
    ArrayList<Item> mItems;

    //Create Constructors//
    public ItemAdapter(Context context,  ArrayList<Item> items) {
        this.mContext = context;
        this.mItems = items;
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return ID_CONSTANT + position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.custom_list_item, null);
        //IF CRASHES HERE,  could also be:  View view = inflater.inflate(R.layout.custom_list_item, parent, false);

        Item item = mItems.get(position);

        //Create textView associated with the custom layout textView field//
        ImageView image = (ImageView) view.findViewById(R.id.customItemImage);
        image.setImageResource(R.mipmap.yardsale_sign);

        TextView nameTV = (TextView) view.findViewById(R.id.customItemName);
        nameTV.setText(item.getiName());

        //NumberFormat nFormat = NumberFormat.getCurrencyInstance();
        TextView priceTV = (TextView) view.findViewById(R.id.customItemPrice);
        priceTV.setText(item.getiPrice());

        return view;
    }
}
