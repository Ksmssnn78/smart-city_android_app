package com.example.smartcityapp2;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.smartcityapp2.databinding.ActivityMapsAirCheckBinding;

import java.io.IOException;
import java.util.List;

public class MapsActivity_air_check extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener, GoogleMap.OnMarkerDragListener {

    private GoogleMap mMap;
    private ActivityMapsAirCheckBinding binding;
    Geocoder geocoder;
    Marker mark;
    String p_name;
    TextView place, percent;
    private int requestcode = 10001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsAirCheckBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        geocoder = new Geocoder(MapsActivity_air_check.this);
        percent = (TextView)findViewById(R.id.show_percent);
        place = (TextView)findViewById(R.id.place_name);
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
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setOnMapLongClickListener(MapsActivity_air_check.this);
        mMap.setOnMarkerDragListener(MapsActivity_air_check.this);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, requestcode);
        }


        try {
            List<Address> addressList = geocoder.getFromLocationName("London", 1);
            if (addressList.size() > 0) {
                Address address = addressList.get(0);
                p_name = address.getAddressLine(0);
                LatLng latlng = new LatLng(address.getLatitude(), address.getLongitude());
                //mMap.addMarker(new MarkerOptions().position(latlng).title(address.getLocality()));
                mark = mMap.addMarker( new MarkerOptions().position(latlng).title(address.getLocality()));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 16));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        try {
            List<Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            if(addresses.size()>0)
            {
                Address address = addresses.get(0);
                p_name = address.getAddressLine(0);
                if(mark != null)
                {
                    mark.remove();
                }
                mark = mMap.addMarker(new MarkerOptions().position(latLng).title(p_name).draggable(true));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,16));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        LatLng latLng = marker.getPosition();
        try {
            List<Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            if(addresses.size()>0)
            {
                Address address = addresses.get(0);
                p_name = address.getAddressLine(0);
                marker.setTitle(p_name);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @SuppressLint({"MissingPermission", "MissingSuperCall"})
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == requestcode)
        {
            if(grantResults.length >0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {
                mMap.setMyLocationEnabled(true);
            }
            else{
                Toast.makeText(MapsActivity_air_check.this,"Permission Denied",Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void air_check(View v) {
        place.setText(p_name);
        String rslt = Double.toString(50.0+Math.round(Math.random()*30));
        percent.setText("'"+rslt+"'% air is cleaned\nin the selected location");
    }
}