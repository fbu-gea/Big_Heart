package com.codepath.parsetagram;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class MainActivity extends AppCompatActivity {

    EditText etUsername;
    EditText etPassword;
    Button btnlogin;
    Button btnsignUp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
            // do stuff with the user
            Toast.makeText(getApplicationContext(), "Welcome back " + currentUser.getUsername() + "!", Toast.LENGTH_LONG).show();
            Intent i = new Intent(MainActivity.this, FeedActivity.class);
            startActivity(i);
            finish();

        }
            // show the signup or login screen

            setContentView(R.layout.activity_main);
            etUsername = (EditText) findViewById(R.id.et_username);
            etPassword = (EditText) findViewById(R.id.et_password);
            btnlogin = (Button) findViewById(R.id.btnLogin);
            btnsignUp = (Button) findViewById(R.id.btnSignUp);

            // Use for troubleshooting -- remove this line for production
            Parse.setLogLevel(Parse.LOG_LEVEL_DEBUG);

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.networkInterceptors().add(httpLoggingInterceptor);

            Parse.initialize(new Parse.Configuration.Builder(this)
                    .applicationId("spiralcloudah") // should correspond to APP_ID env variable
                    .clientKey(null)  // set explicitly unless clientKey is explicitly configured on Parse server
                    .clientBuilder(builder)
                    .server("http://spiralcloudah-fbu-instagram.herokuapp.com/parse").build());

            btnsignUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Create the ParseUser
                    ParseUser user = new ParseUser();
                    System.out.println("Signing up?");

                    // Set core properties
                    user.setUsername(etUsername.getText().toString());
                    user.setPassword(etPassword.getText().toString());
                    // Set custom properties
                    // Invoke signUpInBackground
                    user.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(com.parse.ParseException e) {
                            if (e == null) {
                                // Hooray! Let them use the app now.
                                Toast.makeText(getApplicationContext(), "You're registered! Please click 'LOGIN'", Toast.LENGTH_LONG).show();

                            } else {
                                // Sign up didn't succeed. Look at the ParseException
                                // to figure out what went wrong
                                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();

                            }
                        }
                    });
                }
            });


            btnlogin.setOnClickListener(new View.OnClickListener() {


                @Override
                public void onClick(View v) {

                    System.out.println("Logging in?");

                    ParseUser.logInInBackground(etUsername.getText().toString(), etPassword.getText().toString(), new LogInCallback() {
                        @Override
                        public void done(ParseUser parseUser, ParseException e) {
                            if (parseUser != null) {
                                //Login Successful
                                //you can display sth or do sth
                                //For example Welcome + ParseUser.getUsername()

                                Toast.makeText(getApplicationContext(), "Successfully logged in!", Toast.LENGTH_LONG).show();

                                Intent i = new Intent(MainActivity.this, FeedActivity.class);
                                startActivity(i);
                                finish();

                            } else {
                                //Login Fail
                                //get error by calling e.getMessage()

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        if (!isFinishing()) {
                                            new AlertDialog.Builder(MainActivity.this)
                                                    .setTitle("Login failed")
                                                    .setMessage("Please check your username/password")
                                                    .setCancelable(false)
                                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {

                                                        }
                                                    }).show();
                                        }
                                    }
                                });

                            }
                        }
                    });
                }
            });
        }

}
