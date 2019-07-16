package com.codepath.big_heart;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

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
                registerUser();
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
    }


    private void registerUser() {
        // posts new user to database
        //runs new activity bringing user to main page //finishes
    }
}
