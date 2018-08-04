package io.anime.otakubook;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.RemoteViews;

import static android.content.Context.MODE_PRIVATE;


public class FavoritesWidget extends AppWidgetProvider {

    static SharedPreferences sharedPreferences;
    private String favoritesList = null;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, String favoritesList,
                                int appWidgetId) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.favorite_list_widget);
        sharedPreferences = context.getSharedPreferences("LoginData", MODE_PRIVATE);
        favoritesList = sharedPreferences.getString("stringToWidget", null);

        if (favoritesList != null) {
            views.setTextViewText(R.id.favoritesOfTheUserTV, favoritesList);
            Log.i("TESZT", "Favorites at the widget: " + favoritesList);
        }

        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        views.setOnClickPendingIntent(R.id.favoritesOfTheUserTV, pendingIntent);
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, favoritesList, appWidgetId);
        }
    }
}
