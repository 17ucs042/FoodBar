package com.appsaga.foodbar;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationClient;

    float distance_1;
    float distance_2;

    Location myLocation;

    LatLng Mc_Donalds1;
    LatLng Mc_Donalds2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        Mc_Donalds1 = new LatLng(26.916525, 75.809898);
        mMap.addMarker(new MarkerOptions().position(Mc_Donalds1).title("Mc D1"));
        final Location location1 = new Location("location1");
        location1.setLatitude(26.916525d);
        location1.setLongitude(75.809898d);

        Mc_Donalds2 = new LatLng(26.903797, 75.792806);
        mMap.addMarker(new MarkerOptions().position(Mc_Donalds2).title("Mc D2"));
        final Location location2 = new Location("location2");
        location2.setLatitude(26.903797d);
        location2.setLongitude(75.792806d);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            //    ActivityCompat#requestPermissions
            String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
            ActivityCompat.requestPermissions(this, permissions, 1);
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
        }
        mMap.setMyLocationEnabled(true);

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.

                        if (location != null) {

                            myLocation = location;

                            Log.d("Yes", "Yes");
                            Log.d("Latitude 3: ",Double.toString(myLocation.getLatitude()));
                            Log.d("Longitude 3: ",Double.toString(myLocation.getLongitude()));

                            distance_1 = location1.distanceTo(myLocation);
                            distance_2 = location2.distanceTo(myLocation);

                            checkClosest(distance_1,distance_2);
                        }
                    }
                });

        Log.d("check ", "Checkpoint 1");

       /*CameraPosition cameraPosition = new CameraPosition.Builder().target(Mc_Donalds1).zoom(20).build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));*/

        Log.d("Latitude 1: ",Double.toString(location1.getLatitude()));
        Log.d("Longitude 1: ",Double.toString(location1.getLongitude()));

        Log.d("Latitude 2: ",Double.toString(location2.getLatitude()));
        Log.d("Longitude 2: ",Double.toString(location2.getLongitude()));

        //Log.d("Latitude 3: ",Double.toString(myLocation.getLatitude()));
        //Log.d("Longitude 3: ",Double.toString(myLocation.getLongitude()));
    }

    public void checkClosest(float distance_1,float distance_2)
    {
        if(distance_1 < distance_2)
        {
            CameraPosition cameraPosition1 = new CameraPosition.Builder().target(Mc_Donalds1).zoom(20).build();
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition1));
        }
        else
        {
            CameraPosition cameraPosition1 = new CameraPosition.Builder().target(Mc_Donalds2).zoom(20).build();
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition1));
        }
    }
}
