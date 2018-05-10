package com.example.soundoffear.bakingapp;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import com.example.soundoffear.bakingapp.Utilities.JSON_Utilis;
import com.example.soundoffear.bakingapp.Utilities.NetworkUtilis;
import com.example.soundoffear.bakingapp.interfaces.OnRecyclerViewClickListener;
import com.example.soundoffear.bakingapp.models.RecipeModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeListActivity extends AppCompatActivity implements OnRecyclerViewClickListener {

    private List<RecipeModel> recipeModelListFromJSON;
    View recyclerView;

    public static final String INGREDIENTS_KEY = "ingredients";
    public static final String STEPS_KEY = "steps";
    public static boolean isTabletMode = false;

    static final String SHARED_PREFS = "sharedPrefs";
    static final String SHARED_INGR = "sharedIngredients";
    static final String SHARED_TITLE = "sharedTitle";
    static final String SHARED_STEPS = "sharedSteps";

    private GridLayoutManager gridLayoutManager;

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
        setContentView(R.layout.activity_recipie_list);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        recipeModelListFromJSON = new ArrayList<>();

        recyclerView = findViewById(R.id.recipe_list);
        assert recyclerView != null;

        setupRecyclerView((RecyclerView) recyclerView);

        new AsycConnection().execute();

        getmIdlingResource();
    }

    @Override
    public void onRecyclerViewClick(int position) {

    }

    public boolean isTablet() {
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getMetrics(displayMetrics);

        double wInches = displayMetrics.widthPixels / (double) displayMetrics.densityDpi;
        double hInches = displayMetrics.heightPixels / (double) displayMetrics.densityDpi;

        double screenDiagonal = Math.sqrt(Math.pow(wInches, 2) + Math.pow(hInches, 2));

        return screenDiagonal > 7;

    }

    public class AsycConnection extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String jsonString;
            try {
                jsonString = NetworkUtilis.downloadJSON_RecipeList(NetworkUtilis.buildURL());

                recipeModelListFromJSON = JSON_Utilis.allRecipes(jsonString);

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            SimpleItemRecyclerViewAdapter simpleItemRecyclerViewAdapter = new SimpleItemRecyclerViewAdapter(recipeModelListFromJSON, new OnRecyclerViewClickListener() {
                @Override
                public void onRecyclerViewClick(int position) {
                    SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                    String sharedJsonData = recipeModelListFromJSON.get(position).getRecIngredients();
                    sharedPreferences.edit().putString(SHARED_TITLE, recipeModelListFromJSON.get(position).getRecName()).apply();
                    sharedPreferences.edit().putString(SHARED_INGR, sharedJsonData).apply();
                    sharedPreferences.edit().putString(SHARED_STEPS, recipeModelListFromJSON.get(position).getRecSteps()).apply();
                    sendWidgetBroadcast();

                    if(isTablet()) {
                        Intent intent = new Intent(RecipeListActivity.this, ItemListActivity.class);
                        intent.putExtra(INGREDIENTS_KEY, recipeModelListFromJSON.get(position).getRecIngredients());
                        intent.putExtra(STEPS_KEY, recipeModelListFromJSON.get(position).getRecSteps());
                        isTabletMode = true;
                        startActivity(intent);
                    } else if (!isTablet()) {
                        Intent intent = new Intent(RecipeListActivity.this, RecipeDetailActivity.class);
                        intent.putExtra(INGREDIENTS_KEY, recipeModelListFromJSON.get(position).getRecIngredients());
                        intent.putExtra(STEPS_KEY, recipeModelListFromJSON.get(position).getRecSteps());
                        startActivity(intent);
                    }
                }
            });
            ((RecyclerView) recyclerView).setAdapter(simpleItemRecyclerViewAdapter);

        }
    }

    void sendWidgetBroadcast() {
        Intent intent = new Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        intent.setComponent(new ComponentName(getApplicationContext(), WidgetBroadcast.class));
        sendBroadcast(intent);
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        SimpleItemRecyclerViewAdapter simpleItemRecyclerViewAdapter = new SimpleItemRecyclerViewAdapter(recipeModelListFromJSON, new OnRecyclerViewClickListener() {
            @Override
            public void onRecyclerViewClick(int position) {

            }
        });
        if(isTablet()) {
            gridLayoutManager = new GridLayoutManager(this, 3);
        } else {
            gridLayoutManager = new GridLayoutManager(this, 1);
        }
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(simpleItemRecyclerViewAdapter);
    }

    public static class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        List<RecipeModel> list;
        OnRecyclerViewClickListener onRecyclerViewClickListenerRecipe;

        SimpleItemRecyclerViewAdapter(List<RecipeModel> list, OnRecyclerViewClickListener onRecyclerViewClickListener) {
            this.list = list;
            onRecyclerViewClickListenerRecipe = onRecyclerViewClickListener;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recipie_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.tv_name.setText(list.get(position).getRecName());
            holder.tv_id.setText(list.get(position).getRecId());
            holder.tv_servings.setText("Servings: " + list.get(position).getRecServings());
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            @BindView(R.id.id_text) TextView tv_id;
            @BindView(R.id.content) TextView tv_name;
            @BindView(R.id.servings) TextView tv_servings;

            ViewHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);

                view.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                onRecyclerViewClickListenerRecipe.onRecyclerViewClick(getLayoutPosition());
            }
        }
    }
}
