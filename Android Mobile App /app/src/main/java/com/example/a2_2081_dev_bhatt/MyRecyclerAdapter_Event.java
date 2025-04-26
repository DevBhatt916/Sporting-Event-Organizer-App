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

public class MyRecyclerAdapter_Event extends RecyclerView.Adapter<MyRecyclerAdapter_Event.CustomViewHolder> {

    //Arraylist to hold event data
    private ArrayList<Event> data = new ArrayList<>();

    //Method to set data for the adapter
    public void setData(ArrayList<Event> data) {
        this.data = data;
    }


    // ViewHolder class to hold references to views within each item
    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_card_layout, parent, false);
        return new CustomViewHolder(v);
    }


    //Constructors to initialize views
    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        Event event = data.get(position);
        holder.tvEventID.setText(event.getEventID());
        holder.tvEventName.setText(event.getEventName());
        holder.tvEventCategory.setText(event.getCategoryName());
        holder.tvTicketsAvailable.setText(String.valueOf(event.getTicketsAvailable()));
        holder.tvIsActive.setText(event.isActive() ? "Active" : "Inactive");

        holder.itemView.setOnClickListener(v -> {
            Context context = holder.itemView.getContext();
            Intent intent =new Intent(context, EventGoogleResult.class);
            intent.putExtra("countryName" , event.getEventName());
            context.startActivity(intent);
        });
    }

    //Method to determine number of items in data set
    @Override
    public int getItemCount() {
        return data.size();
    }

    // ViewHolder class to hold references to views within each item
    public static class CustomViewHolder extends RecyclerView.ViewHolder {

        public TextView tvEventID;
        public TextView tvEventName;
        public TextView tvEventCategory;
        public TextView tvTicketsAvailable;
        public TextView tvIsActive;

        // Constructor to initialize views
        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            tvEventID = itemView.findViewById(R.id.cv_eventId);
            tvEventName = itemView.findViewById(R.id.cv_EventName);
            tvEventCategory = itemView.findViewById(R.id.cv_CategoryId);
            tvTicketsAvailable = itemView.findViewById(R.id.cv_TicketsAvailable);
            tvIsActive = itemView.findViewById(R.id.cv_IsActive);
        }
    }
}
