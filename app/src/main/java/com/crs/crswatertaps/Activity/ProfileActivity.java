package com.crs.crswatertaps.Activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.crs.crswatertaps.Model.User;
import com.crs.crswatertaps.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {

    TextView name, email, mobile, company;
    String userName, userEmail, userMobile, userData;
    User userDetails;
    DatabaseReference mReference;
    String UnicID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        setupActionBar("Profile");

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {

            UnicID = firebaseUser.getUid();
        }

        makeForm();
        dataFromFirebase();
        //setData(user);
    }

    private void dataFromFirebase() {

        mReference = FirebaseDatabase.getInstance().getReference("User");
        mReference.child(UnicID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                setData(user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void makeForm() {
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        mobile = findViewById(R.id.mobile);
        company = findViewById(R.id.pas);
    }

    private void setData(User user) {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {
            userName = firebaseUser.getDisplayName();
            userEmail = firebaseUser.getEmail();
            userMobile = firebaseUser.getPhoneNumber();
            userData = firebaseUser.getProviderId();
        }

        User data = user;

        email.setText(userEmail);

        name.setText(data.getName());
        mobile.setText(data.getNumber());
        company.setText(data.getLastName());
        Log.d("DATA", "setData: " + data);

    }

    private void setupActionBar(String title) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(title);
        }
    }
}
