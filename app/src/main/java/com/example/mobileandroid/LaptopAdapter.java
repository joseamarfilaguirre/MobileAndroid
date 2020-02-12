package com.example.mobileandroid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class LaptopAdapter extends RecyclerView.Adapter<LaptopAdapter.ViewHolder> {
    private List<Laptop> mDataset;
    private Context mContext;
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView titleLaptop;
        public TextView descriptionLaptop;
        public ImageView imageLaptop;
        public RelativeLayout relativeLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            relativeLayout = (RelativeLayout)itemView.findViewById(R.id.conteiner_item_laptop);
            imageLaptop = (ImageView) itemView.findViewById(R.id.image_laptop);
            titleLaptop = (TextView)itemView.findViewById(R.id.title_laptop);
            descriptionLaptop = (TextView)itemView.findViewById(R.id.description_laptop);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION) {
                    }
                }
            });
        }
    }
    // Provide a suitable constructor (depends on the kind of dataset)
    public LaptopAdapter(Context context, List<Laptop> myDataset) {
        mDataset = myDataset;
        mContext = context;
    }
    // Create new views (invoked by the layout manager)
//    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_laptop, parent, false);// set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Laptop laptop = mDataset.get(i);
        String desc,text;
        desc = laptop.getDescription();
        if (desc.length()>100)text = desc.substring(0,99).concat("...");
        else text = desc.concat("...");
        viewHolder.titleLaptop.setText(laptop.getTitle());
        viewHolder.descriptionLaptop.setText(text);
        Glide.with(mContext)
                .load(laptop.getImage())
                //.apply(RequestOptions.placeholderOf(R.drawable.default_image))
                .into(viewHolder.imageLaptop);
    }
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}

