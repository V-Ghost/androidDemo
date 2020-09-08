package com.example.bryan.stork;


import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


/**
 * A simple {@link Fragment} subclass.
 */
public class home extends Fragment implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    GoogleMap googleMap;
    private FusedLocationProviderClient mF;
    private static final String flocation = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String clocation = Manifest.permission.ACCESS_COARSE_LOCATION;
    FusedLocationProviderClient mf;

    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    Location location;
    MapView  mView;

    public home() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);


        getPermission();
//        if (mGoogleApiClient == null) {
//            mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
//                    .addConnectionCallbacks(this)
//                    .addOnConnectionFailedListener(this)
//                    .addApi(LocationServices.API)
//                    .build();
//        }
//        if (mGoogleApiClient != null) {
//            mGoogleApiClient.connect();
//        }

        SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.maps);
        supportMapFragment.getMapAsync(this);

        return v;
    }

    @Override
    public void onMapReady(final GoogleMap cgoogleMap) {


        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
//        googleMap.setMyLocationEnabled(true);

//        location = googleMap.getMyLocation();
//
//        if (location != null) {
//
//            // latitude and longitude
//            LatLng lat = new LatLng(location.getLatitude(), location.getLongitude());
//
//            //double latitude = 17.385044;
//            //double longitude = 78.486671;
//            //LatLng lat = new LatLng(latitude,longitude);
//
//            // create marker
//
//            MarkerOptions marker = new MarkerOptions().position(
//                    lat).title("Hello Maps");
//
//            // Changing marker icon
//            marker.icon(BitmapDescriptorFactory
//                    .defaultMarker(BitmapDescriptorFactory.HUE_ROSE));
//
//            // adding marker
//            googleMap.addMarker(marker);
//
//            CameraPosition cameraPosition = new CameraPosition.Builder()
//                    .target(lat).zoom(12).build();
//
//
//            googleMap.animateCamera(CameraUpdateFactory
//                    .newCameraPosition(cameraPosition));
//
//        }
    }





    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 1234;
    private boolean permission ;
    boolean result = false;
    private void getPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(getActivity())
                        .setTitle("Location Permission Needed")
                        .setMessage("This app needs the Location permission, please accept to use location functionality")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(getActivity(),
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION );
                            }
                        })
                        .create()

                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION );
            }
        }


    }




    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permission = false;

        switch (requestCode){
            case MY_PERMISSIONS_REQUEST_LOCATION:
                if (grantResults.length > 0 ){
                    for (int i = 0 ; i < grantResults.length; i ++){
                        if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
                            permission = false;
                            return;

                        }
                        permission = true;


                    }

                }

        }
    }


    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }





    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }

        //Place current location marker
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        Log.i("latlng",Double.toString(Double.parseDouble(location.getLatitude() +"   " + Double.toString(location.getLatitude()))));

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Position");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        mCurrLocationMarker = googleMap.addMarker(markerOptions);

        //move map camera
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,11));

    }
    //public void getDevice(){
//
//    Task location = null;
//    try {
//        location = mf.getLastLocation();
//        location.addOnCompleteListener(new OnCompleteListener() {
//            @Override
//            public void onComplete(@NonNull Task task) {
//                if(task.isSuccessful()) {
//                    Location cl = (Location) task.getResult();
//                    LatLng latLng = new LatLng(cl.getLatitude(), cl.getLongitude());
//                    Log.i("cllatlng",Double.toString(Double.parseDouble(cl.getLatitude() +"   " + Double.toString(cl.getLatitude()))));
//
//                    MarkerOptions markerOptions = new MarkerOptions();
//                    markerOptions.position(latLng);
//                    markerOptions.title("Current Position");
//                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
//                    mCurrLocationMarker = googleMap.addMarker(markerOptions);
//
//                    //move map camera
//                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,13f));
//                }else{
//
//                    Toast.makeText(getActivity(),"bed",Toast.LENGTH_SHORT);
//                }
//            }
//        });
//
//    } catch (SecurityException e) {
//       Toast.makeText(getActivity(),"error",Toast.LENGTH_SHORT);
//    }
//
//
//    if (mCurrLocationMarker != null) {
//        mCurrLocationMarker.remove();
//    }
//
//
//    // Add a marker in Paris and move the camera
//
//
//}
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }



}


