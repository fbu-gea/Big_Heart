package com.codepath.parsetagram;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.parsetagram.model.Post;
import com.parse.ParseException;
import com.parse.ParseFile;

public class PostDetailsActivity extends AppCompatActivity {

    // the movie to display
    Post post;

    // the view objects
    ImageView ivImage;
    ImageButton ibProfilePic;
    TextView tvUser;
    TextView tvUser2;
    TextView tvDescription;
    ImageView imageView3;
    TextView tvDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);

        // resolve the view objects
        ivImage = (ImageView) findViewById(R.id.ivImage);
        ibProfilePic = (ImageButton) findViewById(R.id.ibProfilePic);
        tvUser = (TextView) findViewById(R.id.tvUser);
        tvUser2 = (TextView) findViewById(R.id.tvUser2);
        tvDescription = (TextView) findViewById(R.id.tvDescription);
        imageView3 = (ImageView) findViewById(R.id.imageView3);
        tvDate = (TextView) findViewById(R.id.tvDate);

        // unwrap the movie passed in via intent, using its simple name as a key
        post = (Post) getIntent().getParcelableExtra(Post.class.getSimpleName());
        Log.d("PostDetailsActivity", String.format("Showing details for '%s'", post.getDescription()));

        tvDescription.setText(post.getDescription());
        Glide.with(this).load(post.getImage().getUrl()).into(ivImage);
//        Glide.with(this).load(post.getProfileImage().getUrl()).into(ibProfilePic);
        try {
            tvUser.setText(post.getUser().fetchIfNeeded().getUsername());
            tvUser2.setText(post.getUser().fetchIfNeeded().getUsername());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        tvDate.setText(ParseRelativeDate.getRelativeTimeAgo(post.getCreatedAt()));

        ParseFile p = post.getUser().getParseFile("profilePic");
        if(p != null) {
            Glide.with(this)
                    .load(p.getUrl())
                    .into(ibProfilePic);

            ibProfilePic.setBackgroundColor(Color.WHITE);
        }


    }
}
