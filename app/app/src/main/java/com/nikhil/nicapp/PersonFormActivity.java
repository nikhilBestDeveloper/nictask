package com.nikhil.nicapp;

import static com.nikhil.nicapp.utils.BitmapUtils.saveBitmap;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.snackbar.Snackbar;
import com.nikhil.nicapp.databinding.PersonFormBinding;
import com.nikhil.nicapp.model.Person;
import com.nikhil.nicapp.room.AppDatabase;

import java.io.ByteArrayOutputStream;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class PersonFormActivity extends AppCompatActivity implements OnMapReadyCallback {
    public static final String INTENT_PERSON = "person";
    private GoogleMap googleMap;
    private double selectedLatitude = 19.5944;
    private double selectedLongitude = 81.6615;
    private String selectedAddress = "";
    private AppDatabase appDatabase;
    private Uri selectedImageUri;

    @Override
    public void finish() {
        super.finish();
    }

    private static final int MAP_PICK_REQUEST = 2;
    private PersonFormBinding binding;
    private static final int PICK_IMAGE_REQUEST = 1;
    private ThreadPoolExecutor executor;
    Person personFromIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = PersonFormBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.save.setOnClickListener(view -> validateAndSave());
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapFragment);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
        binding.profileImage.setOnClickListener(selectImage());
        appDatabase = AppDatabase.getInstance(getApplicationContext());
        executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(1);
        personFromIntent = getIntent().getParcelableExtra(INTENT_PERSON);
        if (personFromIntent != null) {
            populate(personFromIntent);
        }
    }

    private void populate(Person person) {
        binding.nameEditText.setText(person.getName());
        binding.isMarriedCheckBox.setChecked(person.isMarried());
        if (person.getGender().equals(getString(R.string.male))) {
            binding.maleRadioButton.setChecked(true);
        } else {
            binding.femaleRadioButton.setChecked(true);
        }
        if (person.getImageUri() != null) {
            selectedImageUri = Uri.parse(person.getImageUri());
            setImage();
        }
        selectedLatitude = person.getLatitude();
        selectedLongitude = person.getLongitude();
        selectedAddress = person.getAddress();
        if (googleMap != null) {
            onMapReady(googleMap);
        }
    }

    private void validateAndSave() {
        if (binding.nameEditText.getText().toString().isEmpty()) {
            showSnackBarWithButton("Name cannot be empty");
            return;
        }
        Person person = new Person();
        person.setName(binding.nameEditText.getText().toString());
        person.setLatitude(selectedLatitude);
        person.setAddress(selectedAddress);
        person.setLongitude(selectedLongitude);
        if (binding.maleRadioButton.isChecked()) {
            person.setGender("Male");
        } else {
            person.setGender("Female");
        }
        person.setMarried(binding.isMarriedCheckBox.isChecked());
        if (selectedImageUri != null)
            person.setImageUri(selectedImageUri.toString());
        savePersons(person);
    }

    private void savePersons(Person newPerson) {
        executor.execute(() -> {
            if (personFromIntent == null) {
                long insert = appDatabase.personAppDao().insert(newPerson);
                if (insert != -1) {
                    finish();
                } else {
                    showSnackBarWithButton("Something went wrong try again!!");
                }
            } else {
                newPerson.setId(personFromIntent.getId());
                int update = appDatabase.personAppDao().update(newPerson);
                if (update != -1) {
                    finish();
                } else {
                    showSnackBarWithButton("Something went wrong try again!!");
                }
            }

        });
    }

    private void showSnackBarWithButton(String text) {
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), text, Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("OK", view -> snackbar.dismiss());
        snackbar.show();
    }

    private void getLocation() {
        Intent intent = new Intent(PersonFormActivity.this, MapActivity.class);
        startActivityForResult(intent, MAP_PICK_REQUEST);
    }

    private View.OnClickListener selectImage() {
        return view -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(intent, PICK_IMAGE_REQUEST);
        };
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Glide.with(this)
                    .asBitmap()
                    .load(data.getData())
                    .override(300, 300) // Resize the image to 300x300 pixels
                    .into(new CustomTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            resource.compress(Bitmap.CompressFormat.JPEG, 50, baos); // Adjust compression quality as needed
                            byte[] compressedByteArray = baos.toByteArray();
                            Bitmap bitmap = BitmapFactory.decodeByteArray(compressedByteArray, 0, compressedByteArray.length);
                            selectedImageUri = saveBitmap(PersonFormActivity.this, bitmap);
                            setImage();
                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {

                        }
                    });
        }
        if (requestCode == MAP_PICK_REQUEST) {
            Log.d("okhttp PersonFormActivity", "onActivityResult: ${}" + resultCode + data);
            if (resultCode == RESULT_OK && data != null) {
                selectedLatitude = data.getDoubleExtra("latitude", 0.0);
                selectedLongitude = data.getDoubleExtra("longitude", 0.0);
                selectedAddress = data.getStringExtra("address");
                if (selectedAddress == null) {
                    selectedAddress = "";
                } else moveCamera();
            }
        }
    }

    private void setImage() {
        Glide.with(PersonFormActivity.this)
                .load(selectedImageUri)
                .circleCrop()
                .into(binding.profileImage);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap m) {
        googleMap = m;
        googleMap.getUiSettings().setAllGesturesEnabled(false);
        googleMap.setOnMapClickListener(latLng -> getLocation());
        moveCamera();
    }

    private void moveCamera() {
        LatLng targetLatLng = new LatLng(selectedLatitude, selectedLongitude);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(targetLatLng, 18f));
        binding.address.setText(selectedAddress);
    }
}