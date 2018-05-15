package com.example.soundoffear.bakingapp;


import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;

import com.example.soundoffear.bakingapp.Utilities.JSON_Utilis;
import com.example.soundoffear.bakingapp.Utilities.NetworkUtilis;
import com.example.soundoffear.bakingapp.models.RecipeModel;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class ActivityTesting {

    List<RecipeModel> recipeModels;

    @Rule
    public ActivityTestRule<RecipeDetailActivity> stepDetailsActivityActivityTestRule =
            new ActivityTestRule<RecipeDetailActivity>(RecipeDetailActivity.class) {
                @Override
                protected Intent getActivityIntent() {
                    Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
                    Intent intent = new Intent(context, RecipeDetailActivity.class);

                    try {
                        recipeModels = JSON_Utilis.allRecipes(NetworkUtilis.downloadJSON_RecipeList(NetworkUtilis.buildURL()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    intent.putExtra(RecipeListActivity.INGREDIENTS_KEY, recipeModels.get(0).getRecIngredients());
                    intent.putExtra(RecipeListActivity.STEPS_KEY, recipeModels.get(0).getRecSteps());

                    return intent;

                }
            };

    @Test
    public void areIngredientsDisplayedInRecyclerView() {
        if (getRVcount() > 0) {
            onView(withId(R.id.ingredientsFragmentRecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        }
    }

    private int getRVcount() {
        RecyclerView recyclerView = stepDetailsActivityActivityTestRule.getActivity().findViewById(R.id.ingredientsFragmentRecyclerView);
        return recyclerView.getAdapter().getItemCount();
    }

}
