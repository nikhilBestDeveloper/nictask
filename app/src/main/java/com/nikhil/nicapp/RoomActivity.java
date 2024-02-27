package com.nikhil.nicapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.nikhil.nicapp.databinding.ActivityRoomBinding;
import com.nikhil.nicapp.model.Person;
import com.nikhil.nicapp.model.SwipeToDeleteCallback;
import com.nikhil.nicapp.recyclerview.PersonAdapter;
import com.nikhil.nicapp.recyclerview.SpaceItemDecorationVertical;
import com.nikhil.nicapp.room.AppDatabase;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class RoomActivity extends AppCompatActivity {

    private ActivityRoomBinding binding;
    private AppDatabase appDatabase;
    List<Person> persons = Collections.emptyList();
    private LinearLayoutManager linearLayoutManager;
    private PersonAdapter adapter;
    private ThreadPoolExecutor executor;
    private SwipeToDeleteCallback.SwipeToDeleteListener deleteListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.add.setOnClickListener(openActivity(PersonFormActivity.class));
        appDatabase = AppDatabase.getInstance(getApplicationContext());
        setUpPersonRecyclerView();
    }

    private void setUpPersonRecyclerView() {
        executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(3);
        linearLayoutManager = new LinearLayoutManager(this);
        binding.recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new PersonAdapter(RoomActivity.this, persons);
        binding.recyclerView.setAdapter(adapter);
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing);
        RecyclerView.ItemDecoration itemDecoration = new SpaceItemDecorationVertical(this, spacingInPixels);
        binding.recyclerView.addItemDecoration(itemDecoration);
        deleteListener = new SwipeToDeleteCallback.SwipeToDeleteListener() {
            @Override
            public void onSwipe(int position) {
                delete(persons.get(position));
            }
        };
        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(this, deleteListener);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchHelper.attachToRecyclerView(binding.recyclerView);
        loadPersons();
    }

    private void delete(Person person) {
        executor.execute(() -> {
            appDatabase.personAppDao().delete(person);
            persons = appDatabase.personAppDao().getAllPersons();
            runOnUiThread(() -> {
                adapter.setPersonList(persons);
                adapter.notifyDataSetChanged();
                if (persons.isEmpty()) {
                    showSnackBarWithButton("No Persons in Database", openActivity(PersonFormActivity.class), "Add");
                }
            });
        });
    }

    private <T extends Activity> View.OnClickListener openActivity(Class<T> activityClass) {
        return view -> {
            Intent intent = new Intent(RoomActivity.this, activityClass);
            startActivity(intent);
        };
    }

    private void loadPersons() {
        executor.execute(() -> {
            persons = appDatabase.personAppDao().getAllPersons();
            runOnUiThread(() -> {
                adapter.setPersonList(persons);
                adapter.notifyDataSetChanged();
                if (persons.isEmpty()) {
                    showSnackBarWithButton("No Persons in Database", openActivity(PersonFormActivity.class), "Add");
                }
            });
        });
    }

    private void showSnackBarWithButton(String text, View.OnClickListener onClickListener, String buttonText) {
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), text, Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction(buttonText, view -> {
            if (onClickListener != null)
                onClickListener.onClick(view);
            snackbar.dismiss();
        });
        snackbar.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadPersons();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        loadPersons();
    }

    private void showSnackBarWithButton(String text) {
        showSnackBarWithButton(text, null, "OK");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (executor != null)
            executor.shutdown();
    }
}