package com.codepath.parsetagram;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.codepath.parsetagram.model.Post;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.parse.Parse.getApplicationContext;


public class ProfileFragment extends Fragment {

    // Store a member variable for the listener
    private EndlessRecyclerViewScrollListener scrollListener;
    private SwipeRefreshLayout swipeContainer;
    Button logoutBtn;
    ImageButton ibProfilePic;
    int whichFragment=1;

    public final static int PICK_PHOTO_CODE = 1046;
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1;
    public final String APP_TAG = "MyCustomApp";
    File photoFile;

    String currentPath;
    ParseFile parseFile;

    ArrayList<Post> posts;
    public RecyclerView rvPostView;
    PostAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        logoutBtn = rootView.findViewById(R.id.btnLogout);
        ibProfilePic = rootView.findViewById(R.id.ibProfilePic);
        String currentUser = ParseUser.getCurrentUser().getUsername(); // this will now be null
        System.out.println("The current user is "+ currentUser);


        ParseFile p = ParseUser.getCurrentUser().getParseFile("profilePic");
        if(p != null) {
            Glide.with(getContext())
                    .load(p.getUrl())
                    .into(ibProfilePic);

            ibProfilePic.setBackgroundColor(Color.WHITE);
        }

        rvPostView= rootView.findViewById(R.id.rvPostView);


        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutUser(v);
            }
        });
        ibProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity activity = (Activity) ProfileFragment.this.getActivity();

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                File mediaStorage = null;
                try {
                    mediaStorage = getTempImageFile(getContext());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                // Create the storage directory if it does not exist
                if (!mediaStorage.exists() && !mediaStorage.mkdirs()){
                    Log.d(APP_TAG, "failed to create directory");
                }

                String path = mediaStorage.getAbsolutePath();
                Uri uri = FileProvider.getUriForFile(activity, "com.codepath.parsetagram", mediaStorage);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);

                photoFile = new File(path);
                //
                startActivityForResult(intent,
                        CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
            }
        });

        loadTopPosts();
        posts = new ArrayList<>();

        adapter = new PostAdapter(posts, whichFragment);
        rvPostView = (RecyclerView) rootView.findViewById(R.id.rvPostView);

        rvPostView.setAdapter(adapter);

        // Configure the RecyclerView

        GridLayoutManager gridLayoutManager= new GridLayoutManager(getContext(), 3);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvPostView.setLayoutManager(gridLayoutManager);

        // Retain an instance so that you can call `resetState()` for fresh searches
        scrollListener = new EndlessRecyclerViewScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                final int curSize = adapter.getItemCount();
                adapter.addAll(posts);

                view.post(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyItemRangeInserted(curSize, adapter.getItemCount() - 1);
                    }
                });
            }
        };
        // Adds the scroll listener to RecyclerView
        rvPostView.addOnScrollListener(scrollListener);



        swipeContainer = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code here
                Toast.makeText(getApplicationContext(), "Refreshed!", Toast.LENGTH_LONG).show();
                // To keep animation for 4 seconds
                posts.clear();
                adapter.clear();
                loadTopPosts();

            }
        });

        // Scheme colors for animation
        swipeContainer.setColorSchemeColors(
                getResources().getColor(android.R.color.holo_blue_bright),
                getResources().getColor(android.R.color.holo_green_light),
                getResources().getColor(android.R.color.holo_orange_light),
                getResources().getColor(android.R.color.holo_red_light)
        );



        loadTopPosts();


        getConfiguration();

        return rootView;
    }

    public void getConfiguration() {
    }

    public void logoutUser(View view) {

        ParseUser.logOut();
        ParseUser currentUser = ParseUser.getCurrentUser(); // this will now be null
        System.out.println("The current user is "+ currentUser);
        Intent i = new Intent(getContext(), MainActivity.class);
        startActivity(i);
    }

    private static File getTempImageFile(Context context) throws IOException {
        String currTime = getCurrentTimestamp();
        String fileNamePrefix = "JPEG_" + currTime + "_";
        String fileNameSuffix = ".jpg";
        File directory = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(fileNamePrefix, fileNameSuffix, directory);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {

                currentPath = photoFile.getPath();
                Bitmap bitmap = BitmapFactory.decodeFile(currentPath);
                ibProfilePic.setImageBitmap(bitmap);
                final BitmapDrawable ob = new BitmapDrawable(getResources(), bitmap);
                ibProfilePic.setBackgroundDrawable(ob);

                parseFile = new ParseFile(photoFile);

                ParseUser.getCurrentUser().put("profilePic", parseFile);
                ParseUser.getCurrentUser().saveInBackground();

//                parseFile.saveInBackground(new SaveCallback() {
//                    @Override
//                    public void done(ParseException e) {
//                        if (e == null){
//                            String s = parseFile.getUrl();
//                            Drawable draw = LoadImageFromWebOperations(s);
//                            ibProfilePic.setBackgroundDrawable(ob);
//                        //    ibProfilePic.saveInBackground();
//
//                        } else {
//                            e.printStackTrace();
//                            Toast.makeText(getContext(),"Could not save Profile Pic",Toast.LENGTH_LONG).show();
//                            String s = "Failed: " + e.getMessage() + " ... ";
//                            Log.d("ProfileFragment",s);
//
//                        }
//                    }
//                });

            }

        }
    }

    private Drawable LoadImageFromWebOperations(String save) {
        try {
            InputStream is = (InputStream) new URL(save).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        } catch (Exception e) {
            return null;
        }
    }

    public static String getCurrentTimestamp() {
        return new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
    }

    public void loadTopPosts(){
        final Post.Query postsQuery = new Post.Query();
        postsQuery
                .getTop()
                .withUser();

        postsQuery.whereEqualTo(Post.KEY_USER, ParseUser.getCurrentUser());

        postsQuery.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> objects, ParseException e) {
                if (e==null){
                    Post post = new Post();
                    System.out.println("Success!");
                    for (int i = 0;i<objects.size(); i++){
                        try {
                            Log.d("FeedActivity", "Post ["+i+"] = "
                                    + objects.get(i).getDescription()
                                    + "\n username = " + objects.get(i).getUser().fetchIfNeeded().getUsername()
                                    + " o k ");
                        } catch (ParseException e1) {
                            e1.printStackTrace();
                        }

                        posts.add(0,objects.get(i));
                        adapter.notifyItemInserted(posts.size()-1);

                    }
                } else {
                    e.printStackTrace();
                }
                swipeContainer.setRefreshing(false);
            }
        });
    }


}