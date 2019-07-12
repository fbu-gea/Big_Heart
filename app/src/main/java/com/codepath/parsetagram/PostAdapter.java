package com.codepath.parsetagram;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.parsetagram.model.Post;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;

import java.io.Serializable;
import java.util.List;


public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private List<Post> mPosts;
    Context context;

    int whichFragment;

    //pass in the post array
    public PostAdapter(List<Post> posts, int whichFragment) {
        mPosts = posts;
        this.whichFragment=whichFragment;
    }

    public TextView tvDate;

    // Clean all elements of the recycler
    public void clear() {
        mPosts.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Post> p) {
        mPosts.addAll(p);
        notifyDataSetChanged();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View postView = inflater.inflate(R.layout.item_post, parent, false);
        ViewHolder viewHolder = new ViewHolder(postView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Post post = mPosts.get(position);

        if(whichFragment==0){
        try {
            holder.tvDate.setText(ParseRelativeDate.getRelativeTimeAgo(post.getCreatedAt()));
            holder.tvUserName2.setText(post.getUser().fetchIfNeeded().getUsername());
            holder.tvUserName.setText(post.getUser().fetchIfNeeded().getUsername()); //????
        } catch (ParseException e) {
            e.printStackTrace();
        }



        ParseFile p = post.getUser().getParseFile("profilePic");
        if(p != null) {
            Glide.with(context)
                    .load(p.getUrl())
                    .into(holder.ibProfilePic);

            holder.ibProfilePic.setBackgroundColor(Color.WHITE);
        }

        holder.ibProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((FeedActivity) context).showProfileFragment(post);
            }
        });

        ParseUser myUser = null;
        myUser = post.getUser();
        System.out.println("User is " + myUser);
        holder.tvDesc.setText(post.getDescription());

        Glide.with(context)
                .load(post.getImage().getUrl())
                .into(holder.ivImage);}
        else if(whichFragment==1){
            holder.tvDate.setVisibility(View.GONE);
            holder.tvDesc.setVisibility(View.GONE);
            holder.tvUserName.setVisibility(View.GONE);
            holder.tvUserName2.setVisibility(View.GONE);
            Glide.with(context)
                    .load(post.getImage().getUrl())
                    .into(holder.ivImage);

            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            int pxWidth = displayMetrics.widthPixels;

            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(pxWidth/3, pxWidth/3);
            holder.ivImage.setLayoutParams(layoutParams);
            Glide.with(context).load(post.getImage().getUrl()).into(holder.ivImage);

        }
       // Glide.with(context)
        //        .load(post.getProfileImage().getUrl())
        //        .into(holder.ibProfilePic);

    }

    @Override
    public int getItemCount() {
        return mPosts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageButton ibProfilePic;
        public ImageView ivImage;
        public TextView tvUserName;
        public TextView tvUserName2;
        public TextView tvDesc;
        public TextView tvDate;


        public ViewHolder(View itemView) {
            super(itemView);

            ivImage = (ImageView) itemView.findViewById(R.id.ivImage);
            tvUserName = (TextView) itemView.findViewById(R.id.tvUser);
            tvUserName2 = (TextView) itemView.findViewById(R.id.tvUser2);
            tvDesc = (TextView) itemView.findViewById(R.id.tvDescription);
            tvDate = (TextView) itemView.findViewById(R.id.tvDate);
            ibProfilePic = (ImageButton) itemView.findViewById(R.id.ibProfilePic);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            // gets item position
            int position = getAdapterPosition();
            // make sure the position is valid, i.e. actually exists in the view
            if (position != RecyclerView.NO_POSITION) {
                // get the post at the position, this won't work if the class is static
                Post post = mPosts.get(position);
                // tell Feed Fragment to start the Details activity
                ((FeedActivity) context).showDetailsFor((Serializable) post);
            }
        }
    }
}
