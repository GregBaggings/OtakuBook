package io.anime.otakubook.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.anime.otakubook.R;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.ViewHolder> {

    private List<String> favoriteList;

    public FavoritesAdapter(List<String> favoriteList) {
        this.favoriteList = favoriteList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.favorite_item, parent, false);
        ButterKnife.bind(this, itemView);

        return new FavoritesAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.favoriteTitleTV.setText(favoriteList.get(position));
    }

    @Override
    public int getItemCount() {
        return favoriteList.size();
    }

    public List<String> getAllItemsFromTheList(){
        return favoriteList;
    }

    public void removeFavorite(int position) {
        favoriteList.remove(position);
        notifyItemRemoved(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.favoriteTitleTV)
        TextView favoriteTitleTV;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
