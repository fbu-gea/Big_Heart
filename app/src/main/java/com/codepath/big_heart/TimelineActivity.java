package com.codepath.big_heart;

import android.os.Bundle;
import android.view.Menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.codepath.big_heart.fragments.HomeFragment;
import com.codepath.big_heart.fragments.MapsFragment;
import com.codepath.big_heart.fragments.ProfileFragment;

public class TimelineActivity extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        final FragmentManager fragmentManager = getSupportFragmentManager();

//        // define your fragments here
        final Fragment homeFragment = new HomeFragment();
        final Fragment postFragment = new MapsFragment();
        final Fragment profileFragment = new ProfileFragment();

//        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
//        getSupportActionBar().setDisplayShowTitleEnabled(false);
//
//        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        // handle navigation selection
//        bottomNavigationView.setOnNavigationItemSelectedListener(
//                new BottomNavigationView.OnNavigationItemSelectedListener() {
//                    @Override
//                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                        Fragment fragment;
//                        switch (item.getItemId()) {
//                            case R.id.miHome:
//                                fragment = homeFragment;
//                                break;
//                            case R.id.miAddPost:
//                                fragment = postFragment;
//                                break;
//                            case R.id.miProfile:
//                                fragment = profileFragment;
//                                break;
//                            default:
//                                fragment = homeFragment;
//                                break;
//                        }
//                        fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit();
//                        return true;
//                    }
//                });
//        // Set default selection
//        bottomNavigationView.setSelectedItemId(R.id.miHome);
    }
}
