package com.codepath.big_heart;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.codepath.big_heart.Fragments.HomeFragment;
import com.codepath.big_heart.Fragments.MapsFragment;
import com.codepath.big_heart.Fragments.ProfileFragment;
import com.codepath.big_heart.model.Post;

import java.io.Serializable;
import java.util.List;

public class TimelineActivity extends AppCompatActivity {

    private List<Post> mPosts;

    ImageButton ibHome;
    ImageButton ibProfile;
    ImageButton ibMaps;
    public HomeFragment homeFragment;
    public MapsFragment mapsFragment;
    public ProfileFragment profileFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        ibMaps = (ImageButton) findViewById(R.id.ibMaps);
        ibHome = (ImageButton) findViewById(R.id.ibHome);
        ibProfile = (ImageButton) findViewById(R.id.ibProfile);

        // define your fragments here
        homeFragment = new HomeFragment();
        mapsFragment = new MapsFragment();
        profileFragment = new ProfileFragment();

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

        ibHome.setImageResource(R.drawable.home);

        Fragment fragment = homeFragment;

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.FragmentPlace, fragment);

        ft.commit();

    }


    public void changeFragment(View view){
        Fragment fragment;

        if (view == findViewById(R.id.ibHome)) {

            ibMaps.setImageResource(R.drawable.maps);
            ibHome.setImageResource(R.drawable.home_tab);
            ibProfile.setImageResource(R.drawable.profile);

            fragment = homeFragment;

            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.FragmentPlace, fragment);

            ft.commit();


        } else if (view == findViewById(R.id.ibMaps)) {

            ibMaps.setImageResource(R.drawable.maps_tab);
            ibHome.setImageResource(R.drawable.home);
            ibProfile.setImageResource(R.drawable.profile);

            fragment = mapsFragment;

            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.FragmentPlace, fragment);

            ft.commit();

        } else if (view == findViewById(R.id.ibProfile)) {


            ibMaps.setImageResource(R.drawable.maps);
            ibHome.setImageResource(R.drawable.home);
            ibProfile.setImageResource(R.drawable.profile_tab);

            fragment = profileFragment;

            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.FragmentPlace, fragment);

            ft.commit();

        } else if (view == findViewById(R.id.ibCompose)) {


            ibMaps.setImageResource(R.drawable.maps);
            ibHome.setImageResource(R.drawable.home);
            ibProfile.setImageResource(R.drawable.profile);

  //          fragment = profileFragment;

            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
  //          ft.replace(R.id.FragmentPlace, fragment);

            ft.commit();
        }



    }


//    public void showDetailsFor(Serializable post) {
//        // create intent for the new activity
//        Intent intent = new Intent(this, PostDetailsActivity.class);
//        // serialize the post using parceler, use its short name as a key
//        intent.putExtra(Post.class.getSimpleName(), (Serializable) post);
//        // show the activity
//        startActivityForResult(intent,123);
//    }

    public void showProfileFragment(Post post) {
        Fragment fragment;

//        ibAdd.setImageResource(R.drawable.instagram_new_post_outline_24);
//        ibHome.setImageResource(R.drawable.instagram_home_outline_24);
//        ibProfile.setImageResource(R.drawable.instagram_user_filled_24);
//        ibSettings.setImageResource(R.drawable.ufi_save);

        fragment = profileFragment;

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.FragmentPlace, fragment);

        ft.commit();
    }

    public void showDetailsFor(Serializable post) {
        // create intent for the new activity
        Intent intent = new Intent(this, PostDetailsActivity.class);
        // serialize the post using parceler, use its short name as a key
        intent.putExtra(Post.class.getSimpleName(), (Serializable) post);
        // show the activity
        startActivityForResult(intent,123);
    }
}
