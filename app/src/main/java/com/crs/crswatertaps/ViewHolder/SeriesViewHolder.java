package com.crs.crswatertaps.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.crs.crswatertaps.R;
import com.squareup.picasso.Picasso;

public class SeriesViewHolder extends RecyclerView.ViewHolder {
    private ImageView image;
    private TextView title;

    public SeriesViewHolder(@NonNull View itemView) {
        super(itemView);
        image = itemView.findViewById(R.id.imgIcon);
        title = itemView.findViewById(R.id.txtTitle);

    }
    public void setRow(String name,String imageUrl){

        title.setText(name);
        Picasso.with(image.getContext()).load(imageUrl).into(image);

    }

}
