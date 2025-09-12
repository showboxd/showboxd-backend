package com.app.showboxd.episode.dto;

import com.app.showboxd.common.dto.Crew;
import com.app.showboxd.common.dto.GuestStar;
import lombok.Data;

import java.util.ArrayList;

@Data
public class Episode {
    public String air_date;
    public int episode_number;
    public String episode_type;
    public int id;
    public String name;
    public String overview;
    public String production_code;
    public int runtime;
    public int season_number;
    public int show_id;
    public String still_path;
    public double vote_average;
    public int vote_count;
    public ArrayList<Crew> crew;
    public ArrayList<GuestStar> guest_stars;
}

