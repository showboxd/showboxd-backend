package com.app.showboxd.common.tmdb;

import com.app.showboxd.episode.dto.Episode;
import com.app.showboxd.season.dto.Season;
import com.app.showboxd.tv.dto.TVShow;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import com.app.showboxd.tv.dto.TVSearchResponse;

import java.util.List;

@FeignClient(name = "tmdbClient", url="${tmdb.api.baseUrl}", configuration = TMDBInterceptor.class)
public interface TMDBClient {

    @GetMapping("/search/tv")
    TVSearchResponse searchShows(
            @RequestParam String query,
            @RequestParam(required = false) Boolean include_adult,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) String language,
            @RequestParam(required = false) Integer page
    );

    //top 10 popular

    @GetMapping("tv/{seriesId}")
    TVShow getShowDetails(@PathVariable("seriesId") int seriesId);

    @GetMapping("tv/{seriesId}/season/{seasonNumber}")
    Season getSeason(@PathVariable("seriesId") int seriesId, @PathVariable("seasonNumber")  int seasonNumber);

    @GetMapping("tv/{seriesId}/season/{seasonNumber}/episode/{episodeNumber}")
    Episode getEpisode(
            @PathVariable("seriesId") int seriesId,
            @PathVariable("seasonNumber")  int seasonNumber,
            @PathVariable("episodeNumber")  int episodeNumber
    );
}
