package com.example.finalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.ViewHolder> {
    List<Location> locations;
    Context mContext;

    public LocationAdapter(Context context, List<Location> locations) {
        this.locations = locations;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflate a layout from xml and return ViewHolder
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        //inflate custom layout
        View locationView = inflater.inflate(R.layout.item_location, parent, false);

        //return new view holder
        ViewHolder viewHolder = new ViewHolder(locationView);

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // populate data into the item through view holder

        //grab data model aka location object
        Location location = locations.get(position);

        //set the view based on the data and view names
        holder.textView_name.setText(location.getLocationName());
        holder.textView_description.setText(location.getDescription());

    }

    @Override
    public int getItemCount() {
        // returns total number of items in list
        return locations.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView_name;
        TextView textView_description;

        public ViewHolder (View itemView) {
            super(itemView);
            textView_name = itemView.findViewById(R.id.textView_name);
            textView_description = itemView.findViewById(R.id.textView_description);
        }

    }
}