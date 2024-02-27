package com.nikhil.nicapp.room;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.nikhil.nicapp.R;

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
}