package com.example.crswatertaps.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.crswatertaps.Activity.ProductActivity;
import com.example.crswatertaps.R;

public class SeriesAdapter extends RecyclerView.Adapter<SeriesAdapter.ImageViewHolder> {

    private int[] images;
    private Context context;
    private String[] dish ={"Star Model",
            "Premium Model","Royal Model",
            "Touch Model","Electrical's Round Plate",
            "CPVC Plug","West Pipe"};

    public SeriesAdapter(int[] imeges, Context context){
        this.images=imeges;
        this.context=context;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int ViewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_activity,parent,false);
        ImageViewHolder imageViewHolder=new ImageViewHolder(view,context,images);
        return imageViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder Holder,final int i) {
        int image_id=images[i];
        String Text=dish[i];
        Holder.image.setImageResource(image_id);
        Holder.Title.setText(Text);

        Holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, ProductActivity.class);
                context.startActivity(intent);
            }
        });

//        Holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent1=new Intent(context,ProductActivity.class);
//                context.startActivity(intent1);
//            }
//        });
    }




    @Override
    public int getItemCount() {
        return images.length;
    }


    public static class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView Title;
        Context context;
        int[] images;

        //String[] dish;
        public ImageViewHolder(@NonNull View itemView, Context context, int[] imeges) {
            super(itemView);
            image = itemView.findViewById(R.id.imgIcon);
            Title = itemView.findViewById(R.id.txtTitle);

//                itemView.setOnClickListener(this);
            this.context = context;
            this.images = imeges;

        }
    }

}
