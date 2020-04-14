package com.padaliya.phv1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.padaliya.phv1.Fragment.HomeFragment;
import com.padaliya.phv1.Fragment.MapFragment;
import com.padaliya.phv1.Fragment.ProfileFragment;
import com.padaliya.phv1.Fragment.SearchFragment;

public class HomeActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    Fragment selectedFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationSelectedItemListener);

        if(selectedFragment != null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeFragment()).commit();
        }
    }

     private BottomNavigationView.OnNavigationItemSelectedListener navigationSelectedItemListener =
             new BottomNavigationView.OnNavigationItemSelectedListener() {
                 @Override
                 public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                     switch (item.getItemId()){
                         case R.id.nav_home :
                             selectedFragment = new HomeFragment();
                             break;
                         case R.id.nav_search :
                             selectedFragment = new SearchFragment();
                             break;
                         case R.id.nav_add :
                             selectedFragment = null ;
                             startActivity(new Intent(HomeActivity.this, PostActivity.class));
                             break;
                         case R.id.nav_map :
                             selectedFragment = new MapFragment();
                             break;

                         case R.id.nav_profile :
                             SharedPreferences.Editor editor = getSharedPreferences("PREFS",MODE_PRIVATE).edit();
                             editor.putString("profileid", FirebaseAuth.getInstance().getCurrentUser().getUid());
                             editor.apply();
                             selectedFragment = new ProfileFragment();
                             break;
                     }
                     if(selectedFragment != null){
                         getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment).commit();
                     }

                     return true;
                 }
             };
}
