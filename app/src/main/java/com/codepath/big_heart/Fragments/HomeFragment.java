package com.codepath.big_heart.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.codepath.big_heart.EndlessRecyclerViewScrollListener;
import com.codepath.big_heart.PostAdapter;
import com.codepath.big_heart.R;
import com.codepath.big_heart.model.Post;
import com.parse.FindCallback;
import com.parse.ParseException;

import java.util.ArrayList;
import java.util.List;

import static com.parse.Parse.getApplicationContext;

public class HomeFragment extends Fragment {
    // Store a member variable for the listener
    private EndlessRecyclerViewScrollListener scrollListener;

    ArrayList<Post> posts;
    public RecyclerView rvPost;
    PostAdapter adapter;
    private SwipeRefreshLayout swipeContainer;
    int whichFragment=0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        posts = new ArrayList<>();

        adapter = new PostAdapter(posts);
        rvPost = (RecyclerView) rootView.findViewById(R.id.rvPost);

        rvPost.setLayoutManager(new LinearLayoutManager(getContext()));
        rvPost.setAdapter(adapter);

        // Configure the RecyclerView
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvPost.setLayoutManager(linearLayoutManager);

        // Retain an instance so that you can call `resetState()` for fresh searches
        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                final int curSize = adapter.getItemCount();
//                adapter.addAll(posts);

                view.post(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyItemRangeInserted(curSize, adapter.getItemCount() - 1);
                    }
                });
            }
        };
        // Adds the scroll listener to RecyclerView
        rvPost.addOnScrollListener(scrollListener);



//        swipeContainer = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
//        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                // Your code here
//                Toast.makeText(getApplicationContext(), "Refreshed!", Toast.LENGTH_LONG).show();
//                // To keep animation for 4 seconds
//                posts.clear();
//                adapter.clear();
//                loadTopPosts();
//
//            }
//        });

        // Scheme colors for animation
//        swipeContainer.setColorSchemeColors(
//                getResources().getColor(android.R.color.holo_blue_bright),
//                getResources().getColor(android.R.color.holo_green_light),
//                getResources().getColor(android.R.color.holo_orange_light),
//                getResources().getColor(android.R.color.holo_red_light)
//        );



        loadTopPosts();


        getConfiguration();

        return rootView;

    }

    // Append the next page of data into the adapter
    // This method probably sends out a network request and appends new data items to your adapter.
    public void loadNextDataFromApi(int offset) {
        // Send an API request to retrieve appropriate paginated data
        //  --> Send the request including an offset value (i.e `page`) as a query parameter.
        //  --> Deserialize and construct new model objects from the API response
        //  --> Append the new data objects to the existing set of items inside the array of items
        //  --> Notify the adapter of the new items made with `notifyItemRangeInserted()`
    }


    public void getConfiguration() {

    }

    public void loadTopPosts(){
        final Post.Query postQuery = new Post.Query();
        postQuery.getTop().withUser();
        postQuery.addDescendingOrder(Post.KEY_DATE);

        postQuery.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> objects, ParseException e) {
                if(e == null) {
                    adapter.clear();
                    for(int i = 0; i < objects.size(); i++) {
                        posts.add(objects.get(i));
                        adapter.notifyItemInserted(posts.size() - 1);
//                        Log.i("HomeFragment", "Post " + i + " " + objects.get(i).getDescription());
                    }
                } else {
                    Toast.makeText(getContext(), "Failed to query posts", Toast.LENGTH_SHORT).show();
                }
//                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
}