package com.example.project2.ViewHolders;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.example.project2.database.entities.User;

public class UserAdapter extends ListAdapter<User, UserViewHolder> {
    public UserAdapter(@NonNull DiffUtil.ItemCallback<User> diffCallBack){
        super(diffCallBack);
    }
    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        return UserViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position){
        User current = getItem(position);
        holder.bind(current.toString());
    }

    public static class UserDiff extends DiffUtil.ItemCallback<User>{
        @Override
        public boolean areItemsTheSame(@NonNull User oldItem, @NonNull User newItem){
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull User oldItem, @NonNull User newItem){
            return oldItem.equals(newItem);
        }
    }
}
