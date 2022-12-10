package com.softaloy.softe_commerce.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.softaloy.softe_commerce.R;
import com.softaloy.softe_commerce.models.RecommendedModle;

import java.util.List;

public class RecommendedAdapter extends RecyclerView.Adapter<RecommendedAdapter.ViewHolder> {

    Context context;
    List<RecommendedModle> recommendedModleList;

    public RecommendedAdapter(Context context, List<RecommendedModle> recommendedModleList) {
        this.context = context;
        this.recommendedModleList = recommendedModleList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recommended_items,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Glide.with(context).load(recommendedModleList.get(position).getImg_url()).into(holder.imageView);
        holder.name.setText(recommendedModleList.get(position).getName());
        holder.description.setText(recommendedModleList.get(position).getDescription());
        holder.rating.setText(recommendedModleList.get(position).getRating());


    }

    @Override
    public int getItemCount() {
        return recommendedModleList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView name, description, rating;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.rec_img);
            name = itemView.findViewById(R.id.rec_name);
            description = itemView.findViewById(R.id.rec_description);
            rating = itemView.findViewById(R.id.rec_rating);


        }
    }
}
