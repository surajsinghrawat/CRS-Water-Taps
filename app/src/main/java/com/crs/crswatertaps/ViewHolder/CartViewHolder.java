package com.crs.crswatertaps.ViewHolder;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.crs.crswatertaps.Model.CartModel;
import com.crs.crswatertaps.R;
import com.squareup.picasso.Picasso;

public class CartViewHolder extends RecyclerView.ViewHolder {
    ImageView image;
    private TextView model,series,price,quantity;
    public Button removeButton;


    //String[] dish;
    public CartViewHolder(@NonNull View itemView) {
        super(itemView);
        image = itemView.findViewById(R.id.imageView);
        model = itemView.findViewById(R.id.model);
        series = itemView.findViewById(R.id.series1);
        price = itemView.findViewById(R.id.price);
        quantity = itemView.findViewById(R.id.quantity);
        removeButton = itemView.findViewById(R.id.btnRemove);
    }

    public void setRow(CartModel data){

        model.setText(data.getModelId());
        series.setText(data.getSeriesId());
        price.setText(data.getPrice()+"");
        quantity.setText(data.getQuantity()+"");
       // image.setImageDrawable(Drawable.createFromPath(data.getImageUrl()));
        Picasso.with(image.getContext()).load(data.getImageUrl()).into(image);



    }
}
