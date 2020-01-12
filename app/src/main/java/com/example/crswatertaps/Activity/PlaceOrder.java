package com.example.crswatertaps.Activity;

import android.os.Bundle;
import android.renderscript.Sampler;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.crswatertaps.CommonAction.RequestClass;
import com.example.crswatertaps.Model.CartModel;
import com.example.crswatertaps.Model.Respons;
import com.example.crswatertaps.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class PlaceOrder extends AppCompatActivity {
    String id;
    EditText customerName,compnayName,mobileNo,address,gstNo,pinCode;

    String name,compnay,phone,fullAddress,gst,pin,userID;
    Button btnOrder;
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    CartModel data;
    private Query query;
    DatabaseReference cartRef;
    List<CartModel> cartModelList;
    HashMap<String,String> map1;
    ArrayList<String> setItem;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.place_order_activity);
        FirebaseUser firebaseUser=mAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {
            // User is signed in
            userID=firebaseUser.getEmail();
            id=firebaseUser.getUid();
            Log.d("USERID",userID);
        }

        cartRef=database.getInstance().getReference("cart").child(id);

        cartModelList=new ArrayList<CartModel>();

        Log.d("sdf",""+cartModelList);
         map1=new HashMap<>();

        makeForm();

        setItem=new ArrayList<>();
        cartRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                cartModelList.clear();
                for (DataSnapshot item : dataSnapshot.getChildren()){
                    CartModel model=item.getValue(CartModel.class);
                    cartModelList.add(model);
                }
                Log.d("TAG",""+cartModelList);
                for (CartModel listItem : cartModelList){
                    Log.d("sdf",listItem.getName());
                    Log.d("sdf",""+listItem.getPrice());
                    Log.d("sdf",""+listItem.getQuantity());
                    Log.d("sdf",listItem.getModelId()+listItem.getSeriesId());


                    map1.put("ItemName",listItem.getName());
                    map1.put("Details",listItem.getModelId()+listItem.getSeriesId());
                    map1.put("Price", String.valueOf(listItem.getPrice()));
                    map1.put("Quantity",String.valueOf(listItem.getQuantity()));
                    Log.d("ABC", String.valueOf(map1));

                    setItem.add(listItem.getName());
                    setItem.add(listItem.getModelId()+listItem.getSeriesId());
                    setItem.add(String.valueOf(listItem.getPrice()));
                    setItem.add(String.valueOf(listItem.getQuantity()));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        final HashMap<String,String> map=new HashMap<>();
        map.put("name",name);
        map.put("shop_name",compnay);
        map.put("mobile",phone);
        map.put("gst_no",gst);
        map.put("address",fullAddress);
        map.put("pincode",pin);
        map.put("customer_email",userID);
        map.put("Item", String.valueOf(map1));

        Log.d("DATA",""+map);

        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(PlaceOrder.this, "Click", Toast.LENGTH_SHORT).show();
                RequestClass request=new RequestClass(RequestClass.Method.POST, new JSONObject(map), new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getBoolean("Result_Ok")){

                                Toast.makeText(PlaceOrder.this, "Order Place Successful", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(PlaceOrder.this, "Error", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                },PlaceOrder.this);
                RequestQueue queue = Volley.newRequestQueue(PlaceOrder.this);
                queue.add(request);
            }
        });





        getData();
    }

    private void makeForm(){
        customerName=findViewById(R.id.etUserName);
        compnayName=findViewById(R.id.companyName);
        mobileNo=findViewById(R.id.mobileNo);
        address=findViewById(R.id.address);
        gstNo=findViewById(R.id.gstNo);
        pinCode=findViewById(R.id.pinCode);
        btnOrder=findViewById(R.id.btnOrder);

        name=customerName.getText().toString();
        compnay=compnayName.getText().toString();
        phone=mobileNo.getText().toString();
        fullAddress=address.getText().toString();
        gst=gstNo.getText().toString();
        pin=pinCode.getText().toString();

    }

    private void getData(){

    }

}
