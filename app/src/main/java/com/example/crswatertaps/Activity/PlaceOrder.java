package com.example.crswatertaps.Activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.crswatertaps.CommonAction.CustomDialogClass;
import com.example.crswatertaps.Model.CartModel;
import com.example.crswatertaps.Model.ModelClass;
import com.example.crswatertaps.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class PlaceOrder extends AppCompatActivity {

    EditText customerName, companyName, gstNo, address, pinCode, mobileNo;
    String name, company, gstNumber, fulladdress, email, UnicID, itemName, itemDetails, itemPrice, itemQuantity;
    String pin, phoneNo;
    Button submit;
    CartModel data;
    public static final String TAG = "DATA";
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference mReference;
    List<CartModel> cartModelList;
    HashMap<String, String> map1 = new HashMap<>();
    HashMap<String,String>map=new HashMap<>();
    ArrayList<String>item=new ArrayList<>();
    ArrayList<String>item2=new ArrayList<>();
    StringBuilder errorBuilder;
    public boolean flag=false;

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
        itemList();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateForm()){
                    submitdata();
                }else {
                    CustomDialogClass.showWarning(PlaceOrder.this,errorBuilder.toString(),"Ok",null);
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
        CartModel list=respons;
        itemName = list.getName();
        itemDetails=""+list.getModelId()+list.getSeriesId();
        itemPrice= String.valueOf(list.getPrice());
        itemQuantity= String.valueOf(list.getQuantity());

//            map.put("item_name",list.getName());
//            map.put("details",list.getModelId()+list.getSeriesId());
//            map.put("price", String.valueOf(list.getPrice()));
//            map.put("quentity", String.valueOf(list.getQuantity()));


        item.add(0,list.getName());
        item.add(0,list.getModelId()+list.getSeriesId());
        item.add(0,String.valueOf(list.getPrice()));
        item.add(0,String.valueOf(list.getQuantity()));


        Log.d("asd",""+item);


    }


    private void itemList(){
//
//            Iterator<String>iterator=item.iterator();
//            while (iterator.hasNext()){
//                map.put("itemName", item.get(0));
//            }
//            Log.d(  TAG, String.valueOf(map));


    }


    private boolean validateForm(){
        boolean valid = true;

        errorBuilder = new StringBuilder();

        name = customerName.getText().toString();
        if (name.isEmpty()) {
            errorBuilder.append("Name Is Empty"+"\n");
            valid = false;
        }

        company = companyName.getText().toString();
        if (company.isEmpty()) {
            errorBuilder.append("Company Name Is Empty"+"\n");
            valid = false;
        }

        gstNumber = gstNo.getText().toString();
        if (gstNumber.isEmpty()) {
            errorBuilder.append("GST Number Is Empty"+"\n");
            valid = false;
        }

        fulladdress = address.getText().toString();
        if (fulladdress.isEmpty()) {
            errorBuilder.append("Address Is Empty"+"\n");
            valid = false;
        }

        pin = pinCode.getText().toString();
        if (pin.isEmpty()) {
            errorBuilder.append("Pin Code Is Empty"+"\n");
            valid = false;
        }
        phoneNo = mobileNo.getText().toString();
        if (phoneNo.isEmpty()) {
            errorBuilder.append("Mobile Number Is Empty"+"\n");
            valid = false;
        }



        return valid;
    }







    private void submitdata() {


        map1.put("name",name);
        map1.put("shop_name",company);
        map1.put("mobile",phoneNo);
        map1.put("gst_no",gstNumber);
        map1.put("address",fulladdress);
        map1.put("pincode",pin);
        map1.put("customer_email",email);


//        map1.put("itemName", item.get(0));
//        map1.put("itemDetails", item.get(1));
//        map1.put("itemPrice", item.get(2));
//        map1.put("itemQuantity", item.get(3));
        map1.put("itemName",item.get(0));


//        map1.put("items", itemName);
//
//        map1.put("details",itemDetails);
//        map1.put("quentity",itemQuantity);
//        map1.put("price",itemPrice);
//

        Log.d(TAG, String.valueOf(map1));

    }

}
