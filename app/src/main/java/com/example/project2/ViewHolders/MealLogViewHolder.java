package com.example.project2.ViewHolders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.project2.R;

public class MealLogViewHolder extends RecyclerView.ViewHolder {
    private final TextView mealLogViewItem;
    private MealLogViewHolder(View mealLogView) {
        super(mealLogView);
        mealLogViewItem = mealLogView.findViewById(R.id.recyclerItemTextView);
    }

    public void bind(String text) {
        mealLogViewItem.setText(text);
    }

    static MealLogViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.meallog_recycler_item, parent, false);
        return new MealLogViewHolder(view);
    }
}
