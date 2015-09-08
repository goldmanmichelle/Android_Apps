//Michelle Goldman
//Java 2 Week 4 1504
//April 29, 2015

package com.michellegoldman.navdrawer.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.michellegoldman.navdrawer.R;

/**
 * Created by mgoldman on 4/27/15.
 */
public class ImageGalleryFragment extends Fragment {

    public static final String TAG = "ImageGalleryFragment.TAG";

    public ImageGalleryFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_image_gallery, container, false);
    }

    @Override
    public void onActivityCreated(final Bundle _savedInstanceState) {
        super.onActivityCreated(_savedInstanceState);
        ImageView imageView = (ImageView)getView().findViewById(R.id.galleryImage);
        imageView.setImageResource(R.mipmap.image_gallery);
    }

}
