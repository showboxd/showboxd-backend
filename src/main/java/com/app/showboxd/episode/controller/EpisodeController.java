package com.app.showboxd.episode.controller;

import com.app.showboxd.common.tmdb.TMDBClient;
import com.app.showboxd.episode.dto.Episode;
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
    public ResponseEntity<Episode> getSeason(
            @PathVariable("seriesId") int seriesId,
            @PathVariable("seasonNumber") int seasonNumber,
            @PathVariable("episodeNumber") int episodeNumber) {
        Episode episode = tmdbClient.getEpisode(seriesId, seasonNumber,episodeNumber);
        if (episode != null) {
            return ResponseEntity.ok(episode);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
