package com.moviemanagment.moviemanagment.exception;

public class DuplicatedRatingException extends RuntimeException {
    private String username;
    private Long movieId;

    public DuplicatedRatingException(String username, Long movieId) {
        this.username = username;
        this.movieId = movieId;
    }

    @Override
    public String getMessage() {
        return String.format("Rating already submited fo movie with ID %d by user %s. Only one rating per user movie is allowed", this.movieId, this.username);
    }
}
