package com.example.soundoffear.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.soundoffear.bakingapp.Utilities.JSON_Utilis;
import com.example.soundoffear.bakingapp.models.IngredientsModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by soundoffear on 20/04/2018.
 */

public class WidgetListViewRemoteServices extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ListViewRemoteViewFactory(this.getApplicationContext(),
                intent);
    }
}

class ListViewRemoteViewFactory implements RemoteViewsService.RemoteViewsFactory {

    static final String SHARED_PREFS = "sharedPrefs";
    static final String SHARED_INGR = "sharedIngredients";


    private Context mContext;
    private List<IngredientsModel> dataList;

    ListViewRemoteViewFactory(Context context, Intent intent) {
        this.mContext = context;
    }

    @Override
    public void onCreate() {
        dataList = JSON_Utilis.allIngredients(mContext.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE).getString(SHARED_INGR, "[]"));;
    }

    @Override
    public void onDataSetChanged() {
        dataList = JSON_Utilis.allIngredients(mContext.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE).getString(SHARED_INGR, "[]"));
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {

        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_listview_item);
        String ingredients = dataList.get(position).getIngIngredient();
        String quantity = dataList.get(position).getInaQuantity();
        String measure = dataList.get(position).getIngMeasure();

        rv.setTextViewText(R.id.widget_ingredient_name, ingredients);
        rv.setTextViewText(R.id.widget_ingredient_quantity, quantity);
        rv.setTextViewText(R.id.widget_ingredient_measure, measure);

        Intent fillInIntent = new Intent();
        Bundle extrasBundle = new Bundle();
        extrasBundle.putString("TEST", "test");
        fillInIntent.putExtras(extrasBundle);
        rv.setOnClickFillInIntent(R.id.widget_ingredient_name, fillInIntent);

        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
