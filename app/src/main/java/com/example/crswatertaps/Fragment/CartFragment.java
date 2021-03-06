package com.example.crswatertaps.Fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.crswatertaps.Activity.PlaceOrder;
import com.example.crswatertaps.Activity.SeriesActivity;
import com.example.crswatertaps.Adapter.CartAdapter;
import com.example.crswatertaps.Model.CartModel;
import com.example.crswatertaps.Model.ModelClass;
import com.example.crswatertaps.R;
import com.example.crswatertaps.ViewHolder.CartViewHolder;
import com.example.crswatertaps.ViewHolder.ModelViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;


/**
 * A simple {@link Fragment} subclass.
 */
public class CartFragment extends Fragment {

    private int[] images={R.drawable.crs};

   private RecyclerView.LayoutManager layoutManager1;
    RecyclerView cartier;
    private FirebaseAuth mAuth;
    private FirebaseRecyclerAdapter adapter;
    private Query query;
    private Button placeOrdeBtn;
   // private  Button removeBtn;
    private DatabaseReference mDatabase;

    String userID;

    public CartFragment() {
        // Required empty public constructor
    }
    FirebaseUser firebaseUser=mAuth.getInstance().getCurrentUser();
//    userID=firebaseUser.getUid();
//        Log.d("USERID in add",userID);


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final ProgressDialog dialog = ProgressDialog.show(getContext(), "", "");
        View layout =  inflater.inflate(R.layout.fragment_cart, container, false);
        View itemLayout =  inflater.inflate(R.layout.cart_item, container, false);
        // Inflate the layout for this fragment
        placeOrdeBtn = layout.findViewById(R.id.placeOrder);

        //removeBtn =  itemLayout.findViewById(R.id.btnRemove);

        placeOrdeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),PlaceOrder.class);

                startActivity(intent);
            }
        });
        cartier =layout.findViewById(R.id.cartitem);
        layoutManager1=new GridLayoutManager(getActivity(),1);
        cartier.setLayoutManager(layoutManager1);
        FirebaseUser firebaseUser=mAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {
            // User is signed in
            userID=firebaseUser.getUid();
            Log.d("USERID",userID);
        } else {
            Log.d("Not LogIn","ok");
        // No user is signed in
        }
        // cartier.setAdapter(new CartAdapter(images,getActivity()));
        query = FirebaseDatabase.getInstance()
                .getReference()
                .child("cart")
                .child(userID);

        FirebaseRecyclerOptions<CartModel> options =
                new FirebaseRecyclerOptions.Builder<CartModel>()
                        .setQuery(query, CartModel.class)
                        .build();

        adapter = new FirebaseRecyclerAdapter<CartModel, CartViewHolder>(options) {

            @Override
            public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                // Create a new instance of the ViewHolder, in this case we are using a custom
                // layout called R.layout.message for each item
                dialog.dismiss();
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.cart_item, parent, false);

                return new CartViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(CartViewHolder holder, int position, final CartModel model) {
                // Bind the Chat object to the ChatHolder
                // ...
                Log.d("sdf",""+model);
                holder.setRow(model);
                holder.removeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FirebaseUser firebaseUser=mAuth.getInstance().getCurrentUser();
                        userID=firebaseUser.getUid();
                        mDatabase = FirebaseDatabase.getInstance().getReference();
                        mDatabase.child("cart").child(userID).child(model.getId()).setValue(null);
//                        Log.d("Sehal", model.getId());

                    }
                });
//                holder.itemView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Intent intent=new Intent(getActivity(), SeriesActivity.class);
//                        intent.putExtra("modelId",model.getId());
//                        startActivity(intent);
//                    }
//                });
            }

        };
//        removeBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String val="ok";
//                Log.d(val, "onClick: test value");
//                Toast.makeText(view.getContext(),"Remove Item", Toast.LENGTH_LONG).show();
//            }
//        });
        // Inflate the layout for this fragment
        cartier.setAdapter(adapter);

        return layout;

    }

    public void remove(View view){
        String val="ok";
        Log.d(val, "onClick: test value");
        Toast.makeText(view.getContext(),"Remove Item", Toast.LENGTH_LONG).show();
    }


    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }


}
