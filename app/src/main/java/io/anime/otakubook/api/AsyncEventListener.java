package io.anime.otakubook.api;


import io.anime.otakubook.models.animes.anime.AnimeResponse;
import io.anime.otakubook.models.animes.search.SearchResponse;
import io.anime.otakubook.models.animes.trending.TrendingResponse;

public interface AsyncEventListener {

    void onSuccessTrendingAnimes(TrendingResponse trendingAnimes);

    void onSuccessSearch(SearchResponse animes);

    void onSuccessAnime(AnimeResponse anime);

}
