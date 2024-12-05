package com.moviemanagment.moviemanagment.dto.request;

import com.moviemanagment.moviemanagment.util.MovieGenre;

public record MovieSearchCriteria(
        String title,
        MovieGenre genre,
        Integer minReleaseYear,
        Integer maxRealeaseYear,
        Integer minAverageRating
) {
}
