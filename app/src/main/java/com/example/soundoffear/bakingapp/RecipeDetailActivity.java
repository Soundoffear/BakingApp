package com.example.soundoffear.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.design.widget.TabLayout;
import android.support.test.espresso.IdlingResource;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.soundoffear.bakingapp.recipeFragments.IngredientsFragment;
import com.example.soundoffear.bakingapp.recipeFragments.RecipeStepsFragment;

public class RecipeDetailActivity extends AppCompatActivity {

    private String sIngredients;
    private String sSteps;

    private final static String INGREDIENT_DATA = "saved_ing_data_JSON";
    private final static String STEPS_DATA = "saved_steps_data_JSON";

    @Nullable
    private SimpleIdlingResource mIdlingResource;

    @VisibleForTesting
    @NonNull
    public IdlingResource getmIdlingResource() {
        if(mIdlingResource == null) {
            mIdlingResource = new SimpleIdlingResource();
        }
        return mIdlingResource;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipie_detail);
        Toolbar toolbar = findViewById(R.id.detailActivityToolBar);
        setSupportActionBar(toolbar);

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        TabLayout tabLayout = findViewById(R.id.detailActivityTabLayout);
        TabLayoutAdapter tabLayoutAdapter = new TabLayoutAdapter(getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.detailTabFragmentViewPager);
        viewPager.setAdapter(tabLayoutAdapter);
        tabLayout.setupWithViewPager(viewPager);

        if(savedInstanceState == null) {
            Intent receivedDataFromList = getIntent();
            sIngredients = receivedDataFromList.getStringExtra(RecipeListActivity.INGREDIENTS_KEY);
            sSteps = receivedDataFromList.getStringExtra(RecipeListActivity.STEPS_KEY);
        } else {
            sIngredients = savedInstanceState.getString(INGREDIENT_DATA);
            sSteps = savedInstanceState.getString(STEPS_DATA);
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(INGREDIENT_DATA, sIngredients);
        outState.putString(STEPS_DATA, sSteps);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    public static final String ING_ARG_FRAGMENT = "fragment_ingredients_arguments";
    public static final String STEPS_ARG_FRAGMENT = "fragment_steps_arguments";

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            NavUtils.navigateUpTo(this, new Intent(this, RecipeListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    class TabLayoutAdapter extends FragmentPagerAdapter {

        TabLayoutAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    IngredientsFragment ingredientsFragment = new IngredientsFragment();
                    Bundle ingredientsBundle = new Bundle();
                    ingredientsBundle.putString(ING_ARG_FRAGMENT, sIngredients);
                    ingredientsFragment.setArguments(ingredientsBundle);
                    return ingredientsFragment;
                case 1:
                    RecipeStepsFragment recipeStepsFragment = new RecipeStepsFragment();
                    Bundle stepsBundle = new Bundle();
                    stepsBundle.putString(STEPS_ARG_FRAGMENT, sSteps);
                    recipeStepsFragment.setArguments(stepsBundle);
                    return recipeStepsFragment;

            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Ingredients";
                case 1:
                    return "Recipe Steps";
            }
            return null;
        }
    }
}
