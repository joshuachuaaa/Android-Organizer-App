package com.example.assignment1_fit2081_32781555.Activities;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.assignment1_fit2081_32781555.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.assignment1_fit2081_32781555.databinding.ActivityGoogleMapBinding;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class GoogleMapActivity extends FragmentActivity implements OnMapReadyCallback {
    private SupportMapFragment mapFragment;

    private Geocoder geocoder;

    private String countryToFocus;

    private String categoryTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityGoogleMapBinding binding = ActivityGoogleMapBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        geocoder = new Geocoder(this, Locale.getDefault());
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        countryToFocus = getIntent().getExtras().getString("categoryLocation","Australia");
        categoryTitle = getIntent().getExtras().getString("categoryTitle", "NULL");
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        findCountryMoveCamera(googleMap);
        //findCountryMoveCamera(googleMap);

    }

    private void findCountryMoveCamera(GoogleMap googleMap) {
        //Log.d("Moving Camera","TRUE");
        // getFromLocationName method works for API 33 and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Log.d("Geocoder", "Attempting to find location for: " + countryToFocus);
            geocoder.getFromLocationName(countryToFocus, 1, addresses -> {
                Log.d("Geocoder", "Received response: " + addresses);
                if (!addresses.isEmpty()) {
                    Log.d("Geocoder", "Addresses found, updating UI.");
                        runOnUiThread(() -> {

                            // define new LatLng variable using the first address from list of addresses
                            LatLng newAddressLocation = new LatLng(
                                    addresses.get(0).getLatitude(),
                                    addresses.get(0).getLongitude()
                            );

                            // just for reference add a new Marker
                            googleMap.addMarker(
                                    new MarkerOptions()
                                            .position(newAddressLocation)
                                            .title(categoryTitle)
                            );
                            // repositions the camera according to newAddressLocation
                            googleMap.moveCamera(CameraUpdateFactory.newLatLng(newAddressLocation));
                            googleMap.moveCamera(CameraUpdateFactory.zoomTo(10.0f));
                        });
                }
                else {
                        Log.d("Geocoder", "No addresses found, showing toast.");
                        runOnUiThread(() -> {
                                Toast.makeText(GoogleMapActivity.this, "Category Address Not Found!", Toast.LENGTH_SHORT).show();
                        });
                    }
            });
        }
        Log.d("DDDDDDDDDDDDDDDDDDDDDDDDDD","ONEEEEEEEEEE");

    }
}