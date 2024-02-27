package com.nikhil.nicapp.recyclerview;

import androidx.recyclerview.widget.RecyclerView;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.nikhil.nicapp.R;
import com.nikhil.nicapp.databinding.ItemShimmerBinding;

public class ShimmerViewHolder extends RecyclerView.ViewHolder {
    public ShimmerFrameLayout shimmerLayout;

    public ShimmerViewHolder(ItemShimmerBinding binding) {
        super(binding.getRoot());
        shimmerLayout = binding.shimmerLayout;
    }
}
