package com.nikhil.nicapp.recyclerview;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.nikhil.nicapp.FullImageViewActivity;
import com.nikhil.nicapp.R;

import java.util.ArrayList;

public class FullImageAdapter extends RecyclerView.Adapter<FullImageAdapter.ImageViewHolder> {

    private Context mContext;
    private ArrayList<String> mImageUrls;

    public FullImageAdapter(Context context, ArrayList<String> imageUrls) {
        this.mContext = context;
        this.mImageUrls = imageUrls;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_full_image_view, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        String imageUrl = mImageUrls.get(position);
        Glide.with(mContext)
                .load(imageUrl)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .fitCenter()
                .into(holder.imageView);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, FullImageViewActivity.class);
                intent.putStringArrayListExtra(FullImageViewActivity.EXTRA_IMAGE_URL, mImageUrls);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mImageUrls.size();
    }

    static class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageViewFull);
        }
    }
}
