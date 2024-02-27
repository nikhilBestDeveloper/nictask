package com.nikhil.nicapp.recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nikhil.nicapp.R;
import com.nikhil.nicapp.databinding.ItemProductBinding;
import com.nikhil.nicapp.databinding.ItemShimmerBinding;
import com.nikhil.nicapp.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Product> productList;

    private static final int VIEW_TYPE_ITEM = 0;
    private static final int VIEW_TYPE_SHIMMER = 1;

    private boolean showShimmer = true;


    public ProductAdapter(List<Product> productList) {
        this.productList = productList;
    }

    @Override
    public int getItemViewType(int position) {
        return showShimmer ? VIEW_TYPE_SHIMMER : VIEW_TYPE_ITEM;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_SHIMMER) {
            ItemShimmerBinding binding = ItemShimmerBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new ShimmerViewHolder(binding);
        } else {
            ItemProductBinding binding = ItemProductBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new ProductViewHolder(binding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ProductViewHolder) {
            Product product = productList.get(position);
            ((ProductViewHolder) holder).bind(product);
        } else {
            ShimmerViewHolder shimmerViewHolder = (ShimmerViewHolder) holder;
            shimmerViewHolder.shimmerLayout.startShimmer();
        }
    }

    @Override
    public int getItemCount() {
        return showShimmer ? 10 : productList.size();
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
       showShimmer = false;
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder {

        private final ItemProductBinding binding;
        private LinearLayoutManager linearLayoutManager;
        private ImageAdapter adapter;

        public ProductViewHolder(@NonNull ItemProductBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Product product) {
            binding.titleTextView.setText(product.getTitle());
            binding.priceTextView.setText(String.valueOf(product.getPrice()));
            binding.descriptionTextView.setText(product.getDescription());
            binding.brandTextView.setText(product.getBrand());
            binding.categoryTextView.setText(product.getCategory());
            binding.stockTextView.setText(String.valueOf(product.getStock()));
            binding.ratingTextView.setText(String.valueOf(product.getRating()));
            setUpProductImagesRecyclerView(product);
        }

        private void setUpProductImagesRecyclerView(Product product) {
            linearLayoutManager = new LinearLayoutManager(binding.recyclerView.getContext(), LinearLayoutManager.HORIZONTAL, false);
            binding.recyclerView.setLayoutManager(linearLayoutManager);
            adapter = new ImageAdapter(binding.recyclerView.getContext(), new ArrayList<>(product.getImages()));
            binding.recyclerView.setAdapter(adapter);
            int spacingInPixels = binding.recyclerView.getResources().getDimensionPixelSize(R.dimen.spacing);
            RecyclerView.ItemDecoration itemDecoration = new SpaceItemDecorationHorizontal(binding.recyclerView.getContext(), spacingInPixels);
            binding.recyclerView.addItemDecoration(itemDecoration);
        }

    }
}
