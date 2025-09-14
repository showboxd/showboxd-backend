// TMDBClient.java
package com.app.showboxd.common.tmdb;

import info.movito.themoviedbapi.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class TMDBClient {
    private final TmdbApi tmdbApi;
    private final TmdbSearch tmdbSearch;
    private final TmdbTvSeries tmdbTvSeries;
    private final TmdbTvSeriesLists tmdbTvSeriesList;
    private final TmdbTvSeasons tmdbTvSeasons;
    private final TmdbTvEpisodes tmdbTvEpisodes;

    public TMDBClient(@Value("${tmdb.api.key}") String apiKey) {
        this.tmdbApi = new TmdbApi(apiKey);
        this.tmdbSearch = tmdbApi.getSearch();
        this.tmdbTvSeries = tmdbApi.getTvSeries();
        this.tmdbTvSeriesList = tmdbApi.getTvSeriesLists();
        this.tmdbTvSeasons= tmdbApi.getTvSeasons();
        this.tmdbTvEpisodes  = tmdbApi.getTvEpisodes();
    }
}