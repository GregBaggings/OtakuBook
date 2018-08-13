package io.anime.otakubook.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.anime.otakubook.DetailsActivity;
import io.anime.otakubook.R;
import io.anime.otakubook.api.AnimesAPI;
import io.anime.otakubook.api.AnimesAPIInterface;
import io.anime.otakubook.models.animes.TempEnumForAnimes;
import io.anime.otakubook.models.animes.anime.AnimeResponse;
import io.anime.otakubook.models.animes.trending.TrendingResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrendingAnimesAdapter extends RecyclerView.Adapter<TrendingAnimesAdapter.ViewHolder> {

    private List<String> posterPaths = new ArrayList<>();
    private Context myContext;
    private TrendingResponse trendingAnimesResponse;
    private ProgressBar progressBar;

    public TrendingAnimesAdapter(Context context, ProgressBar progressBar, TrendingResponse trendingAnimesResponse) {
        this.trendingAnimesResponse = trendingAnimesResponse;
        this.progressBar = progressBar;
        this.myContext = context;
    }

    private void getPosterPaths() {
        for (int i = 0; i < trendingAnimesResponse.getTop().size(); i++) {
            String posterUrlWithoutSize = trendingAnimesResponse.getTop().get(i).getImageUrl().replace("/r/100x140", "");
            posterPaths.add(posterUrlWithoutSize);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.image_row, parent, false);
        ButterKnife.bind(this, itemView);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        getPosterPaths();
        Picasso.with(myContext)
                .load(posterPaths.get(position))
                .placeholder(R.drawable.ic_cloud_download_black_24dp).resize(350, 450)
                .error(R.drawable.ic_error_outline_black_24dp).into(holder.trendingAnimePosterIV);
    }

    @Override
    public int getItemCount() {
        return trendingAnimesResponse.getTop().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.trendingPosterIV)
        ImageView trendingAnimePosterIV;
        private Intent intent;
        private AnimesAPIInterface service = AnimesAPI.getRetrofit().create(AnimesAPIInterface.class);

        private ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            intent = new Intent(myContext, DetailsActivity.class);
            trendingAnimePosterIV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    progressBar.setVisibility(View.VISIBLE);
                    int malId = trendingAnimesResponse.getTop().get(getAdapterPosition()).getMalId();
                    Call<AnimeResponse> call = service.getAnimeByID(malId);
                    call.enqueue(new Callback<AnimeResponse>() {

                        @Override
                        public void onResponse(@NonNull Call<AnimeResponse> call, @NonNull Response<AnimeResponse> response) {
                            if(response.body() != null) {
                                TempEnumForAnimes enumForAnime = TempEnumForAnimes.INSTANCE;
                                enumForAnime.setAnime(response.body());
                                progressBar.setVisibility(View.INVISIBLE);
                                myContext.startActivity(intent);
                            }
                            else{
                                progressBar.setVisibility(View.INVISIBLE);
                                Toast.makeText(myContext, myContext.getString(R.string.error_on_no_result_from_server), Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<AnimeResponse> call, @NonNull Throwable t) {
                            Toast.makeText(myContext, myContext.getString(R.string.error_on_no_result_from_server), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            });
        }
    }
}