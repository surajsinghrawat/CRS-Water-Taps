package com.example.crswatertaps.Activity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.crswatertaps.Model.CartModel;
import com.example.crswatertaps.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    TextView textView,txt1;
    TextView textView1;
    TextView textView2;
    TextView textView3;
    ImageView imageView;
    EditText quantityEditText;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final String name=getIntent().getStringExtra("name");
        final String id=getIntent().getStringExtra("id");
        setupActionBar(name);
        setContentView(R.layout.activity_detail);
        imageView=findViewById(R.id.image_display);
        final String img=getIntent().getStringExtra("image");
        //imageView.setImageResource(getIntent().getIntExtra("image",00));
        Picasso.with(imageView.getContext()).load(img).into(imageView);
//MODEL
        final String modelName=getIntent().getStringExtra("modelName");
        textView=findViewById(R.id.model);
        textView.setText(modelName);
//SERIES
        final String seriesName=getIntent().getStringExtra("seriesName");
        if(getIntent().getStringExtra("seriesName")!=null) {
            textView1 = findViewById(R.id.series1);
            txt1=findViewById(R.id.txt1);
            textView1.setVisibility(View.VISIBLE);
            txt1.setVisibility(View.VISIBLE);
            textView1.setText(seriesName);
        }
//PRICE
        final long price=getIntent().getLongExtra("price",0L);
        textView2=findViewById(R.id.price);
        textView2.setText(price+"");

 //

// ...
        mDatabase = FirebaseDatabase.getInstance().getReference();

        quantityEditText=findViewById(R.id.Quantity);
        button=findViewById(R.id.btnAddCart);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity=Integer.parseInt(quantityEditText.getText().toString());
               int price1= (int) (quantity*price);
                CartModel cartItem=new CartModel();
                cartItem.setName(name);
                cartItem.setId(id);
                cartItem.setPrice(price1);
                cartItem.setQuantity(quantity);
                cartItem.setModelId(modelName);
                cartItem.setSeriesId(seriesName);
                cartItem.setImageUrl(img);


                mDatabase.child("cart").child("asd").child(id).setValue(cartItem);
                Toast.makeText(DetailActivity.this, "Successfully Add ", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(DetailActivity.this,Main2Activity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void setupActionBar(String name) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {

            actionBar.setTitle(name);
        }
    }
}
