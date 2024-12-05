package com.moviemanagment.moviemanagment.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.moviemanagment.moviemanagment.util.MovieGenre;

import java.io.Serializable;

public record GetMovieStatistic(
        Long id,
        String title,
        String director,
        MovieGenre genre,
        @JsonProperty("total_ratings")
        int totalRatings,
        @JsonProperty("release_year")
        int releaseYear,
        @JsonProperty("average_ratings")
        Double averageRatings,
        @JsonProperty("lowest_year")
        int lowestRating,
        @JsonProperty("highest_year")
        int highestRating
)implements Serializable {
        @Override
        public Double averageRatings() {
                return Double.parseDouble(String.format("%1.2f", averageRatings));
        }
}
