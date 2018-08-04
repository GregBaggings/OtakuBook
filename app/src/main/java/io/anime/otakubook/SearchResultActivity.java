package io.anime.otakubook;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.ProgressBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.anime.otakubook.adapters.SearchResultAdapter;
import io.anime.otakubook.models.animes.search.SearchResponse;

public class SearchResultActivity extends AppCompatActivity {

    @BindView(R.id.searchResultRV)
    RecyclerView recyclerView;
    @BindView(R.id.navigationSearch)
    BottomNavigationView navigation;
    @BindView(R.id.loading_indicator)
    ProgressBar progressBar;

    private Parcelable recyclerViewState;
    private SearchResultAdapter searchResultAdapter;
    private SearchResponse animes;

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
        setContentView(R.layout.activity_search_result);
        ButterKnife.bind(this);

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        if (savedInstanceState != null) {
            recyclerViewState = savedInstanceState.getParcelable("SEARCH_RESULT_LIST_STATE");
        }

        animes = getIntent().getExtras().getParcelable("SEARCH_RESULT");
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.getLayoutManager().onRestoreInstanceState(recyclerViewState);
        searchResultAdapter = new SearchResultAdapter(getApplicationContext(), progressBar, animes);
        recyclerView.setAdapter(searchResultAdapter);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("SEARCH_RESULT_LIST_STATE", recyclerView.getLayoutManager().onSaveInstanceState());
    }
}
