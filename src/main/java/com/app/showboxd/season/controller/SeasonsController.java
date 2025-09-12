package com.app.showboxd.season.controller;

import com.app.showboxd.common.tmdb.TMDBClient;
import com.app.showboxd.season.dto.Season;
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
    public ResponseEntity<Season> getSeason(
            @PathVariable("seriesId") int seriesId,
            @PathVariable("seasonNumber") int seasonNumber) {
        Season season = tmdbClient.getSeason(seriesId, seasonNumber);
        if (season != null) {
            return ResponseEntity.ok(season);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
