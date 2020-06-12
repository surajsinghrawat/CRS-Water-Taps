package com.crs.crswatertaps.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.crs.crswatertaps.CommonAction.CustomDialogClass;
import com.crs.crswatertaps.CommonAction.PermissionCheck;
import com.crs.crswatertaps.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private TextView email;
    private TextView Password;
    private Button login;
    private Button register;
    private FirebaseAuth mAuth;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_login);
        setupActionBar("CRS WATER TAPS");
        if (!PermissionCheck.checkPermission(this)){
            PermissionCheck.requestPermission(this);
        }
        mAuth = FirebaseAuth.getInstance();
        email = (EditText) findViewById(R.id.etemail);
        Password = (EditText) findViewById(R.id.etpass);

        login = (Button) findViewById(R.id.btnLogin);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                   if (validate()){
                       final ProgressDialog dialog = ProgressDialog.show(MainActivity.this, "", "Logging in");
                       String emil = email.getText().toString().trim();
                       String pass = Password.getText().toString().trim();

                       mAuth.signInWithEmailAndPassword(emil, pass)
                               .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                   @Override
                                   public void onComplete(@NonNull Task<AuthResult> task) {
                                       if (task.isSuccessful()) {
                                           dialog.dismiss();

                                           Toast.makeText(MainActivity.this, "Successful Login", Toast.LENGTH_LONG).show();
                                           final FirebaseUser firebaseUser = mAuth.getCurrentUser();
                                           assert firebaseUser != null;
                                           userID = firebaseUser.getUid();
                                           Log.d("USERID", userID);
                                           Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                                           intent.putExtra("userID", firebaseUser.getUid());
                                           startActivity(intent);
                                           finish();
                                       } else {
                                           dialog.dismiss();
                                           CustomDialogClass.showWarning(MainActivity.this, "Authentication failed." + "Enter Valid Email ID And Password", "Ok", new CustomDialogClass.WarningResponse() {
                                               @Override
                                               public void onPositive() {
                                                   email.setText("");
                                                   Password.setText("");
                                                   email.requestFocus();
                                               }

                                               @Override
                                               public void onNegative() {

                                               }
                                           });
                                           Toast.makeText(MainActivity.this, "Authentication failed.",
                                                   Toast.LENGTH_SHORT).show();

                                       }
                                   }
                               });
                   }
               }


        });

        startRegistration();
    }

    private void startRegistration(){
        register = (Button) findViewById(R.id.btnSinUp);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private Boolean validate() {
        boolean result = false;
        String emil = email.getText().toString();
        String pass = Password.getText().toString();
        if (TextUtils.isEmpty(emil)){
            email.setError("Required.");
            Toast.makeText(this, "Please Enter Email ID", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(pass)){
            Password.setError("Required.");
            Toast.makeText(this, "Please Enter Password", Toast.LENGTH_SHORT).show();
        }else {
            result=true;
        }
        return result;
    }


    @Override
    protected void onStart() {
        super.onStart();


        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            toDashboard();
        } else {
            Log.d("no user", "no user");
        }
    }

    private void toDashboard() {
        Intent intent = new Intent(MainActivity.this, Main2Activity.class);
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
