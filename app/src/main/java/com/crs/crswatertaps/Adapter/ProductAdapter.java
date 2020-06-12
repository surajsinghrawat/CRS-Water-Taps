package com.crs.crswatertaps.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.crs.crswatertaps.Activity.DetailActivity;
import com.crs.crswatertaps.R;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ImageViewHolder> {
    private int[] images;
    private Context context;
    private String[] dish ={"Star Model","Premium Model","Royal Model","Touch Model",
                            "Electrical's Round Plate","CPVC Plug","West Pipe"};
    private String[] series={"Star Model","Premium Model","Royal Model","Touch Model",
                             "Electrical's Round Plate","CPVC Plug","West Pipe"};
    private String[] price={"100","200","300","400","500","600","700"};


    public ProductAdapter(int[] images, Context context){
        this.images=images;
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
        String Series=series[i];
        String Price=price[i];
        Holder.image.setImageResource(image_id);
        Holder.Title.setText(Text);

        Holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, DetailActivity.class);
                intent.putExtra("image_id",images[i]);
                intent.putExtra("Text",dish[i]);
                intent.putExtra("Series",series[i]);
                intent.putExtra("Price",price[i]);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return images.length;
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView Title;
        Context context;
        int[] images;


        public ImageViewHolder(@NonNull View itemView, Context context, int[] images) {
            super(itemView);
            image = itemView.findViewById(R.id.imgIcon);
            Title = itemView.findViewById(R.id.txtTitle);

            this.context=context;
            this.images=images;
        }
    }

}
