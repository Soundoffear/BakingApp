package com.example.soundoffear.bakingapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.soundoffear.bakingapp.R;
import com.example.soundoffear.bakingapp.models.IngredientsModel;

import java.util.List;

public class IngredientsRVAdapter extends RecyclerView.Adapter<IngredientsRVAdapter.IngredientsViewHolder> {

    Context context;
    List<IngredientsModel> ingredientsModelList;

    public IngredientsRVAdapter(Context context, List<IngredientsModel> ingredientsModelList) {
        this.context = context;
        this.ingredientsModelList = ingredientsModelList;
    }

    @NonNull
    @Override
    public IngredientsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredient_item_list, parent, false);

        return new IngredientsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientsViewHolder holder, int position) {
        holder.ingredientName.setText(ingredientsModelList.get(position).getIngIngredient());
        holder.ingredientQuantity.setText(ingredientsModelList.get(position).getInaQuantity());
        holder.ingredientMeasure.setText(ingredientsModelList.get(position).getIngMeasure());
    }

    @Override
    public int getItemCount() {
        return ingredientsModelList.size();
    }

    class IngredientsViewHolder extends RecyclerView.ViewHolder {

        TextView ingredientName;
        TextView ingredientQuantity;
        TextView ingredientMeasure;

        IngredientsViewHolder(View view) {
            super(view);

            ingredientName = view.findViewById(R.id.ingredientItemListName);
            ingredientQuantity = view.findViewById(R.id.ingredientItemListQuantityValue);
            ingredientMeasure = view.findViewById(R.id.ingredientItemListMeasureValue);

        }

    }

}
