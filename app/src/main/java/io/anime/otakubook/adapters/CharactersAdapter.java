package io.anime.otakubook.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.anime.otakubook.R;
import io.anime.otakubook.models.animes.anime.AnimeResponse;


public class CharactersAdapter extends RecyclerView.Adapter<CharactersAdapter.ViewHolder> {

    private AnimeResponse anime;
    private Context myContext;

    public CharactersAdapter(Context myContext, AnimeResponse anime) {
        this.myContext = myContext;
        this.anime = anime;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.character_item, parent, false);
        ButterKnife.bind(this, itemView);

        return new CharactersAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.characterNameTV.setText(anime.getCharacter().get(position).getName());
        holder.characterRoleTV.setText(anime.getCharacter().get(position).getRole());
        Picasso.with(myContext)
                .load(anime.getCharacter().get(position).getImageUrl().replace("r/46x64/", ""))
                .placeholder(R.drawable.ic_cloud_download_black_24dp).resize(350, 450)
                .error(R.drawable.ic_error_outline_black_24dp).into(holder.characterIV);
    }

    @Override
    public int getItemCount() {
        return anime.getCharacter().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.characterNameTV)
        TextView characterNameTV;
        @BindView(R.id.characterRoleTV)
        TextView characterRoleTV;
        @BindView(R.id.characterIV)
        ImageView characterIV;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
