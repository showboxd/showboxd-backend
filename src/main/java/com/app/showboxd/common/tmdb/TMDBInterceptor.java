package com.app.showboxd.common.tmdb;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TMDBInterceptor implements RequestInterceptor {

    @Autowired
    private TMDBProperties tmdbProperties;

    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header("Authorization", "Bearer " + tmdbProperties.getKey());
    }
}
