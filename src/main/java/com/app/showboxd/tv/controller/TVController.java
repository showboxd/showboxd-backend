package com.app.showboxd.tv.controller;

import com.app.showboxd.common.tmdb.TMDBClient;
import com.app.showboxd.tv.dto.TVSearchResponse;
import com.app.showboxd.tv.dto.TVShow;
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
    public ResponseEntity<TVSearchResponse> searchShows(@RequestParam String query) {
        TVSearchResponse tvSearchResponse = tmdbClient.searchShows(
                query,
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
    public ResponseEntity<TVShow> getSeriesDetails(@PathVariable int seriesId)
    {
        TVShow tvShow = tmdbClient.getShowDetails(seriesId);
        return ResponseEntity.ok(tvShow);
    }
}
