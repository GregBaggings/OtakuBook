package io.anime.otakubook.models.animes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Favorite {

    private List<String> favoriteTitles;

    public Favorite() {
    }

    public Favorite(List<String> favoriteTitles) {
        this.favoriteTitles = favoriteTitles;
    }

    public List<String> getFavoriteTitles() {
        return favoriteTitles;
    }

    public void setFavoriteTitles(List<String> favoriteTitles) {
        this.favoriteTitles = favoriteTitles;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("favoriteTitles", favoriteTitles);

        return result;
    }

    @Override
    public String toString() {
        return "Favorite(s): " + favoriteTitles;
    }
}
