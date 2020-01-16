package com.example.crswatertaps.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.crswatertaps.CommonAction.CustomDialogClass;
import com.example.crswatertaps.CommonAction.RequestClass;
import com.example.crswatertaps.Model.CartModel;
import com.example.crswatertaps.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class PlaceOrder extends AppCompatActivity {

    EditText customerName, companyName, gstNo, address, pinCode, mobileNo;
    String name, company, gstNumber, fulladdress, email, UnicID, itemName, itemDetails, itemPrice, itemQuantity;
    String pin, phoneNo;
    Button submit;
    CartModel data;
    public static final String TAG = "DATA";
    DatabaseReference mReference;
    HashMap<String, String> map1 = new HashMap<>();
    HashMap<String, String> map = new HashMap<>();
    ArrayList<String> item = new ArrayList<>();
    StringBuilder errorBuilder;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.place_order_activity);
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {
            email = firebaseUser.getEmail();
            UnicID = firebaseUser.getUid();
        }


        customerName = findViewById(R.id.etUserName);
        companyName = findViewById(R.id.companyName);
        gstNo = findViewById(R.id.gstNo);
        address = findViewById(R.id.address);
        pinCode = findViewById(R.id.pinCode);
        mobileNo = findViewById(R.id.mobileNo);
        submit = findViewById(R.id.btnOrder);


        dataFromFirebase();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateForm()) {
                    submitdata();
                } else {
                    CustomDialogClass.showWarning(PlaceOrder.this, errorBuilder.toString(), "Ok", null);
                }
            }
        });

    }


    private void dataFromFirebase() {

        mReference = FirebaseDatabase.getInstance().getReference("cart").child(UnicID);

        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    data = item.getValue(CartModel.class);
                    CartModel respons = data;
                    getData(respons);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void getData(CartModel respons) {
        CartModel list = respons;
//        itemName = list.getName();
//        itemDetails = "" + list.getModelId() + list.getSeriesId();
//        itemPrice = String.valueOf(list.getPrice());
//        itemQuantity = String.valueOf(list.getQuantity());

        map.put("item_name", list.getName());
        map.put("details", list.getModelId() + list.getSeriesId());
        map.put("price", String.valueOf(list.getPrice()));
        map.put("quentity", String.valueOf(list.getQuantity()));
        item.add(String.valueOf(map));



    }


    private boolean validateForm() {
        boolean valid = true;

        errorBuilder = new StringBuilder();

        name = customerName.getText().toString();
        if (name.isEmpty()) {
            errorBuilder.append("Name Is Empty" + "\n");
            valid = false;
        }

        company = companyName.getText().toString();
        if (company.isEmpty()) {
            errorBuilder.append("Company Name Is Empty" + "\n");
            valid = false;
        }

        gstNumber = gstNo.getText().toString();
        if (gstNumber.isEmpty()) {
            errorBuilder.append("GST Number Is Empty" + "\n");
            valid = false;
        }

        fulladdress = address.getText().toString();
        if (fulladdress.isEmpty()) {
            errorBuilder.append("Address Is Empty" + "\n");
            valid = false;
        }

        pin = pinCode.getText().toString();
        if (pin.isEmpty()) {
            errorBuilder.append("Pin Code Is Empty" + "\n");
            valid = false;
        }
        phoneNo = mobileNo.getText().toString();
        if (phoneNo.isEmpty()) {
            errorBuilder.append("Mobile Number Is Empty" + "\n");
            valid = false;
        }


        return valid;
    }


    private void submitdata() {


        map1.put("name", name);
        map1.put("shop_name", company);
        map1.put("mobile", phoneNo);
        map1.put("gst_no", gstNumber);
        map1.put("address", fulladdress);
        map1.put("pincode", pin);
        map1.put("customer_email", email);
        map1.put("items", String.valueOf(item));

        RequestClass request=new RequestClass(Request.Method.POST, new JSONObject(map), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                CustomDialogClass.showDialog(PlaceOrder.this, "Order Place", "Ok", "", new CustomDialogClass.WarningResponse() {
                    @Override
                    public void onPositive() {

                        customerName.setText("");
                        companyName.setText("");
                        gstNo.setText("");
                        address.setText("");
                        pinCode.setText("");
                        mobileNo.setText("");
                    }

                    @Override
                    public void onNegative() {

                    }
                });

                Log.d(TAG,response.toString());
                Intent intent = new Intent(PlaceOrder.this, Main2Activity.class);
                startActivity(intent);
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PlaceOrder.this, "Error"+error, Toast.LENGTH_SHORT).show();
            }
        },PlaceOrder.this);

        RequestQueue queue= Volley.newRequestQueue(PlaceOrder.this);
        queue.add(request);


        Log.d(TAG, String.valueOf(map1));

    }

}
