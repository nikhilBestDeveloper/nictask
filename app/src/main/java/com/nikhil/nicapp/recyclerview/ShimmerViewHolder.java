package com.nikhil.nicapp.recyclerview;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.nikhil.nicapp.R;
import com.nikhil.nicapp.databinding.ItemShimmerBinding;

public class ShimmerViewHolder extends RecyclerView.ViewHolder {
    public ShimmerFrameLayout shimmerLayout;

    public ShimmerViewHolder(View view) {
        super(view);
        shimmerLayout = view.findViewById(R.id.shimmer_layout);
    }

    public ShimmerViewHolder(ItemShimmerBinding binding) {
        super(binding.getRoot());
        shimmerLayout = binding.shimmerLayout;
    }
}
