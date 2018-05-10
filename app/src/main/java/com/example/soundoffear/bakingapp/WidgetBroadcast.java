package com.example.soundoffear.bakingapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

public class WidgetBroadcast extends AppWidgetProvider {

    public static final String ACTION_CURRENT_WIDGET_NAME = "action_start_detail_activity";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for(int i = 0; i < appWidgetIds.length; i++) {
            Intent intentService = new Intent(context, WidgetListViewRemoteServices.class);
            intentService.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);

            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.recipe_widget_layout);
            remoteViews.setRemoteAdapter(R.id.widget_recipe_listview, intentService);

            Intent startActivityIntent = new Intent(context, RecipeDetailActivity.class);

            startActivityIntent.setAction(WidgetBroadcast.ACTION_CURRENT_WIDGET_NAME);
            startActivityIntent.putExtra(RecipeListActivity.INGREDIENTS_KEY,
                    context.getSharedPreferences(RecipeListActivity.SHARED_PREFS, Context.MODE_PRIVATE)
                            .getString(RecipeListActivity.SHARED_INGR, "[]"));
            startActivityIntent.putExtra(RecipeListActivity.STEPS_KEY,
                    context.getSharedPreferences(RecipeListActivity.SHARED_PREFS, Context.MODE_PRIVATE)
            .getString(RecipeListActivity.SHARED_STEPS, "[]"));

            PendingIntent pendingIntent = PendingIntent
                    .getActivity(context, 0, startActivityIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            remoteViews.setPendingIntentTemplate(R.id.widget_recipe_listview, pendingIntent);

            remoteViews.setTextViewText(R.id.widget_recipe_name, context
                    .getSharedPreferences(RecipeListActivity.SHARED_PREFS, Context.MODE_PRIVATE)
                    .getString(RecipeListActivity.SHARED_TITLE, "No Name"));

            appWidgetManager.updateAppWidget(appWidgetIds[i], remoteViews);

        }

    }

    @Override
    public void onReceive(Context context, Intent intent) {

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        if(intent.getAction().equals(AppWidgetManager.ACTION_APPWIDGET_UPDATE)) {
            int appWidgetIds[] = appWidgetManager.getAppWidgetIds(new ComponentName(context, WidgetBroadcast.class));

            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_recipe_listview);
        }

        super.onReceive(context, intent);

    }
}
