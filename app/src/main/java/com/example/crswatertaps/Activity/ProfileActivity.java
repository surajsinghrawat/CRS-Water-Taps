package com.example.crswatertaps.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.crswatertaps.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileActivity extends AppCompatActivity {
    TextView name,email,number;
    FirebaseDatabase database;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        makeForm();

    }
    private void makeForm(){
        name=findViewById(R.id.name);
        email=findViewById(R.id.email);
        number=findViewById(R.id.number);
        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        name.setText(firebaseUser.getDisplayName().toString());
        email.setText(firebaseUser.getEmail().toString());
        number.setText(firebaseUser.getPhoneNumber().toString());

    }
}
