package com.example.a2_2081_dev_bhatt;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyRecyclerAdapter_Category extends RecyclerView.Adapter<MyRecyclerAdapter_Category.CustomViewHolder> {

    ArrayList<Category> data = new ArrayList<Category>();

    public void setData(ArrayList<Category> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_card_layout, parent, false);
        return new CustomViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.tvCategoryID.setText(data.get(position).getCategoryID());
        holder.tvCategoryName.setText(data.get(position).getCategoryName());
        holder.tvCategoryCount.setText(String.valueOf(data.get(position).getEventCount()));

        if (data.get(position).getIsActive()) {
            holder.tvIsActive.setText("Active");
        } else {
            holder.tvIsActive.setText("Inactive");
        }

        holder.itemView.setOnClickListener(v -> {
            Context context = holder.itemView.getContext();
            Intent intent = new Intent(context, A3MapsActivity.class);
            intent.putExtra("cLocation", data.get(position).getLocation());
            intent.putExtra("cName", data.get(position).getCategoryName());
            context.startActivity(intent);
        });
    }






    @Override
    public int getItemCount() {
        return this.data != null ? this.data.size() : 0;
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        public TextView tvCategoryID;
        public TextView tvCategoryName;
        public TextView tvCategoryCount;
        public TextView tvIsActive;
        public TextView tvLocationName;

        public View itemView;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            tvCategoryID = itemView.findViewById(R.id.cv_eventId);
            tvCategoryName = itemView.findViewById(R.id.cv_EventName);
            tvCategoryCount = itemView.findViewById(R.id.cv_CategoryId);
            tvIsActive = itemView.findViewById(R.id.cv_IsActive);
            tvLocationName = itemView.findViewById(R.id.locationName);
        }
    }
}
