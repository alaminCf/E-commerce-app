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
import com.softaloy.softe_commerce.R;
import com.softaloy.softe_commerce.activities.NavCategoryActivity;
import com.softaloy.softe_commerce.models.NavCategoryDetailedModel;
import com.softaloy.softe_commerce.models.NavCategoryModle;

import java.util.List;

public class NavCategoryDetailedAdapter extends RecyclerView.Adapter<NavCategoryDetailedAdapter.ViewHolder> {

    Context context;
    List<NavCategoryDetailedModel> list;


    public NavCategoryDetailedAdapter(Context context, List<NavCategoryDetailedModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.nav_category_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

       Glide.with(context).load(list.get(position).getImg_url()).into(holder.imageView);
       holder.name.setText(list.get(position).getName());
       holder.price.setText(list.get(position).getPrice());




    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView name, price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.nav_dtld_img);
            name = itemView.findViewById(R.id.nav_dtld_name);
            price = itemView.findViewById(R.id.nav_dtld_price);

        }
    }
}
