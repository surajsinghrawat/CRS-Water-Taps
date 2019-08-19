package com.example.crswatertaps.Activity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.example.crswatertaps.Model.ModelClass;
import com.example.crswatertaps.Model.SeriesModel;
import com.example.crswatertaps.R;
import com.example.crswatertaps.Adapter.SeriesAdapter;
import com.example.crswatertaps.ViewHolder.ModelViewHolder;
import com.example.crswatertaps.ViewHolder.SeriesViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class SeriesActivity extends AppCompatActivity {
    private FirebaseRecyclerAdapter adapter;
    private Query query;

//    private int[] images={R.drawable.crs,R.drawable.crs_taps,R.drawable.t_taps,
//            R.drawable.taps,R.drawable.w_taps,R.drawable.water,R.drawable.crs};

    private RecyclerView seriesList;
    private RecyclerView.LayoutManager layoutManager;
    private String modelId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_series);
        setupActionBar("Series");
        modelId = getIntent().getStringExtra("modelId");
        seriesList=findViewById(R.id.seriesList);
        layoutManager=new GridLayoutManager(this,2);
        seriesList.setHasFixedSize(true);
        seriesList.setLayoutManager(layoutManager);

//        Toast.makeText(SeriesActivity.this, "ASD", Toast.LENGTH_SHORT).show();
        query = FirebaseDatabase.getInstance()
                .getReference()
                .child("series");

        FirebaseRecyclerOptions<SeriesModel> options =
                new FirebaseRecyclerOptions.Builder<SeriesModel>()
                        .setQuery(query, SeriesModel.class)
                        .build();

        adapter = new FirebaseRecyclerAdapter<SeriesModel, SeriesViewHolder>(options) {
            @Override
            public SeriesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                // Create a new instance of the ViewHolder, in this case we are using a custom
                // layout called R.layout.message for each item
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item_activity, parent, false);

                return new SeriesViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(SeriesViewHolder holder, int position, final SeriesModel model) {
                // Bind the Chat object to the ChatHolder
                // ...
                holder.setRow(model.getName(),model.getImageUrl());

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(SeriesActivity.this, ProductActivity.class);
                        intent.putExtra("modelId",modelId);
                        intent.putExtra("seriesId",model.getId());
                        startActivity(intent);
                    }
                });
            }
        };

        seriesList.setAdapter(adapter);
    }


    private void setupActionBar(String title) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(title);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
