package com.codepath.big_heart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.parse.Parse;
import com.parse.ParseFile;
import com.parse.ParseUser;

public class RegisterActivity extends AppCompatActivity {

    // instantiate layout variables
    EditText etFirstName;
    EditText etLastName;
    EditText etEmail;
    EditText etUsername;
    EditText etPassword;
    Button btnRegister;
    Button btnLogin;
    Button btnAddProfilePic;
    ImageView ivProfilePic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //find pointers to ids for edit text views in layout
        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etEmail = findViewById(R.id.etEmail);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        ivProfilePic = findViewById(R.id.ivProfilePic);

        //find pointers to ids for button views in layout
        btnRegister = findViewById(R.id.btnRegister);
        btnLogin = findViewById(R.id.btnLogin);
        btnAddProfilePic = findViewById(R.id.btnAddProfilePic);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String firstName = etFirstName.getText().toString();
                final String lastName = etLastName.getText().toString();
                final String email = etEmail.getText().toString();
                final String username = etUsername.getText().toString();
                final String password = etPassword.getText().toString();
                //final ParseFile profilePic = ivProfilePi
                registerUser(firstName,lastName,email,username,password);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToLogin();
            }
        });

        btnAddProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //fires camera intent to capture photo
                // captured photo goes into image view
            }
        });
    }

    // fires an intent to bring user back to Login activity
    private void backToLogin() {
        Intent backtoLogin = new Intent(RegisterActivity.this, MainActivity.class);
        startActivity(backtoLogin);
        finish();
    }


    private void registerUser(String firstName, String lastName, String email, String username, String password) {
        //create new parse user
        ParseUser user = new ParseUser();

        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);


        // posts new user to database
        //runs new activity bringing user to main page //finishes
    }
}
