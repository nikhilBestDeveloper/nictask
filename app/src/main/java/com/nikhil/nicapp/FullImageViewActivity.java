package com.nikhil.nicapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nikhil.nicapp.model.Product;
import com.nikhil.nicapp.recyclerview.FullImageAdapter;
import com.nikhil.nicapp.recyclerview.SpaceItemDecorationHorizontal;

import java.util.ArrayList;

public class FullImageViewActivity extends AppCompatActivity {
    public static final String EXTRA_IMAGE_URL = "extra_image_url";
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView recyclerView;
    private FullImageAdapter adapter;
    private ArrayList<String> imageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_image_view);
        imageList = getIntent().getStringArrayListExtra(EXTRA_IMAGE_URL);
        recyclerView = findViewById(R.id.recycler_view);
        setUpImagesRecyclerView();
    }

    private void setUpImagesRecyclerView() {
        linearLayoutManager = new LinearLayoutManager(recyclerView.getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new FullImageAdapter(this, imageList);
        recyclerView.setAdapter(adapter);
        int spacingInPixels = recyclerView.getResources().getDimensionPixelSize(R.dimen.spacing);
        RecyclerView.ItemDecoration itemDecoration = new SpaceItemDecorationHorizontal(recyclerView.getContext(), spacingInPixels);
        recyclerView.addItemDecoration(itemDecoration);
    }
}