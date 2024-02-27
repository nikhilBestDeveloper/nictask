package com.nikhil.nicapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.nikhil.nicapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        com.nikhil.nicapp.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        ActionBar actionBar = getSupportActionBar();
        binding.buttonApi.setOnClickListener(openActivity(ApiActivity.class));
        binding.buttonRoom.setOnClickListener(openActivity(RoomActivity.class));
    }

    private <T extends Activity> View.OnClickListener openActivity(Class<T> activityClass) {
        return view -> {
            Intent intent = new Intent(MainActivity.this, activityClass);
            startActivity(intent);
        };
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // Handle the back button press
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}