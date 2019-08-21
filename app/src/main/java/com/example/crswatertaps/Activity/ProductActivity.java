package com.example.crswatertaps.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.crswatertaps.Adapter.ProductAdapter;
import com.example.crswatertaps.Model.ProductModel;
import com.example.crswatertaps.Model.SeriesModel;
import com.example.crswatertaps.R;
import com.example.crswatertaps.ViewHolder.ProductViewHolder;
import com.example.crswatertaps.ViewHolder.SeriesViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class ProductActivity extends AppCompatActivity {
    private FirebaseRecyclerAdapter adapter;
    private Query query;

    private int[] images={R.drawable.crs,R.drawable.crs_taps,R.drawable.t_taps,
            R.drawable.taps,R.drawable.w_taps,R.drawable.water,R.drawable.crs};


    private RecyclerView productlist;
   private RecyclerView.LayoutManager layoutManager;
    private String modelId;
    private String seriesId;
    private int type;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        setupActionBar("Product");

        modelId = getIntent().getStringExtra("modelId");
        seriesId = getIntent().getStringExtra("seriesId");
        type=getIntent().getIntExtra("type",1);

        productlist= findViewById(R.id.productList);
        layoutManager=new GridLayoutManager(this,1);
        productlist.setHasFixedSize(true);
        productlist.setLayoutManager(layoutManager);

        if (type==1) {
            query = FirebaseDatabase.getInstance()
                    .getReference()
                    .child("product")
                    .orderByChild("typeId").equalTo(modelId + "#" + seriesId);
        }else if (type==2){
            query = FirebaseDatabase.getInstance()
                    .getReference()
                    .child("product")
                    .orderByChild("typeId").equalTo(modelId);

        }
        FirebaseRecyclerOptions<ProductModel> options =
                new FirebaseRecyclerOptions.Builder<ProductModel>()
                        .setQuery(query, ProductModel.class)
                        .build();

        adapter = new FirebaseRecyclerAdapter<ProductModel, ProductViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull final ProductModel model) {
                holder.setRow(model.getName(), model.getImageUrl());

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ProductActivity.this, DetailActivity.class);
                        intent.putExtra("modelName", model.getModelId());
                        intent.putExtra("seriesName", model.getSeriesId());
                        intent.putExtra("price", model.getPrice());
                        intent.putExtra("image", model.getImageUrl());
                        intent.putExtra("name", model.getName());
                        intent.putExtra("id",model.getId());
                        startActivity(intent);
                    }
                });

            }

            @Override
            public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                // Create a new instance of the ViewHolder, in this case we are using a custom
                // layout called R.layout.message for each item
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item_activity, parent, false);

                return new ProductViewHolder(view);
            }

        };
        productlist.setAdapter(adapter);

    }

    private void setupActionBar(String title){
        ActionBar actionBar= getSupportActionBar();
        if (actionBar !=null){
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
