package com.padaliya.phv1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.maps.android.heatmaps.HeatmapTileProvider;
import com.padaliya.phv1.Model.Post;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class HazardmapActivity extends AppCompatActivity implements Serializable {

    MapView mMapView;
    private GoogleMap googleMap;

    HeatmapTileProvider mProvider ;

    private List<Post> postList;

    private TileOverlay mOverlay;
    Post post ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        readPosts();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hazardmap);

        mMapView = findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);
        postList = new ArrayList<>();

        post = (Post) getIntent().getSerializableExtra("post");
        Log.d("Haz",post.toString());

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;

                String [] latLong = post.getLocation().split(",");

                if(latLong.length > 0){
                    //For dropping a marker at a point on the Map
                    LatLng sydney = new LatLng(Double.parseDouble(latLong[0]),Double.parseDouble(latLong[1]));
                    MarkerOptions markerOptions =  new MarkerOptions() ;
                    markerOptions.position(sydney) ;
                    markerOptions.title(post.getDescription());
                    googleMap.addMarker(markerOptions);
                    addHeatMap();


                    // For zooming automatically to the location of the marker
                    CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(10).build();
                    googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                }

                // For showing a move to my location button
                //  googleMap.setMyLocationEnabled(true);




            }
        });

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
