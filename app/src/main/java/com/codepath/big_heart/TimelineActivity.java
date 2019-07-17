package com.codepath.big_heart;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.codepath.big_heart.fragments.ProfileFragment;
import com.codepath.big_heart.model.Post;

import java.io.Serializable;
import java.util.List;import com.codepath.big_heart.model.Post;

public class TimelineActivity extends AppCompatActivity {

    private List<Post> mPosts;

    ImageButton ibHome;
    ImageButton ibProfile;
    ImageButton ibMaps;
 //   public FeedFragment feedFragment;
//    public AddPhotoFragment addPhotoFragment;
    public ProfileFragment profileFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        ibMaps = (ImageButton) findViewById(R.id.ibMaps);
        ibHome = (ImageButton) findViewById(R.id.ibHome);
        ibProfile = (ImageButton) findViewById(R.id.ibProfile);

        ibHome.setImageResource(R.drawable.home);

 //       feedFragment = new FeedFragment();
  //      addPhotoFragment = new AddPhotoFragment();
   //     profileFragment = new ProfileFragment();

 //       Fragment fragment = feedFragment;

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
 //       ft.replace(R.id.FragmentPlace, fragment);

        ft.commit();

    }


    public void changeFragment(View view){
        Fragment fragment;

        if (view == findViewById(R.id.ibHome)) {

 //           ibAdd.setImageResource(R.drawable.instagram_new_post_outline_24);
  //          ibHome.setImageColor(R.drawable.instagram_home_filled_24);
            ibProfile.setImageResource(R.drawable.instagram_user_outline_24);
  //          ibSettings.setImageResource(R.drawable.ufi_save);

            fragment = feedFragment;

            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.FragmentPlace, fragment);

            ft.commit();


        } else if (view == findViewById(R.id.ibAdd)) {

            ibAdd.setImageResource(R.drawable.instagram_new_post_filled_24);
            ibHome.setImageResource(R.drawable.instagram_home_outline_24);
            ibProfile.setImageResource(R.drawable.instagram_user_outline_24);
            ibSettings.setImageResource(R.drawable.ufi_save);

            fragment = addPhotoFragment;

            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.FragmentPlace, fragment);

            ft.commit();

        } else if (view == findViewById(R.id.ibProfile) || view == findViewById(R.id.ibProfilePic)) {


            ibAdd.setImageResource(R.drawable.instagram_new_post_outline_24);
            ibHome.setImageResource(R.drawable.instagram_home_outline_24);
            ibProfile.setImageResource(R.drawable.instagram_user_filled_24);
            ibSettings.setImageResource(R.drawable.ufi_save);

            fragment = profileFragment;

            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.FragmentPlace, fragment);

            ft.commit();

        } else if (view == findViewById(R.id.ibSettings)) {


            ibAdd.setImageResource(R.drawable.instagram_new_post_outline_24);
            ibHome.setImageResource(R.drawable.instagram_home_outline_24);
            ibProfile.setImageResource(R.drawable.instagram_user_outline_24);
            ibSettings.setImageResource(R.drawable.ufi_save_active);

            fragment = profileFragment;

            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.FragmentPlace, fragment);

            ft.commit();
        }



    }


    public void showDetailsFor(Serializable post) {
        // create intent for the new activity
        Intent intent = new Intent(this, PostDetailsActivity.class);
        // serialize the post using parceler, use its short name as a key
        intent.putExtra(Post.class.getSimpleName(), (Serializable) post);
        // show the activity
        startActivityForResult(intent,123);
    }

    public void showProfileFragment(Post post) {
        Fragment fragment;

        ibAdd.setImageResource(R.drawable.instagram_new_post_outline_24);
        ibHome.setImageResource(R.drawable.instagram_home_outline_24);
        ibProfile.setImageResource(R.drawable.instagram_user_filled_24);
        ibSettings.setImageResource(R.drawable.ufi_save);

        fragment = profileFragment;

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.FragmentPlace, fragment);

        ft.commit();
    }
}
