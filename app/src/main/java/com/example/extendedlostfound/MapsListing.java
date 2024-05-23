package com.example.extendedlostfound;

import androidx.fragment.app.FragmentActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.extendedlostfound.databinding.ActivityMapsListingBinding;

import java.util.ArrayList;

public class MapsListing extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsListingBinding binding;
    MyDatebase myDB;
    ArrayList<LatLng> coordinates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsListingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        myDB = new MyDatebase(MapsListing.this);
        coordinates = new ArrayList<>();

        // Retrieve coordinates from the database
        storeCoordinates();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void storeCoordinates() {
        Cursor cursor = myDB.readAllData();
        if (cursor.getCount() == 0) {
            Toast.makeText(MapsListing.this, "No data!", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                String latString = cursor.getString(5);
                String lngString = cursor.getString(6);

                if (latString != null && lngString != null && !latString.isEmpty() && !lngString.isEmpty()) {
                    try {
                        double lat = Double.parseDouble(latString);
                        double lng = Double.parseDouble(lngString);
                        coordinates.add(new LatLng(lat, lng));
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        cursor.close();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add markers for each coordinate
        for (LatLng coordinate : coordinates) {
            mMap.addMarker(new MarkerOptions().position(coordinate).title("Advert Location"));
        }

        // Move the camera to the first marker if there are any coordinates
        if (!coordinates.isEmpty()) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coordinates.get(0), 10));
        }
    }
}