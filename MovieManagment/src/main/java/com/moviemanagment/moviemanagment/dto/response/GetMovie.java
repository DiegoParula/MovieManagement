package com.moviemanagment.moviemanagment.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.moviemanagment.moviemanagment.util.MovieGenre;

import java.io.Serializable;
import java.util.List;

public record GetMovie(
        Long id,
        String title,
        String director,
        MovieGenre genre,
        @JsonProperty(value = "release_year") int releaseYear,
        //List<GetRating> ratings
        @JsonProperty("total_ratings")
        int totalRatings


) implements Serializable {
    public static record GetRating(
            long id,
            int rating,
            String username
    )implements Serializable {}

}
