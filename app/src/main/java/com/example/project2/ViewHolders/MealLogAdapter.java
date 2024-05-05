package com.example.project2.ViewHolders;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import com.example.project2.database.entities.Meal;

public class MealLogAdapter extends ListAdapter<Meal, MealLogViewHolder> {
    public MealLogAdapter(@NonNull DiffUtil.ItemCallback<Meal> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public MealLogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return MealLogViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull MealLogViewHolder holder, int position) {
        Meal current = getItem(position);
        holder.bind(current.toString());
    }


    public static class MealLogDiff extends DiffUtil.ItemCallback<Meal> {
        @Override
        public boolean areItemsTheSame(@NonNull Meal oldItem, @NonNull Meal newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Meal oldItem, @NonNull Meal newItem) {
            return oldItem.equals(newItem);
        }
    }
}
