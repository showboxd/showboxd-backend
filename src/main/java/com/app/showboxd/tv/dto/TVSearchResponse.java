package com.app.showboxd.tv.dto;

import lombok.Data;
import java.util.List;

@Data
public class TVSearchResponse {

    private int page;
    private List<TVShow> results;
    private int totalPages;
    private int totalResults;
}
