package com.example.locationapidemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.provider.Telephony;
import android.telephony.SmsManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity  implements OnMapReadyCallback {
    int PERMISSION_ID = 44,REQUEST_CHECK_SETTINGS=14;
    FusedLocationProviderClient mFusedLocationClient;
    Button locationTaostBtn, sendMessageBtn;
    LocationResult location;
    Geocoder geocoder;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        handleActions();

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        getLastLocation();
        requestNewLocationData();
    }
    void viewMapFragment(){

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(MainActivity.this);
    }
    void init(){
        locationTaostBtn = findViewById(R.id.locationTaostBtn);
        sendMessageBtn = findViewById(R.id.sendMessageBtn);
        geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        sendMessageBtn.setEnabled(false);
    }
    void handleActions(){
        locationTaostBtn.setOnClickListener((view)->{
            requestNewLocationData();
            //viewMapFragment();
        });
        sendMessageBtn.setOnClickListener((view)->{

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) //At least KitKat
            {
                String defaultSmsPackageName = Telephony.Sms.getDefaultSmsPackage(MainActivity.this);

                Intent sendIntent = new Intent(Intent.ACTION_SEND);
                sendIntent.setType("text/plain");
                sendIntent.putExtra(Intent.EXTRA_TEXT, "i'm here "+ getLocationText());
                sendIntent.putExtra("address"  , "01032280773");

                if (defaultSmsPackageName != null)
                {
                    sendIntent.setPackage(defaultSmsPackageName);
                }
                startActivity(sendIntent);

            }
            else //For early versions, do what worked for you before.
            {
                Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                sendIntent.setData(Uri.parse("sms:"));
                sendIntent.putExtra("address"  , "01032280773");
                sendIntent.putExtra("sms_body", "i'm here "+ getLocationText());
                startActivity(sendIntent);
            }


        });
    }

    private String getLocationText(){
        String cityName = "";
        String counteryName= "";

        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(location.getLastLocation().getLatitude(), location.getLastLocation().getLongitude(), 1);
            cityName = addresses.get(0).getAddressLine(0);
            counteryName = addresses.get(0).getCountryName();


        } catch (IOException e) {
            e.printStackTrace();
        }

        return  counteryName+" "+cityName;
    }

    @SuppressLint("MissingPermission")
    private void getLastLocation(){
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                mFusedLocationClient.getLastLocation().addOnCompleteListener(
                        (task) -> {
                            Location location = task.getResult();
                            if (location == null) {
                                task.addOnFailureListener((e)->{
                                    try {
                                        // Show the dialog by calling startResolutionForResult(),
                                        // and check the result in onActivityResult().
                                        ResolvableApiException resolvable = (ResolvableApiException) e;
                                        resolvable.startResolutionForResult(MainActivity.this,
                                                REQUEST_CHECK_SETTINGS);
                                    } catch (IntentSender.SendIntentException sendEx) {
                                        // Ignore the error.
                                    }
                                });
                                //requestNewLocationData();
                            } else {
                               // latTextView.setText(location.getLatitude()+"");
                               // lonTextView.setText(location.getLongitude()+"");

                            }
                        }
                );
            } else {
                Toast.makeText(this, "Turn on location", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            requestPermissions();
        }
    }

    @SuppressLint("MissingPermission")
    private void requestNewLocationData(){

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(0);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.requestLocationUpdates(
                mLocationRequest, mLocationCallback,
                Looper.myLooper()
        );

    }

    private LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            //latTextView.setText(mLastLocation.getLatitude()+"");
            //lonTextView.setText(mLastLocation.getLongitude()+"");
            location = locationResult;
            Toast.makeText(MainActivity.this,"Latitiude = "+mLastLocation.getLatitude()+" Longitude = "+mLastLocation.getLongitude(), Toast.LENGTH_LONG).show();
            if (locationResult.getLastLocation() != null){
                sendMessageBtn.setEnabled(true);
                viewMapFragment();
            }
        }
    };

    private boolean checkPermissions() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                PERMISSION_ID
        );
    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //getLastLocation();
                requestNewLocationData();
            }
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        if (checkPermissions()) {
            getLastLocation();
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng loc = new LatLng(location.getLastLocation().getLatitude(), location.getLastLocation().getLongitude());
        mMap.addMarker(new MarkerOptions().position(loc).title("My Location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, 15));

    }
}
