package com.padaliya.phv1.Fragment;

import android.os.Bundle;


import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.model.TileOverlay;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.maps.android.heatmaps.HeatmapTileProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;

import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import com.google.android.gms.maps.model.TileOverlayOptions;

import com.padaliya.phv1.Model.Post;
import com.padaliya.phv1.R;

import java.util.ArrayList;
import java.util.List;

public class MapFragment extends Fragment  {

    MapView mMapView;
    private GoogleMap googleMap;
    HeatmapTileProvider mProvider ;

    private List<Post> postList;

    private TileOverlay mOverlay;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        readPosts();
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        mMapView = view.findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);

        postList = new ArrayList<>();


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
               LatLng sydney = new LatLng(43.79596586012583, -79.22372333851163);
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

        for(Post post : postList ){
            String [] latLong = post.getLocation().split(",");
            if(latLong.length > 0){
                Log.d("MapF",latLong.toString());
                list.add(new LatLng(Double.parseDouble(latLong[0]),Double.parseDouble(latLong[1])));
            }

        }

        mProvider = new HeatmapTileProvider.Builder()
                .radius(50)
                .data(list)
                .build();

        // Add a tile overlay to the map, using the heat map tile provider.
        mOverlay = googleMap.addTileOverlay(new TileOverlayOptions().tileProvider(mProvider));

    }
    private void readPosts(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Posts");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                postList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Post post = snapshot.getValue(Post.class);
                    postList.add(post);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }





}
