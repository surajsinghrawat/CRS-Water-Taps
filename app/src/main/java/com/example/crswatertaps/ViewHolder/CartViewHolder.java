package com.example.crswatertaps.ViewHolder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.crswatertaps.Model.CartModel;
import com.example.crswatertaps.R;

public class CartViewHolder extends RecyclerView.ViewHolder {
    ImageView image;
    private TextView model,series,price,quantity;


    //String[] dish;
    public CartViewHolder(@NonNull View itemView) {
        super(itemView);
        image = itemView.findViewById(R.id.imageView);
        model = itemView.findViewById(R.id.model);
        series = itemView.findViewById(R.id.series1);
        price = itemView.findViewById(R.id.price);
        quantity = itemView.findViewById(R.id.quantity);
    }

    public void setRow(CartModel data){

        model.setText(data.getModelId());
        series.setText(data.getSeriesId());
        price.setText(data.getPrice()+"");
        quantity.setText(data.getQuantity()+"");



    }
}
