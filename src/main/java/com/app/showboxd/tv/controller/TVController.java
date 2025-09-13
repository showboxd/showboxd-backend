package com.app.showboxd.tv.controller;

import com.app.showboxd.common.tmdb.TMDBClient;
import info.movito.themoviedbapi.model.core.TvSeriesResultsPage;
import info.movito.themoviedbapi.model.tv.series.TvSeriesDb;
import info.movito.themoviedbapi.tools.TmdbException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/tv")
public class TVController {

    @Autowired
    private TMDBClient tmdbClient;

    @GetMapping("/search")
    public ResponseEntity<TvSeriesResultsPage> searchShows(@RequestParam String query) throws TmdbException {
        TvSeriesResultsPage tvSearchResponse = tmdbClient.getTmdbSearch().searchTv(
                query,
                null,
                null,
                null,
                null,
                null
        );
        if (tvSearchResponse != null) {
            return ResponseEntity.ok(tvSearchResponse);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{seriesId}")
    public ResponseEntity<TvSeriesDb> getSeriesDetails(@PathVariable int seriesId) throws TmdbException {
        TvSeriesDb tvShowDb = tmdbClient.getTmdbTvSeries().getDetails(seriesId,null);
        return ResponseEntity.ok(tvShowDb);
    }
}
