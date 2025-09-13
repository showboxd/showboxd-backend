package com.app.showboxd.episode.controller;

import com.app.showboxd.common.tmdb.TMDBClient;
import info.movito.themoviedbapi.model.tv.episode.TvEpisodeDb;
import info.movito.themoviedbapi.tools.TmdbException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/tv/{seriesId}/season/{seasonNumber}")
public class EpisodeController {

    @Autowired
    private TMDBClient tmdbClient;

    @GetMapping("/episode/{episodeNumber}")
    public ResponseEntity<TvEpisodeDb> getSeason(
            @PathVariable("seriesId") int seriesId,
            @PathVariable("seasonNumber") int seasonNumber,
            @PathVariable("episodeNumber") int episodeNumber) throws TmdbException {
        TvEpisodeDb tvEpisodeDb = tmdbClient.getTmdbTvEpisodes().getDetails(seriesId, seasonNumber,episodeNumber,null);
        if (tvEpisodeDb != null) {
            return ResponseEntity.ok(tvEpisodeDb);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
