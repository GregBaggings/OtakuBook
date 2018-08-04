package io.anime.otakubook.models.animes;

import io.anime.otakubook.models.animes.anime.AnimeResponse;

public enum TempEnumForAnimes {

    INSTANCE;
    AnimeResponse anime;

    public AnimeResponse getAnime() {
        return anime;
    }

    public void setAnime(AnimeResponse anime) {
        this.anime = anime;
    }


}
