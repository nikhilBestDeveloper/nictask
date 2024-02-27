package com.nikhil.nicapp;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private double selectedLatitude;
    private double selectedLongitude;
    private GoogleMap googleMap;
    private String addressLine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapFragment);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
        findViewById(R.id.select).setOnClickListener(view -> {
            sendResult();
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap map) {
        googleMap = map;
        googleMap.setOnMapClickListener(latLng -> {
            selectedLatitude = latLng.latitude;
            selectedLongitude = latLng.longitude;
            getAddressFromLocation();
            LatLng centerLatLng = googleMap.getCameraPosition().target;
            googleMap.clear();
            MarkerOptions markerOptions = new MarkerOptions()
                    .position(centerLatLng)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
                    .title("Location");
            googleMap.addMarker(markerOptions);
        });
        LatLng defaultLocation = new LatLng(19.5944, 81.6615);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 15.0f));
    }

    private void getAddressFromLocation() {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(selectedLatitude, selectedLongitude, 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address address = addresses.get(0);
                addressLine = address.getAddressLine(0);
                Toast.makeText(this, "Address: " + addressLine, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "No address found for this location", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error retrieving address", Toast.LENGTH_SHORT).show();
        }
    }

    public void sendResult() {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("latitude", selectedLatitude);
        resultIntent.putExtra("longitude", selectedLongitude);
        resultIntent.putExtra("address", addressLine);
        setResult(RESULT_OK, resultIntent);
        finish();
    }
}