package io.anime.otakubook;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.anime.otakubook.adapters.FavoritesAdapter;
import io.anime.otakubook.models.animes.Favorite;

public class FavoritesActivity extends AppCompatActivity {

    @BindView(R.id.favoritesRV)
    RecyclerView recyclerView;
    @BindView(R.id.navigationSearch)
    BottomNavigationView navigation;
    @BindView(R.id.loading_indicator)
    ProgressBar loadingIndicator;

    private FavoritesWidget widget = new FavoritesWidget();
    private DatabaseReference databaseReference;
    private SharedPreferences sharedPreferences;
    private Parcelable recyclerViewState;
    private FavoritesAdapter favoritesAdapter;
    private List<String> favoriteTitles = new ArrayList<>();

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.nav_home_from_search_and_favorites:
                    finish();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        ButterKnife.bind(this);

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        sharedPreferences = getApplicationContext().getSharedPreferences("LoginData", MODE_PRIVATE);
        final String userName = sharedPreferences.getString("userName", null);

        if (savedInstanceState != null) {
            recyclerViewState = savedInstanceState.getParcelable("FAVORITES_LIST_STATE");
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        loadingIndicator.setVisibility(View.VISIBLE);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        queryDBForFavorites(userName);

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                favoritesAdapter.removeFavorite(viewHolder.getAdapterPosition());
                favoriteTitles = favoritesAdapter.getAllItemsFromTheList();
                updateDB(userName, favoriteTitles);
                widgetContentUpdate();
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

            }
        };

        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);
    }

    private void queryDBForFavorites(String userName) {
        if (userName != null) {
            databaseReference.child("favoriteAnimes").child(userName)
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Favorite dbStuff = dataSnapshot.getValue(Favorite.class);
                            try {
                                favoriteTitles = dbStuff.getFavoriteTitles();
                            } catch (Exception e) {
                                e.getLocalizedMessage();
                            }

                            recyclerView.getLayoutManager().onRestoreInstanceState(recyclerViewState);
                            favoritesAdapter = new FavoritesAdapter(favoriteTitles);
                            recyclerView.setAdapter(favoritesAdapter);

                            widgetContentUpdate();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
        } else {
            favoriteTitles = null;
            loadingIndicator.setVisibility(View.INVISIBLE);
            Toast.makeText(getApplicationContext(), getString(R.string.please_log_in), Toast.LENGTH_LONG).show();
        }
    }

    private void widgetContentUpdate() {
        sharedPreferences.edit().putString("stringToWidget", formattedFavorites(favoriteTitles)).commit();
        int[] ids = AppWidgetManager.getInstance(getApplication()).getAppWidgetIds(new ComponentName(getApplication(), FavoritesWidget.class));
        widget.onUpdate(getApplicationContext(), AppWidgetManager.getInstance(getApplicationContext()), ids);
        loadingIndicator.setVisibility(View.INVISIBLE);
    }

    private String formattedFavorites(List<String> list) {
        String formattedFavorites = getString(R.string.yourFavorites) + TextUtils.join(", \n-", list);

        return formattedFavorites;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("FAVORITES_LIST_STATE", recyclerView.getLayoutManager().onSaveInstanceState());
    }

    private void updateDB(String userName, List<String> favoriteTitles) {
        Favorite input = new Favorite(favoriteTitles);
        Map<String, Object> postValues = input.toMap();
        Map<String, Object> childUpdates = new HashMap<>();

        childUpdates.put("/favoriteAnimes/" + userName, postValues);

        databaseReference.updateChildren(childUpdates);
    }
}
