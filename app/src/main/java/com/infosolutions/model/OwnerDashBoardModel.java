package com.infosolutions.model;

import android.databinding.BindingAdapter;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;

public class OwnerDashBoardModel {

    public int menuicon;
    public String menuname;

    public OwnerDashBoardModel(int menuicon, String menuname) {
        this.menuicon = menuicon;
        this.menuname = menuname;
    }

    @BindingAdapter("android:imageUrl")
    public static void loadImage(View view, int menuicon) {
        ImageView imageView = (ImageView) view;
        imageView.setImageDrawable(ContextCompat.getDrawable(view.getContext(), menuicon));
    }
}
