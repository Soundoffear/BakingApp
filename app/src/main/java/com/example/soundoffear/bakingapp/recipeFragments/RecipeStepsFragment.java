package com.example.soundoffear.bakingapp.recipeFragments;

import android.content.Context;
import android.content.Intent;
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
import com.example.soundoffear.bakingapp.StepDetailsActivity;
import com.example.soundoffear.bakingapp.Utilities.JSON_Utilis;
import com.example.soundoffear.bakingapp.adapters.StepsRVAdapter;
import com.example.soundoffear.bakingapp.interfaces.OnRecyclerViewClickListener;
import com.example.soundoffear.bakingapp.models.StepsModel;

import java.util.List;

public class RecipeStepsFragment extends Fragment implements OnRecyclerViewClickListener {

    private List<StepsModel> stepsModelList;
    StepsRVAdapter stepsRVAdapter;

    private static final String SAVED_STEPS = "steps_saved_data_JSON";
    public static final String RECIPE_BUNDLE = "all_data_recipe";
    public static final String RECIPE_POSITION = "position";
    private static final String SAVED_PREFS = "Saved_prefs_JSON";
    private String receivedBundle;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recipe_steps_fragment_layout, container,false);

        receivedBundle = getArguments().getString(RecipeDetailActivity.STEPS_ARG_FRAGMENT);

        if(receivedBundle != null) {
            stepsModelList = JSON_Utilis.allSteps(receivedBundle);
        } else {
            receivedBundle = getActivity().getSharedPreferences(SAVED_PREFS, Context.MODE_PRIVATE).getString(SAVED_STEPS, "[]");
            stepsModelList = JSON_Utilis.allSteps(receivedBundle);
        }

        RecyclerView recyclerView = view.findViewById(R.id.recipeStepsFragmentRecyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        stepsRVAdapter = new StepsRVAdapter(getContext(), stepsModelList, new OnRecyclerViewClickListener() {
            @Override
            public void onRecyclerViewClick(int position) {
                Intent intent = new Intent(getActivity(), StepDetailsActivity.class);
                Bundle recipeData = new Bundle();
                recipeData.putString(RECIPE_BUNDLE, receivedBundle);
                recipeData.putInt(RECIPE_POSITION, position);
                intent.putExtras(recipeData);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(stepsRVAdapter);

        return view;
    }

    @Override
    public void onRecyclerViewClick(int position) {

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        getActivity().getSharedPreferences(SAVED_PREFS, Context.MODE_PRIVATE).edit().putString(SAVED_STEPS, receivedBundle).apply();
    }


}
