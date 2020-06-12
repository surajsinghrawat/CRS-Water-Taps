package com.crs.crswatertaps.Activity;

import android.app.ProgressDialog;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.crs.crswatertaps.CommonAction.CustomDialogClass;
import com.crs.crswatertaps.CommonAction.RequestClass;
import com.crs.crswatertaps.Model.CartModel;
import com.crs.crswatertaps.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PlaceOrder extends AppCompatActivity {

    EditText customerName, companyName, gstNo, address, pinCode, mobileNo;
    String name, company, gstNumber, fulladdress, email, UnicID;
    String pin, phoneNo;
    Button submit;
    List<CartModel>list=new ArrayList<>();
    public static final String TAG = "DATA";
    DatabaseReference mReference;
    HashMap<String, Object> map1 = new HashMap<>();
    HashMap<String, String> map = new HashMap<>();

    StringBuilder errorBuilder;



    JSONArray array = new JSONArray();
    CartModel respond;
    List<CartModel> cartModelList;



    public static final String server="https://crsmailfunction.herokuapp.com/api/mail/";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.place_order_activity);
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {
            email = firebaseUser.getEmail();
            UnicID = firebaseUser.getUid();
        }

        cartModelList=new ArrayList<CartModel>();
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
                    try {
                        submitdata();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
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
                    CartModel data = item.getValue(CartModel.class);
                    cartModelList.add(data);
                    respond = data;
                    assert respond != null;

                }
                    getData(cartModelList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void getData(List<CartModel> cartModelList) {

        List<CartModel>data=cartModelList;
        int i=0;
        while (i<cartModelList.size()){
            JSONObject obj = new JSONObject();
            try {
                    obj.put("item_name", data.get(i).getName());
                obj.put("details", data.get(i).getModelId() + data.get(i).getSeriesId());
                obj.put("quentity", data.get(i).getQuantity());
                obj.put("price", data.get(i).getPrice());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            array.put(obj);
                i++;
        }

        Log.d(TAG, "on "+array);
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


    private void submitdata() throws JSONException {

        map1.put("name", name);
        map1.put("shop_name", company);
        map1.put("mobile", phoneNo);
        map1.put("gst_no", gstNumber);
        map1.put("address", fulladdress);
        map1.put("pincode", pin);
        map1.put("customer_email", email);
        map1.put("items", array);

        final ProgressDialog dialog = ProgressDialog.show(this, "", "");
        RequestClass request=new RequestClass(Request.Method.POST, new JSONObject(map1), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                dialog.dismiss();
                CustomDialogClass.showDialog(PlaceOrder.this, "Order Place", "Ok", "", new CustomDialogClass.WarningResponse() {
                    @Override
                    public void onPositive() {

//                       DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Place_Order").child(UnicID);
//                       reference.setValue(map1);
                        DatabaseReference reference=FirebaseDatabase.getInstance().getReference();
                        reference.child("cart").child(UnicID).setValue(null);

                        customerName.setText("");
                        companyName.setText("");
                        gstNo.setText("");
                        address.setText("");
                        pinCode.setText("");
                        mobileNo.setText("");
                        Intent intent = new Intent(PlaceOrder.this, Main2Activity.class);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onNegative() {

                    }
                });
                Log.d(TAG, String.valueOf(map1));
                Log.d(TAG,response.toString());

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PlaceOrder.this, "Error"+error, Toast.LENGTH_SHORT).show();
            }
        },PlaceOrder.this);

        RequestQueue queue= Volley.newRequestQueue(PlaceOrder.this);
        queue.add(request);
    }

}
