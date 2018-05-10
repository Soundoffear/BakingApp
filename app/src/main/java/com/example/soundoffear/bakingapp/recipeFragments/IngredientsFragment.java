package com.example.soundoffear.bakingapp.recipeFragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.soundoffear.bakingapp.R;
import com.example.soundoffear.bakingapp.RecipeDetailActivity;
import com.example.soundoffear.bakingapp.Utilities.JSON_Utilis;
import com.example.soundoffear.bakingapp.adapters.IngredientsRVAdapter;
import com.example.soundoffear.bakingapp.models.IngredientsModel;

import java.util.List;

public class IngredientsFragment extends Fragment {

    private static final String SAVED_JSON = "saved_json_string";
    private static final String SAVED_PREFS = "Saved_prefs_JSON";
    private List<IngredientsModel> ingredientsModels;
    private String ingredients;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ingredients_fragment_layout, container, false);

        ingredients = getArguments().getString(RecipeDetailActivity.ING_ARG_FRAGMENT);

        if(ingredients != null) {
            ingredientsModels = JSON_Utilis.allIngredients(ingredients);
        } else {
            ingredients = getActivity().getSharedPreferences(SAVED_PREFS, Context.MODE_PRIVATE).getString(SAVED_JSON, "[]");
            ingredientsModels = JSON_Utilis.allIngredients(ingredients);
        }

        RecyclerView recyclerView = view.findViewById(R.id.ingredientsFragmentRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        IngredientsRVAdapter ingredientsRVAdapter = new IngredientsRVAdapter(getContext(), ingredientsModels);
        recyclerView.setAdapter(ingredientsRVAdapter);

        return view;

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        getActivity().getSharedPreferences(SAVED_PREFS, Context.MODE_PRIVATE).edit().putString(SAVED_JSON, ingredients).apply();
    }
}
