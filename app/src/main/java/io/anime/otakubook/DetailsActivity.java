package io.anime.otakubook;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.anime.otakubook.adapters.CharactersAdapter;
import io.anime.otakubook.api.AnimeRequestHandler;
import io.anime.otakubook.api.AnimesAPI;
import io.anime.otakubook.api.AnimesAPIInterface;
import io.anime.otakubook.api.AsyncEventListener;
import io.anime.otakubook.models.animes.Favorite;
import io.anime.otakubook.models.animes.TempEnumForAnimes;
import io.anime.otakubook.models.animes.anime.AnimeResponse;
import io.anime.otakubook.models.animes.search.SearchResponse;
import io.anime.otakubook.models.animes.trending.TrendingResponse;
import retrofit2.Call;

public class DetailsActivity extends AppCompatActivity {

    @BindView(R.id.navigationDetails)
    BottomNavigationView navigation;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.posterIV)
    ImageView posterIV;
    @BindView(R.id.titleTV)
    TextView titleTV;
    @BindView(R.id.numberOfEpisodesTV)
    TextView episodesTV;
    @BindView(R.id.synopsisTV)
    TextView synopsisTV;
    @BindView(R.id.charactersRV)
    RecyclerView recyclerView;
    @BindView(R.id.favoriteIBT)
    ImageButton favorite;
    @BindView(R.id.loading_indicator)
    ProgressBar loadingIndicator;

    private AnimesAPIInterface service = AnimesAPI.getRetrofit().create(AnimesAPIInterface.class);
    private TempEnumForAnimes enumForAnime = TempEnumForAnimes.INSTANCE;
    private AnimeResponse anime;
    private CharactersAdapter charactersAdapter;
    private Parcelable recyclerViewState;
    private DatabaseReference databaseReference;
    private SharedPreferences sharedPreferences;
    private List<String> favoriteTitles = new ArrayList<>();
    private int yellow = Color.YELLOW;
    private int gray = Color.GRAY;
    private String userName;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.nav_details_prev:
                    getPrevOrNextAnime(anime.getMalId() - 1);
                    return true;
                case R.id.nav_home_from_details:
                    finish();
                    return true;
                case R.id.nav_details_next:
                    getPrevOrNextAnime(anime.getMalId() + 1);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);

        sharedPreferences = getApplicationContext().getSharedPreferences("LoginData", MODE_PRIVATE);
        if (savedInstanceState != null) {
            recyclerViewState = savedInstanceState.getParcelable("CHARACTER_LIST_STATE");
            anime = savedInstanceState.getParcelable("ANIME");
        }

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        anime = enumForAnime.getAnime();
        userName = sharedPreferences.getString("userName", null);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        charactersAdapter = new CharactersAdapter(getApplicationContext(), anime);
        recyclerView.setAdapter(charactersAdapter);
        recyclerView.getLayoutManager().onRestoreInstanceState(recyclerViewState);

        updateUI();
        loadingIndicator.setVisibility(View.INVISIBLE);
    }

    private void queryAndUpdateUI() {
        databaseReference = FirebaseDatabase.getInstance().getReference();

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

                            if (favoriteTitles.contains(anime.getTitle())) {
                                favorite.setColorFilter(yellow);
                                favorite.setSelected(true);
                            } else {
                                favorite.setColorFilter(gray);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("CHARACTER_LIST_STATE", recyclerView.getLayoutManager().onSaveInstanceState());
        outState.putParcelable("ANIME", anime);
    }

    private void updateUI() {
        titleTV.setText(anime.getTitle());
        synopsisTV.setText(anime.getSynopsis());
        episodesTV.setText(String.valueOf(anime.getEpisodes()));
        Picasso.with(getApplicationContext())
                .load(anime.getImageUrl())
                .placeholder(R.drawable.common_google_signin_btn_icon_light).resize(350, 450)
                .error(R.drawable.common_full_open_on_phone).into(posterIV);
        charactersAdapter = new CharactersAdapter(getApplicationContext(), anime);
        recyclerView.setAdapter(charactersAdapter);
        queryAndUpdateUI();
    }

    private void getPrevOrNextAnime(int malId) {
        Call<AnimeResponse> call = service.getAnimeByID(malId);
        loadingIndicator.setVisibility(View.VISIBLE);
        AnimeRequestHandler requestHandler = new AnimeRequestHandler(call, getApplicationContext(), new AsyncEventListener() {

            @Override
            public void onSuccessTrendingAnimes(TrendingResponse trendingAnimes) {

            }

            @Override
            public void onSuccessSearch(SearchResponse animes) {

            }

            @Override
            public void onSuccessAnime(AnimeResponse otherAnime) {
                anime = otherAnime;
                enumForAnime.setAnime(anime);
                updateUI();
                loadingIndicator.setVisibility(View.INVISIBLE);
            }
        });

        requestHandler.execute(call);
    }

    @OnClick(R.id.favoriteIBT)
    public void addAndRemoveFavorite() {
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

                            if (!favoriteTitles.contains(anime.getTitle())) {
                                favoriteTitles.add(anime.getTitle());
                                writeData(userName, favoriteTitles);
                                favorite.setColorFilter(yellow);
                                favorite.setSelected(true);
                            } else {
                                favoriteTitles.remove(anime.getTitle());
                                writeData(userName, favoriteTitles);
                                favorite.setColorFilter(gray);
                                favorite.setSelected(false);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
        } else {
            Toast.makeText(getApplicationContext(), getString(R.string.login_to_save_favorite), Toast.LENGTH_LONG).show();
        }
    }

    private void writeData(String userName, List<String> favoriteTitles) {
        Favorite input = new Favorite(favoriteTitles);
        Map<String, Object> postValues = input.toMap();
        Map<String, Object> childUpdates = new HashMap<>();

        childUpdates.put("/favoriteAnimes/" + userName, postValues);

        databaseReference.updateChildren(childUpdates);
    }
}
