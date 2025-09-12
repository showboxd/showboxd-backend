package com.app.showboxd.season.dto;


import com.app.showboxd.episode.dto.Episode;
import lombok.Data;

import java.util.ArrayList;

@Data
public class Season {
    public String _id;
    public String air_date;
    public ArrayList<Episode> episodes;
    public String name;
    public String overview;
    public int id;
    public String poster_path;
    public int season_number;
    public double vote_average;
}
