package com.example.crswatertaps.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.crswatertaps.CommonAction.CustomDialogClass;
import com.example.crswatertaps.Model.User;
import com.example.crswatertaps.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private EditText UserName,PassWord,Email,lastName,phoneNo;
    private Button regButton;
    private TextView userLogin;
    private FirebaseAuth firebaseAuth;
    private String UnicID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newregister);

        firebaseAuth=FirebaseAuth.getInstance();
        final DatabaseReference mDataBase=FirebaseDatabase.getInstance().getReference();

        setupActionBar("CRS Water Taps");

        setupUIViews();

        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()){
                    String user_email= Email.getText().toString().trim();
                    String user_password=PassWord.getText().toString().trim();

                    firebaseAuth.createUserWithEmailAndPassword(user_email,user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            if (task.isSuccessful())
                            {
                                FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                                if (firebaseUser != null) {
                                    UnicID = firebaseUser.getUid();
                                }
                                Toast.makeText(RegisterActivity.this,"SinUp Successful",Toast.LENGTH_SHORT).show();
                                Intent newIntent=new Intent(RegisterActivity.this,MainActivity.class);
                                startActivity(newIntent);
                                User user=new User();
                                user.setName(UserName.getText().toString());
                                user.setLastName(lastName.getText().toString());
                                user.setNumber(phoneNo.getText().toString());
                                mDataBase.child("User").child(UnicID).setValue(user);
//                                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                            }else
                            {
                                CustomDialogClass.showWarning(RegisterActivity.this, "Registration failed."+"Enter Valid Details", "CANCEL", new CustomDialogClass.WarningResponse() {
                                    @Override
                                    public void onPositive() {

                                        UserName.setText("");
                                        PassWord.setText("");
                                        Email.setText("");
                                        UserName.requestFocus();
                                    }

                                    @Override
                                    public void onNegative() {

                                    }
                                });
                                Toast.makeText(RegisterActivity.this,"SinUp UnSuccessful",Toast.LENGTH_SHORT).show();
                            }


                        }
                    });
                }
            }
        });


        userLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private Boolean validate(){
        boolean result=false;

        String name = UserName.getText().toString();
        String password = PassWord.getText().toString();
        String email = Email.getText().toString();
        String secondName=lastName.getText().toString();
        String number=phoneNo.getText().toString();

        if (TextUtils.isEmpty(name) && TextUtils.isEmpty(password) && TextUtils.isEmpty(email) && TextUtils.isEmpty(secondName) && TextUtils.isEmpty(number)){
            UserName.setError("Required.");
            PassWord.setError("Required."+"@gmail.com");
            Email.setError("Required.");
            lastName.setError("Required");
            phoneNo.setError("Required");
            Toast.makeText(this,"Please Enter All The Details",Toast.LENGTH_SHORT).show();
        }else {
            result = true;
        }
        return  result;
    }

    private void setupUIViews(){
        UserName = (EditText)findViewById(R.id.etUserName);
        PassWord = (EditText)findViewById(R.id.etPassWord);
        Email = (EditText)findViewById(R.id.etEmail);
        lastName=findViewById(R.id.etLasName);
        phoneNo=findViewById(R.id.etNumber);
        regButton = (Button) findViewById(R.id.btnRegister);
        userLogin = (Button) findViewById(R.id.tvBack);
    }

    private void setupActionBar(String title) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(title);
        }
    }



}
