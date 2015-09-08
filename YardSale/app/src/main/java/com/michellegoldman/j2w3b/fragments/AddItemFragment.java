package com.michellegoldman.j2w3b.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.michellegoldman.j2w3b.R;

public class AddItemFragment extends Fragment {

    //MEMBER VARIABLES//
    private final String TAG = "ADDITEMFRAGMENT.TAG";

    String newItemStr;
    EditText nameText;
    EditText descText;
    EditText priceText;

    //Create variables to save Add Item elements//
    private static final String SAVE_NAME = "AddItemFragment.SAVE_NAME";
    private static final String SAVE_DESCRIPTION = "AddItemFragment.SAVE_DESCRIPTION";
    private static final String SAVE_PRICE = "AddItemFragment.SAVE_PRICE";

    private String mName;
    private String mDescription;
    private String mPrice;

    private ItemSaveListener mListener;

    //CONSTRUCTORS//
    public static AddItemFragment newInstance(String itemName, String itemDescription, String itemPrice) {
        AddItemFragment fragment = new AddItemFragment();
        Bundle args = new Bundle();
        args.putString("itemName", itemName);
        args.putString("itemDescription", itemDescription);
        args.putString("itemPrice", itemPrice);
        fragment.setArguments(args);
        return fragment;
    }

    public AddItemFragment() {
        // Required empty public constructor
    }

    //INTERFACES//
    public interface ItemSaveListener{

        public void saveItem();
    }

    //FRAGMENT METHODS//
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ItemSaveListener) {
            mListener = (ItemSaveListener) activity;
        } else {
            throw new IllegalArgumentException(" must implement the ItemSaveListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Inflate item list fragment//
        View rootView = inflater.inflate(R.layout.fragment_add_item, container, false);
        return rootView;
    }


    @Override
    public void onActivityCreated(Bundle _savedInstanceState) {
        super.onActivityCreated(_savedInstanceState);

        if (_savedInstanceState != null) {
            mName = _savedInstanceState.getString(SAVE_NAME, "");
            mDescription = _savedInstanceState.getString(SAVE_DESCRIPTION, "");
            mPrice = _savedInstanceState.getString(SAVE_PRICE, "");


        }

    }

}
   /*
    //Write new item to list//
    Data.writeItemArray(getActivity(),sItemList);

    //read item array and refresh adapter//
    final ItemAdapter iAdapter = new ItemAdapter(getActivity(), Data.readItemArray(getActivity()));
        setListAdapter(iAdapter);




    //Dismiss Keyboard Automatically after submitting creation//
    InputMethodManager aHideKeyboard = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
    aHideKeyboard.hideSoftInputFromWindow(addItemTextView.getWindowToken(), 0);

    //Dismiss Keyboard Automatically after submitting creation//
    InputMethodManager dHideKeyboard = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
    dHideKeyboard.hideSoftInputFromWindow(itemDescTextView.getWindowToken(), 0);
*/