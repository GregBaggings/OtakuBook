package io.anime.otakubook;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.concurrent.ThreadLocalRandom;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;
import io.anime.otakubook.adapters.TrendingAnimesAdapter;
import io.anime.otakubook.api.AnimeRequestHandler;
import io.anime.otakubook.api.AnimesAPI;
import io.anime.otakubook.api.AnimesAPIInterface;
import io.anime.otakubook.api.AsyncEventListener;
import io.anime.otakubook.api.SearchRequestHandler;
import io.anime.otakubook.api.TrendingAnimesRequestHandler;
import io.anime.otakubook.models.animes.TempEnumForAnimes;
import io.anime.otakubook.models.animes.anime.AnimeResponse;
import io.anime.otakubook.models.animes.search.SearchResponse;
import io.anime.otakubook.models.animes.trending.TrendingResponse;
import retrofit2.Call;

public class MainActivity extends AppCompatActivity {

    private static final int RESULT_CODE_SIGN_IN = 1;
    @BindView(R.id.adView)
    AdView mAdView;
    @BindView(R.id.trendingAnimesRV)
    RecyclerView recyclerView;
    @BindView(R.id.navigation)
    BottomNavigationView navigation;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.searchButton)
    Button searchButton;
    @BindView(R.id.searchET)
    EditText searchET;
    @BindView(R.id.loggedInTV)
    TextView loggedInTV;
    @BindView(R.id.loading_indicator)
    ProgressBar loadingIndicator;

    private Menu loginMenu;
    private AnimesAPIInterface service = AnimesAPI.getRetrofit().create(AnimesAPIInterface.class);
    private TrendingAnimesAdapter trendingAnimesAdapter;
    private GoogleSignInClient googleSignInClient;
    private FirebaseAuth firebaseAuth;
    private String currentUserName;
    private SharedPreferences sharedPreferences;
    private Parcelable recyclerViewState;
    private Intent intent;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_favorites:
                    intent = new Intent(getApplicationContext(), FavoritesActivity.class);
                    startActivity(intent);
                    return true;
                case R.id.navigation_surprise:
                    getRandomAnime();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        if (savedInstanceState != null) {
            recyclerViewState = savedInstanceState.getParcelable("TRENDING_LIST_STATE");
        }

        setSupportActionBar(toolbar);
        invalidateOptionsMenu();
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.getLayoutManager().onRestoreInstanceState(recyclerViewState);
        sharedPreferences = getApplicationContext().getSharedPreferences("LoginData", MODE_PRIVATE);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, gso);

        firebaseAuth = FirebaseAuth.getInstance();
        currentUserName = sharedPreferences.getString("userName", null);
        if (currentUserName != null) {
            loggedInTV.setText(String.format(getString(R.string.logged_in_with_title), currentUserName));

        }

        MobileAds.initialize(this, "1");
        if (checkInternetConnection()) {
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);

            Call<TrendingResponse> call = service.getTrendingAnimes();
            getTrendingResponse(call);
        } else {
            loadingIndicator.setVisibility(View.INVISIBLE);
            Toast.makeText(getApplicationContext(), getString(R.string.error_on_no_connection), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("TRENDING_LIST_STATE", recyclerView.getLayoutManager().onSaveInstanceState());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.login, menu);
        this.loginMenu = menu;

        sharedPreferences = getApplicationContext().getSharedPreferences("LoginData", MODE_PRIVATE);
        currentUserName = sharedPreferences.getString("userName", null);

        if (currentUserName != null) {
            MenuItem loginField = menu.findItem(R.id.loginField);
            loginField.setTitle(getString(R.string.logout));

        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.loginField) {
            signIn(item);
        }

        return super.onOptionsItemSelected(item);
    }

    private void getRandomAnime() {
        int id = ThreadLocalRandom.current().nextInt(1, 1000);
        loadingIndicator.setVisibility(View.VISIBLE);
        Call<AnimeResponse> call = service.getAnimeByID(id);
        AnimeRequestHandler requestHandler = new AnimeRequestHandler(call, getApplicationContext(), new AsyncEventListener() {

            @Override
            public void onSuccessTrendingAnimes(TrendingResponse trendingAnimes) {

            }

            @Override
            public void onSuccessSearch(SearchResponse animes) {

            }

            @Override
            public void onSuccessAnime(AnimeResponse anime) {
                loadingIndicator.setVisibility(View.INVISIBLE);
                intent = new Intent(getApplicationContext(), DetailsActivity.class);
                TempEnumForAnimes enumForAnime = TempEnumForAnimes.INSTANCE;
                enumForAnime.setAnime(anime);
                startActivity(intent);
            }
        });

        requestHandler.execute(call);
    }

    private void getTrendingResponse(Call call) {
        loadingIndicator.setVisibility(View.VISIBLE);
        TrendingAnimesRequestHandler requestHandler = new TrendingAnimesRequestHandler(call, getApplicationContext(), new AsyncEventListener() {

            @Override
            public void onSuccessTrendingAnimes(TrendingResponse trendingAnimes) {
                trendingAnimesAdapter = new TrendingAnimesAdapter(getApplicationContext(), loadingIndicator, trendingAnimes);
                recyclerView.setAdapter(trendingAnimesAdapter);
                loadingIndicator.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onSuccessSearch(SearchResponse animes) {

            }

            @Override
            public void onSuccessAnime(AnimeResponse anime) {

            }
        });

        requestHandler.execute(call);
    }

    @OnClick(R.id.searchButton)
    public void getSearchResponse() {
        String title = searchET.getText().toString();
        Call<SearchResponse> call = service.searchByAnimeTitle(title);

        if (!title.equals("")) {
            loadingIndicator.setVisibility(View.VISIBLE);
            SearchRequestHandler requestHandler = new SearchRequestHandler(call, getApplicationContext(), new AsyncEventListener() {

                @Override
                public void onSuccessTrendingAnimes(TrendingResponse trendingAnimes) {

                }

                @Override
                public void onSuccessSearch(SearchResponse animes) {
                    Intent intent = new Intent(getApplicationContext(), SearchResultActivity.class);
                    intent.putExtra("SEARCH_RESULT", animes);
                    loadingIndicator.setVisibility(View.INVISIBLE);
                    startActivity(intent);
                }

                @Override
                public void onSuccessAnime(AnimeResponse anime) {

                }
            });

            requestHandler.execute(call);
        } else {
            Toast.makeText(getApplicationContext(), getString(R.string.error_on_invalid_input), Toast.LENGTH_LONG).show();
        }
    }

    private boolean checkInternetConnection() throws NullPointerException {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null;
    }

    @Optional
    @OnClick(R.id.loginField)
    public void signIn(MenuItem item) {
        currentUserName = sharedPreferences.getString("userName", null);

        if (currentUserName == null) {
            Intent signInIntent = googleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, RESULT_CODE_SIGN_IN);
        } else {
            firebaseAuth.signOut();
            item.setTitle(getString(R.string.login));
            sharedPreferences.edit().putString("userName", null).commit();
            loggedInTV.setText("");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_CODE_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                authWithGoogleThroughFirebase(account);
            } catch (ApiException e) {
                Log.e("TESZT", getString(R.string.error_on_login), e);
            }
        }
    }

    private void authWithGoogleThroughFirebase(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            currentUserName = firebaseAuth.getCurrentUser().getDisplayName();
                            sharedPreferences.edit().putString("userName", currentUserName).commit();
                            loggedInTV.setText(String.format(getString(R.string.logged_in_with_title), currentUserName));

                            MenuItem login = loginMenu.findItem(R.id.loginField);
                            login.setTitle(getString(R.string.logout));
                        } else {
                            Toast.makeText(getApplicationContext(), getString(R.string.error_on_authentication), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
