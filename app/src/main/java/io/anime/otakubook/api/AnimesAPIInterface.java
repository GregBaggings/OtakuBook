package io.anime.otakubook.api;

import io.anime.otakubook.models.animes.anime.AnimeResponse;
import io.anime.otakubook.models.animes.search.SearchResponse;
import io.anime.otakubook.models.animes.trending.TrendingResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface AnimesAPIInterface {

    @GET("https://api.jikan.moe/top/anime/1/airing")
    Call<TrendingResponse> getTrendingAnimes();

    @GET("https://api.jikan.moe/anime/{malId}/characters_staff/")
    Call<AnimeResponse> getAnimeByID(@Path("malId") int id);

    @GET(" https://api.jikan.moe/search/anime")
    Call<SearchResponse> searchByAnimeTitle(@Query("q") String title);

}
