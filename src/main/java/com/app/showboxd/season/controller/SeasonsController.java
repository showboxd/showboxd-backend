package com.app.showboxd.season.controller;

import com.app.showboxd.common.tmdb.TMDBClient;
import info.movito.themoviedbapi.model.tv.season.TvSeasonDb;
import info.movito.themoviedbapi.tools.TmdbException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/tv/{seriesId}")
public class SeasonsController {

    @Autowired
    private TMDBClient tmdbClient;

    @GetMapping("/season/{seasonNumber}")
    public ResponseEntity<TvSeasonDb> getSeason(
            @PathVariable("seriesId") int seriesId,
            @PathVariable("seasonNumber") int seasonNumber) throws TmdbException {
        TvSeasonDb tvSeasonDb = tmdbClient.getTmdbTvSeasons().getDetails(seriesId, seasonNumber,null);
        if (tvSeasonDb != null) {
            return ResponseEntity.ok(tvSeasonDb);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
