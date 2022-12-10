package com.softaloy.softe_commerce.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.gif.GifBitmapProvider;
import com.softaloy.softe_commerce.R;
import com.softaloy.softe_commerce.activities.DetailedActivity;
import com.softaloy.softe_commerce.models.ViewAllModel;

import java.util.List;

public class ViewAllAdapter extends RecyclerView.Adapter<ViewAllAdapter.ViewHolder> {

    Context context;
    List<ViewAllModel> viewAllModelList;

    public ViewAllAdapter(Context context, List<ViewAllModel> viewAllModelList) {
        this.context = context;
        this.viewAllModelList = viewAllModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_all_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        Glide.with(context).load(viewAllModelList.get(position).getImg_url()).into(holder.imageView);
        holder.name.setText(viewAllModelList.get(position).getName());
        holder.description.setText(viewAllModelList.get(position).getDescription());
        holder.rating.setText(viewAllModelList.get(position).getRating());
       holder.price.setText(viewAllModelList.get(position).getPrice()+"/kg");

        if (viewAllModelList.get(position).getType().equals("egg")){
            holder.price.setText(viewAllModelList.get(position).getPrice()+"/Dozen");
        }
        if (viewAllModelList.get(position).getType().equals("milk")){
            holder.price.setText(viewAllModelList.get(position).getPrice()+"/Litre");
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, DetailedActivity.class);
                intent.putExtra("detail", viewAllModelList.get(position));
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return viewAllModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView name, description, rating, price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView= itemView.findViewById(R.id.view_all_img);
            name = itemView.findViewById(R.id.view_all_name);
            description = itemView.findViewById(R.id.view_all_description);
            rating = itemView.findViewById(R.id.view_all_rating);
            price = itemView.findViewById(R.id.vew_all_price);
        }
    }
}
