package com.padaliya.phv1.Fragment;

import android.content.ContentProvider;
import android.os.Bundle;


import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.model.TileOverlay;
import com.google.maps.android.heatmaps.HeatmapTileProvider;




import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOverlay;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;

import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import com.google.android.gms.maps.model.TileOverlayOptions;

import com.padaliya.phv1.R;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class MapFragment extends Fragment  {

    MapView mMapView;
    private GoogleMap googleMap;
    HeatmapTileProvider mProvider ;

    private TileOverlay mOverlay;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        mMapView = view.findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;

                // For showing a move to my location button
             //  googleMap.setMyLocationEnabled(true);

                 //For dropping a marker at a point on the Map
               LatLng sydney = new LatLng(43.651070, -79.347015);
              // googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker Title").snippet("Marker Description"));

                addHeatMap();


                // For zooming automatically to the location of the marker
                CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(10).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        });


        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }


    private void addHeatMap() {
        List<LatLng> list = new ArrayList<>();
        list.add(new LatLng(43.651070, -79.347015));
        list.add(new LatLng(43.651070, -79.347015));
        list.add(new LatLng(43.651070, -79.347015));
        list.add(new LatLng(43.651070, -79.347015));
        list.add(new LatLng(43.651070, -79.347015));
        list.add(new LatLng(43.651070, -79.347015));
        list.add(new LatLng(43.651070, -79.347015));
        list.add(new LatLng(43.651070, -79.347015));
        list.add(new LatLng(43.651070, -79.347015));
        list.add(new LatLng(43.651070, -79.347015));
        list.add(new LatLng(43.651070, -79.347015));
        list.add(new LatLng(43.651070, -79.347015));
        list.add(new LatLng(43.651070, -79.347015));
        list.add(new LatLng(43.50, -79.347015));
        list.add(new LatLng(42.2070, -78.347015));
        list.add(new LatLng(40.11070, -73.347015));
        list.add(new LatLng(38.651070, -75.347015));
        list.add(new LatLng(35.50, -74.347015));
        list.add(new LatLng(47.2070, -72.347015));
        list.add(new LatLng(43.11070, -71.347015));
        list.add(new LatLng(43.651070, -74.347015));
        list.add(new LatLng(43.50, -75.7015));
        list.add(new LatLng(42.2070, -76.015));
        list.add(new LatLng(40.11070, -71.337015));
        list.add(new LatLng(38.651070, -74.347015));
        list.add(new LatLng(35.50, -76.347015));
        list.add(new LatLng(47.2070, -78.7015));
        list.add(new LatLng(43.11070, -77.347015));
        list.add(new LatLng(43.651070, -74.347015));
        list.add(new LatLng(43.50, -77.5));
        list.add(new LatLng(42.2070, -72.015));
        list.add(new LatLng(40.11070, -71.7015));
        list.add(new LatLng(38.651070, -74.0125));
        list.add(new LatLng(35.50, -75.66015));
        list.add(new LatLng(47.2070, -78.015));
        list.add(new LatLng(43.11070, -72.347015));

        mProvider = new HeatmapTileProvider.Builder()
                .radius(50)
                .data(list)
                .build();

        // Add a tile overlay to the map, using the heat map tile provider.
        mOverlay = googleMap.addTileOverlay(new TileOverlayOptions().tileProvider(mProvider));

    }





}
