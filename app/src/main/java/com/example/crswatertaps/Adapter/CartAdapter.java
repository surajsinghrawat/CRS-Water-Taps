package com.example.crswatertaps.Adapter;

import android.content.Context;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.crswatertaps.R;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ImageViewHolder> {
    private int[] images;
    private Context context;
    private String[] model={"Star Model",
            "Premium Model","Royal Model",
            "Touch Model","Electrical's Round Plate",
            "CPVC Plug","West Pipe"};

    public CartAdapter(int[] images, Context context) {
        this.images=images;
        this.context=context;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item,parent,false);
        ImageViewHolder imageViewHolder=new ImageViewHolder(view,context,images);
        return imageViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder Holder, int i) {
        int image_is=images[i];
        String text=model[i];
        Holder.Model.setText(text);
        Holder.image.setImageResource(image_is);
        Holder.Price.setText(text);
        Holder.Quantity.setText(text);
        Holder.Series.setText(text);

    }


    @Override
    public int getItemCount() {
        return images.length;
    }


    public static class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView Model,Series,Price,Quantity;
        Context context;
        int[] images;

        //String[] dish;
        public ImageViewHolder(@NonNull View itemView,Context context,int[] imeges) {
            super(itemView);
            image = itemView.findViewById(R.id.imageView);
            Model = itemView.findViewById(R.id.model);
            Series = itemView.findViewById(R.id.series1);
            Price = itemView.findViewById(R.id.price);
            Quantity = itemView.findViewById(R.id.quantity);

//                itemView.setOnClickListener(this);
            this.context = context;
            this.images = imeges;

        }
    }
}

