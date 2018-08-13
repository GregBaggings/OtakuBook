package io.anime.otakubook.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.anime.otakubook.DetailsActivity;
import io.anime.otakubook.R;
import io.anime.otakubook.api.AnimesAPI;
import io.anime.otakubook.api.AnimesAPIInterface;
import io.anime.otakubook.models.animes.TempEnumForAnimes;
import io.anime.otakubook.models.animes.anime.AnimeResponse;
import io.anime.otakubook.models.animes.search.SearchResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.ViewHolder> {

    private SearchResponse animes;
    private Context myContext;
    private ProgressBar progressBar;

    public SearchResultAdapter(Context myContext, ProgressBar progressBar, SearchResponse animes) {
        this.myContext = myContext;
        this.progressBar = progressBar;
        this.animes = animes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_item, parent, false);
        ButterKnife.bind(this, itemView);

        return new SearchResultAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Picasso.with(myContext)
                .load(animes.getResult().get(position).getImageUrl().replace("r/100x140/", ""))
                .placeholder(R.drawable.ic_cloud_download_black_24dp)
                .error(R.drawable.ic_error_outline_black_24dp).into(holder.animePosterIV);
        holder.animeTitleTV.setText(animes.getResult().get(position).getTitle());
        holder.animeTypeTV.setText(animes.getResult().get(position).getType());
    }

    @Override
    public int getItemCount() {
        return animes.getResult().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.animePosterIV)
        ImageView animePosterIV;
        @BindView(R.id.animeTitleTV)
        TextView animeTitleTV;
        @BindView(R.id.animeTypeTV)
        TextView animeTypeTV;
        @BindView(R.id.card_view)
        CardView cardView;

        private Intent intent;
        private AnimesAPIInterface service = AnimesAPI.getRetrofit().create(AnimesAPIInterface.class);

        private ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            progressBar.setVisibility(View.INVISIBLE);
            intent = new Intent(myContext, DetailsActivity.class);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int malId = animes.getResult().get(getAdapterPosition()).getMalId();
                    progressBar.setVisibility(View.VISIBLE);
                    Call<AnimeResponse> call = service.getAnimeByID(malId);
                    call.enqueue(new Callback<AnimeResponse>() {

                        @Override
                        public void onResponse(@NonNull Call<AnimeResponse> call, @NonNull Response<AnimeResponse> response) {
                            if (response.body() != null) {
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
