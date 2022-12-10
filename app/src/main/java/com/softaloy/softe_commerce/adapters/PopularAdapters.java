package com.softaloy.softe_commerce.adapters;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.softaloy.softe_commerce.R;
import com.softaloy.softe_commerce.activities.ViewAllActivity;
import com.softaloy.softe_commerce.models.PopularModle;

import java.util.List;

public class PopularAdapters extends RecyclerView.Adapter<PopularAdapters.ViewHolder> {

    private  Context context;
    private List<PopularModle> popularModleList;

    public PopularAdapters(Context context, List<PopularModle> popularModleList) {
        this.context = context;
        this.popularModleList = popularModleList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.popular_item, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        Glide.with(context).load(popularModleList.get(position).getImg_url()).into(holder.popimg);
        holder.name.setText(popularModleList.get(position).getName());
        holder.rating.setText(popularModleList.get(position).getRating());
        holder.description.setText(popularModleList.get(position).getDescription());
        holder.discount.setText(popularModleList.get(position).getDiscount());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ViewAllActivity.class);
                intent.putExtra("type", popularModleList.get(position).getType());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return popularModleList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView popimg;
        TextView name, description, rating, discount;
        public  ViewHolder(@NonNull View itemView) {
            super(itemView);

            popimg = itemView.findViewById(R.id.pop_imge);
            name = itemView.findViewById(R.id.pop_name);
            description = itemView.findViewById(R.id.pop_des);
            rating = itemView.findViewById(R.id.pop_ratting);
            discount = itemView.findViewById(R.id.pop_discount);
        }
    }
}
