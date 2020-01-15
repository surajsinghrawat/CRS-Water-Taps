package com.example.crswatertaps.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.crswatertaps.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileActivity extends AppCompatActivity {

    TextView name, email, mobile, company;
    String userName, userEmail, userMobile, userData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        setupActionBar("Profile");

        makeForm();
        setData();
    }

    private void makeForm() {
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        mobile = findViewById(R.id.mobile);
        company = findViewById(R.id.pas);
    }

    private void setData() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {
            userName = firebaseUser.getDisplayName();
            userEmail = firebaseUser.getEmail();
            userMobile = firebaseUser.getPhoneNumber();
            userData = firebaseUser.getProviderId();
        }

        name.setText(userName);
        email.setText(userEmail);
        mobile.setText(userMobile);
        company.setText(userData);
    }

    private void setupActionBar(String title) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(title);
        }
    }
}
