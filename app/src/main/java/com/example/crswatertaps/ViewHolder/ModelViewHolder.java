package com.example.crswatertaps.ViewHolder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.crswatertaps.R;

public class ModelViewHolder extends RecyclerView.ViewHolder {
    private ImageView image;
    private TextView title;

    public ModelViewHolder(@NonNull View itemView) {
        super(itemView);
        image = itemView.findViewById(R.id.imgIcon);
        title = itemView.findViewById(R.id.txtTitle);

    }
    public void setRow(String name,String imageUrl){

        title.setText(name);


    }

}