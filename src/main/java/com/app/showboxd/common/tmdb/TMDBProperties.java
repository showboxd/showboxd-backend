package com.app.showboxd.common.tmdb;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Component
@ConfigurationProperties(prefix="tmdb.api")
public class TMDBProperties {

    String key;
    String baseUrl;
    String imageBaseUrl;
}
