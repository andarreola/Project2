package com.example.project2.ViewHolders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import com.example.project2.R;

public class UserViewHolder extends RecyclerView.ViewHolder {

    private final TextView userViewItem;
    private UserViewHolder(View userView){
        super(userView);
        userViewItem = userView.findViewById(R.id.recyclerItemTextView);
    }

    public void bind(String text){
        userViewItem.setText(text);
    }

    static UserViewHolder create(ViewGroup parent){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_recycler_item, parent, false);
        return new UserViewHolder(view);
    }
}
