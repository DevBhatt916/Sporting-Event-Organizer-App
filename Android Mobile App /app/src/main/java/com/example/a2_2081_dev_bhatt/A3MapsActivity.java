package com.example.a2_2081_dev_bhatt;

import androidx.fragment.app.FragmentActivity;

import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.a2_2081_dev_bhatt.databinding.ActivityA3MapsBinding;

import java.util.Locale;

public class A3MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityA3MapsBinding binding;

    private String countryToFocus;
    private String categoryName; // New field for category name

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityA3MapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Get the location and category name from the intent
        countryToFocus = getIntent().getExtras().getString("cLocation", "Mumbai");
        categoryName = getIntent().getExtras().getString("cName", "Category"); // Default category name

        Toast.makeText(this, countryToFocus, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        findCountryMoveCamera();
    }

    private void findCountryMoveCamera() {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        final LatLng defaultLocation = new LatLng(-37.8136, 144.9631); // Melbourne coordinates

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            geocoder.getFromLocationName(countryToFocus, 1, addresses -> {
                if (!addresses.isEmpty()) {
                    runOnUiThread(() -> {
                        LatLng newAddressLocation = new LatLng(
                                addresses.get(0).getLatitude(),
                                addresses.get(0).getLongitude()
                        );

                        // Add a marker with category name and move the camera
                        mMap.addMarker(new MarkerOptions().position(newAddressLocation).title(categoryName));
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(newAddressLocation));
                        mMap.animateCamera(CameraUpdateFactory.zoomTo(10f));
                    });
                } else {
                    runOnUiThread(() -> {
                        Toast.makeText(this, "Category location not found", Toast.LENGTH_SHORT).show();
                        //move to default location
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(defaultLocation));
                        mMap.animateCamera(CameraUpdateFactory.zoomTo(10f));
                    });
                }
            });
        }
    }
}
