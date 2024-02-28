package com.nikhil.nicapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.nikhil.nicapp.api.ProductApiList;
import com.nikhil.nicapp.api.RetrofitClient;
import com.nikhil.nicapp.databinding.ActivityApiBinding;
import com.nikhil.nicapp.model.Product;
import com.nikhil.nicapp.model.ProductResponse;
import com.nikhil.nicapp.recyclerview.ProductAdapter;
import com.nikhil.nicapp.recyclerview.SpaceItemDecorationHorizontal;
import com.nikhil.nicapp.recyclerview.SpaceItemDecorationVertical;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiActivity extends AppCompatActivity {

    private ActivityApiBinding binding;
    private ProductAdapter adapter;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_api);
        binding = ActivityApiBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        setUpProductRecyclerView();
    }

    private void setUpProductRecyclerView() {
        fetchProducts();
        linearLayoutManager = new LinearLayoutManager(this);
        binding.recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new ProductAdapter(Collections.emptyList());
        binding.recyclerView.setAdapter(adapter);
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing);
        RecyclerView.ItemDecoration itemDecoration = new SpaceItemDecorationVertical(this, spacingInPixels);
        binding.recyclerView.addItemDecoration(itemDecoration);
    }

    private void fetchProducts() {
        RetrofitClient.getRetrofitInstance().create(ProductApiList.class).getProducts().enqueue(new Callback<ProductResponse>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                List<Product> products = response.body().get();
                if (products != null && !products.isEmpty()) {
                    adapter.setProductList(products);
                    adapter.notifyDataSetChanged();
                } else
                    Snackbar.make(binding.recyclerView, "Something went wrong.Please check your internet connection", Snackbar.LENGTH_INDEFINITE).show();
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                Log.d("okhttp ApiActivity", "onFailure: " + t.getLocalizedMessage());
                Snackbar.make(binding.recyclerView, "Something went wrong.Please check your internet connection", Snackbar.LENGTH_INDEFINITE).show();
            }
        });
    }
}