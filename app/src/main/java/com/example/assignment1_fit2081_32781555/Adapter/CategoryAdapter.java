package com.example.assignment1_fit2081_32781555.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment1_fit2081_32781555.Activities.GoogleMapActivity;
import com.example.assignment1_fit2081_32781555.Objects.Category;

import java.util.ArrayList;
import com.example.assignment1_fit2081_32781555.R;


public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    private static final ArrayList<Category>categoryList = new ArrayList<>();
    public CategoryAdapter() {
        setHasStableIds(true);
    }
    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_list_item_category, parent, false);
        return new CategoryViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = categoryList.get(position);
        holder.tvCategoryID.setText(category.getCategoryID());
        holder.tvCategoryName.setText(category.getCategoryName());
        holder.tvEventCount.setText(String.valueOf(category.getEventCount()));
        holder.tvEventLocation.setText(String.valueOf(category.getEventLocation()));
        holder.tvIsActive.setText(String.valueOf(category.isActive()));

        holder.cardView.setOnClickListener(v -> {
            String selectedCountry = category.getEventLocation();
            String selectedCategoryName = category.getCategoryName();
            Context context  = holder.cardView.getContext();
            Intent intent = new Intent(context, GoogleMapActivity.class);
            intent.putExtra("categoryLocation",selectedCountry);
            intent.putExtra("categoryTitle",selectedCategoryName);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(ArrayList<Category> arrayList){
        categoryList.clear();
        categoryList.addAll(arrayList);
        notifyDataSetChanged();
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {

        TextView tvCategoryID;
        TextView tvCategoryName;
        TextView tvEventCount;
        TextView tvEventLocation;
        TextView tvIsActive;
        View cardView;
        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView;
            tvCategoryID = itemView.findViewById(R.id.tvCategoryID);
            tvCategoryName = itemView.findViewById(R.id.tvCategoryName);
            tvEventCount = itemView.findViewById(R.id.tvEventCount);
            tvIsActive = itemView.findViewById(R.id.tvIsActive);
            tvEventLocation = itemView.findViewById(R.id.tvEventLocation);
        }
    }

    @Override
    public long getItemId(int position) {
        return categoryList.get(position).getCategoryID().hashCode();
    }
}