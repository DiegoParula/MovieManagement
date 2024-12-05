package com.moviemanagment.moviemanagment.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public record GetCompleteRatingDto(
        @JsonProperty("rating_id")
        Long ratingId,
        @JsonProperty("movie_id")
        long movieId,
        @JsonProperty("movie_title")
        String movieTitle,
        String username,
        int rating
) implements Serializable {

}
