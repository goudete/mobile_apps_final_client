package com.example.finalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    List<User> users;

    public UserAdapter(List<User> users) {
        this.users = users;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflate a layout from xml and return ViewHolder
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        //inflate custom layout
        View userView = inflater.inflate(R.layout.item_user, parent, false);

        //return new view holder
        ViewHolder viewHolder = new ViewHolder(userView);

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // populate data into the item through view holder

        //grab data model aka location object
        User user = users.get(position);

        //set the view based on the data and view names
        holder.textView_userName.setText(user.getUserName());

    }

    @Override
    public int getItemCount() {
        // returns total number of items in list
        return users.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView_userName;

        public ViewHolder (View itemView) {
            super(itemView);
            textView_userName = itemView.findViewById(R.id.textView_userName);
        }

    }
}