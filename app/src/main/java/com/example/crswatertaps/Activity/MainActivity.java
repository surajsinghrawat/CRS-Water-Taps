package com.example.crswatertaps.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.crswatertaps.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    public boolean hasSession = false;
    private TextView email;
    private TextView Password;
    private Button login;
    private Button register;
    private FirebaseAuth mAuth;
String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupActionBar("CRS WATER TAPS");
        mAuth = FirebaseAuth.getInstance();
        email = (EditText)findViewById(R.id.etemail);
        Password = (EditText)findViewById(R.id.etpass);

        FirebaseAuth.AuthStateListener authStateListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
                userID =firebaseUser.getUid();
                Log.d("USERID",userID);
            }
        };

//        final FirebaseUser firebaseUser=mAuth.getCurrentUser();
//        if (firebaseUser != null) {
//            userID = firebaseUser.getUid();
//            Log.d("USERID", userID);
//        }else Toast.makeText(this, "firebaseUser Is Null", Toast.LENGTH_SHORT).show();
        login = (Button)findViewById(R.id.btnLogin);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog dialog = ProgressDialog.show(MainActivity.this, "", "Logging in");
                String emil=email.getText().toString().trim();
                String pass= Password.getText().toString().trim();

                mAuth.signInWithEmailAndPassword(emil, pass)
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    dialog.dismiss();
                                    // Sign in success, update UI with the signed-in user's information

                                    Toast.makeText(MainActivity.this,"Sucessfulll main",Toast.LENGTH_LONG).show();
                                    final FirebaseUser firebaseUser=mAuth.getCurrentUser();
                                    assert firebaseUser != null;
                                    userID = firebaseUser.getUid();
                                    Log.d("USERID", userID);
                                    Intent intent= new Intent(MainActivity.this, Main2Activity.class);
                                    intent.putExtra("userID",firebaseUser.getUid());
                                    startActivity(intent);
                                    finish();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    //Toast.makeText(MainActivity.this,"failed",Toast.LENGTH_LONG).show();
                                    String emil=email.getText().toString().trim();
                                    String pass= Password.getText().toString().trim();
//                                    mAuth.createUserWithEmailAndPassword(emil, pass)
//
//                                            .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
//                                                @Override
//                                                public void onComplete(@NonNull Task<AuthResult> task) {
//                                                    if (task.isSuccessful()) {
//                                                        // Sign in success, update UI with the signed-in user's information
//                                                        Toast.makeText(MainActivity.this,"Sucessfulll second ",Toast.LENGTH_LONG).show();
//                                                        Intent intent= new Intent(MainActivity.this,Main2Activity.class);
//                                                        startActivity(intent);
//                                                        finish();
//                                                    } else {
                                                        // If sign in fails, display a message to the user.

                                                        Toast.makeText(MainActivity.this, "Authentication failed.",
                                                                Toast.LENGTH_SHORT).show();

                                                    }

                                                    // ...
                                                }
                                            });
                                }

                                // ...
                          //  }
                        //});





          //  }
        });


        register=(Button)findViewById(R.id.btnSinUp);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser=mAuth.getCurrentUser();
        if (currentUser!=null){
            toDashboard();
        }else {
           Log.d("no user","no user");
        }
    }

    private void toDashboard(){
        Intent intent=new Intent(MainActivity.this, Main2Activity.class);
        startActivity(intent);
        finish();
    }

    private void setupActionBar(String title) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(title);
        }
    }





}
