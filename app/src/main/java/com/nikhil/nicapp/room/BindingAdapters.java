package com.nikhil.nicapp.room;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.nikhil.nicapp.R;
import com.nikhil.nicapp.model.Person;

public class BindingAdapters {

    @BindingAdapter("imageUri")
    public static void setImageUri(ImageView imageView, String imageUri) {
        if (TextUtils.isEmpty(imageUri)) {
            imageView.setVisibility(View.GONE);
        } else {
            imageView.setVisibility(View.VISIBLE);
            Glide.with(imageView.getContext())
                    .load(imageUri)
                    .circleCrop()
                    .placeholder(R.drawable.placeholder) // Placeholder image while loading
                    .into(imageView);
        }
    }

    @BindingAdapter("hideIfZeroLatLng")
    public static void hideIfZeroLatLng(LinearLayout layout, Person person) {
        if (person.getLatitude() == 0 || person.getLongitude() == 0 || person.getAddress().isEmpty()) {
            layout.setVisibility(View.GONE);
        } else {
            layout.setVisibility(View.VISIBLE);
        }
    }
}