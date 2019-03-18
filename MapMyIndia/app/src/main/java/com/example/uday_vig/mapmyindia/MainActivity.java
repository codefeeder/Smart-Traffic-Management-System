package com.example.uday_vig.mapmyindia;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.mapbox.mapboxsdk.MapmyIndia;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.annotations.PolylineOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdate;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mmi.services.account.MapmyIndiaAccountManager;

import java.util.Arrays;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {

    private Button mButton;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private Location mLocation;
    public Context context;
    public LatLng[] arr = new LatLng[2];

    MapboxMap mMapboxMap;
    MapView mapView;

    Iterable<LatLng> iterable = new Iterable<LatLng>() {
        @Override
        public Iterator<LatLng> iterator() {
            Iterator<LatLng> iterator = Arrays.asList(arr).iterator();
            return iterator;
        }
    };

    private static final int LOCATION_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MapmyIndiaAccountManager.getInstance().setMapSDKKey("Your Map SDK Key");
        MapmyIndiaAccountManager.getInstance().setRestAPIKey("Your Rest API Key");
        MapmyIndiaAccountManager.getInstance().setAtlasGrantType("client_credentials");
        MapmyIndiaAccountManager.getInstance().setAtlasClientId("Your Atlas Client ID");
        MapmyIndiaAccountManager.getInstance().setAtlasClientSecret("Your Atlas Client Secret");
        MapmyIndiaAccountManager.getInstance().setAtlasAPIVersion("1.3.7");
        MapmyIndia.getInstance(getApplicationContext());

        setContentView(R.layout.activity_main);

        mapView = findViewById(R.id.mapView);

        mButton = findViewById(R.id.button);

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        context = this;

        /*LatLng obj = new LatLng(40.921288, 40.668916);
        arr[0] = obj;

        LatLng obj2 = new LatLng(40.922610, 40.669872);
        arr[1] = obj2;

        LatLng obj3 = new LatLng(40.923450, 40.671286);
        arr[2] = obj3;

        LatLng obj4 = new LatLng(40.924118, 40.673142);
        arr[3] = obj4;*/

        LatLng obj = new LatLng(40.921288, 40.668916);
        arr[0] = obj;

        LatLng obj2 = new LatLng(40.922310, 40.668916);
        arr[1] = obj2;

        setListener();

//        new GetData().execute("https://apis.mapmyindia.com/advancedmaps/v1/Your Map API Key/distance?center=40.743966,40.117798%7c&pts=40.603924,40.294510");

        /*ArrayList coordinates=new ArrayList<>();

        coordinates.add(Point.fromLngLat(40.745146,40.116973));
        coordinates.add(Point.fromLngLat(40.604128, 40.294529));
        new MapmyIndiaDistanceLegacy.Builder()
                .setCenter(Point.fromLngLat(40.23,40.1231))
                .setCoordinates(coordinates)
                .build().enqueueCall(new Callback<LegacyDistanceResponse>() {
            @Override
            public void onResponse(Call<LegacyDistanceResponse> call, Response<LegacyDistanceResponse> response) {
                //handle response
                Log.e("Distance1", "onResponse: " + response.body().getResults().get(0).getLength());
                Log.e("Distance2", "onResponse: " + response.body().getResults().get(1).getLength());
                long dist = response.body().getResults().get(0).getLength() - response.body().getResults().get(1).getLength();
                Log.e("Final Distance", "onResponse: " + dist);
            }
            @Override
            public void onFailure(Call<LegacyDistanceResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });*/

        /*ArrayList coordinates=new ArrayList<>();

        coordinates.add(Point.fromLngLat(40.25,40.9231));
        new MapmyIndiaDistanceLegacy.Builder()
                .setCenter(Point.fromLngLat(40.23,40.1231))
                .setCoordinates(coordinates)
                .build().enqueueCall(new Callback<LegacyDistanceResponse>() {
            @Override
            public void onResponse(Call<LegacyDistanceResponse> call, Response<LegacyDistanceResponse> response) {
                //handle response
                Log.e("Distance1", "onResponse: " + response.body().getResults().get(0).getDuration());
            }
            @Override
            public void onFailure(Call<LegacyDistanceResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });*/
    }

    private void setListener(){
        Toast.makeText(context, "Entered", Toast.LENGTH_SHORT).show();
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION},
                            LOCATION_REQUEST);
                }else{
                    mFusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            mLocation = location;
                            CameraUpdate cameraUpdate = new CameraUpdate() {
                                @Nullable
                                @Override
                                public CameraPosition getCameraPosition(@NonNull MapboxMap mapboxMap) {
                                    CameraPosition position = new CameraPosition.Builder()
                                            .target(new LatLng(location.getLatitude(), location.getLongitude()))
                                            .zoom(18)
                                            .tilt(0)
                                            .build();
                                    return position;
                                }
                            };

                            mapView.getMapAsync(new OnMapReadyCallback() {
                                @Override
                                public void onMapReady(MapboxMap mapboxMap) {
                                    mMapboxMap = mapboxMap;

                                    mapboxMap.moveCamera(cameraUpdate);
                                    mapboxMap.addPolyline(new PolylineOptions()
                                            .addAll(iterable)
                                            .color(Color.parseColor("#3bb2d0"))
                                            .width(10));

                                    MarkerOptions  markerOptions =  new MarkerOptions().position(arr[0]);
                                    Marker marker1 = mapboxMap.addMarker(markerOptions);

                                    MarkerOptions  markerOptions2 =  new MarkerOptions().position(arr[1]);
                                    Marker marker2 = mapboxMap.addMarker(markerOptions2);

                                    markerOptions.setTitle("");
                                    markerOptions.setSnippet("");
                                }
                            });

                            Log.e("POLO", "onSuccess: " + location.getLatitude() + " " + location.getLongitude());
                            new GetData(1, arr, context).execute("https://apis.mapmyindia.com/advancedmaps/v1/Your Map API Key/distance?center=" + arr[0].getLatitude() + "," + arr[0].getLongitude() + "%7c&pts=" + arr[1].getLatitude() + "," + arr[1].getLongitude());
                        }
                    });
                }
            }
        });
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            switch (requestCode){
                case LOCATION_REQUEST:
                    setListener();
            }
        }
    }

    @Override
    public void onBackPressed() {

    }
}
