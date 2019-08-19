package com.example.crswatertaps.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.crswatertaps.Activity.SeriesActivity;
import com.example.crswatertaps.Model.ModelClass;
import com.example.crswatertaps.R;
import com.example.crswatertaps.Adapter.programingAdapter;
import com.example.crswatertaps.ViewHolder.ModelViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;


/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryFragment extends Fragment {
    private FirebaseRecyclerAdapter adapter;
    private Query query;
//    private int[] images={R.drawable.crs,R.drawable.crs_taps,R.drawable.t_taps,
//            R.drawable.taps,R.drawable.w_taps,R.drawable.water,R.drawable.crs};

    private RecyclerView programingList;
    private RecyclerView.LayoutManager layoutManager;


    public CategoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View layout =  inflater.inflate(R.layout.fragment_category, container, false);

        programingList= layout.findViewById(R.id.programingList);
        layoutManager=new GridLayoutManager(getActivity(),2);

        programingList.setHasFixedSize(true);
        programingList.setLayoutManager(layoutManager);



        query = FirebaseDatabase.getInstance()
                .getReference()
                .child("models");

        FirebaseRecyclerOptions<ModelClass> options =
                new FirebaseRecyclerOptions.Builder<ModelClass>()
                        .setQuery(query, ModelClass.class)
                        .build();

         adapter = new FirebaseRecyclerAdapter<ModelClass, ModelViewHolder>(options) {
            @Override
            public ModelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                // Create a new instance of the ViewHolder, in this case we are using a custom
                // layout called R.layout.message for each item
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item_activity, parent, false);

                return new ModelViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(ModelViewHolder holder, int position, final ModelClass model) {
                // Bind the Chat object to the ChatHolder
                // ...
                holder.setRow(model.getName(),model.getImageUrl());
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
        programingList.setAdapter(adapter);
        return layout;
    }

    @Override
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
