package com.example.crswatertaps.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.crswatertaps.Activity.SeriesActivity;
import com.example.crswatertaps.Adapter.CartAdapter;
import com.example.crswatertaps.Model.CartModel;
import com.example.crswatertaps.Model.ModelClass;
import com.example.crswatertaps.R;
import com.example.crswatertaps.ViewHolder.CartViewHolder;
import com.example.crswatertaps.ViewHolder.ModelViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;


/**
 * A simple {@link Fragment} subclass.
 */
public class CartFragment extends Fragment {

    private int[] images={R.drawable.crs};

   private RecyclerView.LayoutManager layoutManager1;
    RecyclerView cartier;

    private FirebaseRecyclerAdapter adapter;
    private Query query;



    public CartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View layout =  inflater.inflate(R.layout.fragment_cart, container, false);
        // Inflate the layout for this fragment
        cartier =layout.findViewById(R.id.cartitem);
        layoutManager1=new GridLayoutManager(getActivity(),1);
        cartier.setLayoutManager(layoutManager1);
       // cartier.setAdapter(new CartAdapter(images,getActivity()));
        query = FirebaseDatabase.getInstance()
                .getReference()
                .child("cart")
                .child("asd");

        FirebaseRecyclerOptions<CartModel> options =
                new FirebaseRecyclerOptions.Builder<CartModel>()
                        .setQuery(query, CartModel.class)
                        .build();

        adapter = new FirebaseRecyclerAdapter<CartModel, CartViewHolder>(options) {

            @Override
            public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                // Create a new instance of the ViewHolder, in this case we are using a custom
                // layout called R.layout.message for each item
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.cart_item, parent, false);

                return new CartViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(CartViewHolder holder, int position, final CartModel model) {
                // Bind the Chat object to the ChatHolder
                // ...
                holder.setRow(model);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(getActivity(), SeriesActivity.class);
                        intent.putExtra("modelId",model.getId());
                        startActivity(intent);
                    }
                });
            }
        };
        // Inflate the layout for this fragment
        cartier.setAdapter(adapter);
        return layout;
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
