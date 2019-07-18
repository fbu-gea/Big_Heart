package com.codepath.big_heart;

import android.app.Application;

import com.codepath.big_heart.model.Post;
import com.parse.Parse;
import com.parse.ParseObject;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class ParseApplication extends Application {
        @Override
        public void onCreate() {
            super.onCreate();

            ParseObject.registerSubclass(Post.class);

            final Parse.Configuration configuration = new Parse.Configuration.Builder(this)
                    .applicationId("biggerheart")
                    .clientKey("tlandc")
                    .server("http://big-heart.herokuapp.com/parse")
                    .build();
            Parse.initialize(configuration);
        }
}
