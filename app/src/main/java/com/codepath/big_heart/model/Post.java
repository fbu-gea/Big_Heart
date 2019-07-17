package com.codepath.big_heart.model;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.io.Serializable;

@ParseClassName("Post")
public class Post extends ParseObject implements Serializable {
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_IMAGE = "image";
    public static final String KEY_USER = "user";
    private static final String KEY_PROFILE = "profile";
    public static final String KEY_DATE = "createdAt";


    public String getDescription() {
        return getString(KEY_DESCRIPTION);
    }

    public void setDescription(String description) {
        put(KEY_DESCRIPTION, description);
    }

    public ParseFile getImage() {
        return getParseFile(KEY_IMAGE);
    }

    public void setImage(ParseFile image){
        put(KEY_IMAGE, image);
    }

    public ParseFile getProfileImage() { return getParseFile(KEY_PROFILE); }

    public void setProfileImage(ParseFile pImage) { put(KEY_PROFILE, pImage); }

    public ParseUser getUser() {
        return getParseUser(KEY_USER);
    }

    public void setUser(ParseUser user){
        put(KEY_USER, user);
    }

    public static class Query extends ParseQuery<Post>{

        public Query() {
            super(Post.class);
        }

        public Query getTop(){
            setLimit(20);
            return this;
        }

        public Query withUser(){
            include("user");
            return this;
        }
    }


}

