package com.moviemanagment.moviemanagment.mapper;

import com.moviemanagment.moviemanagment.dto.request.SaveRatingDto;
import com.moviemanagment.moviemanagment.dto.response.GetCompleteRatingDto;
import com.moviemanagment.moviemanagment.dto.response.GetMovie;
import com.moviemanagment.moviemanagment.dto.response.GetUser;
import com.moviemanagment.moviemanagment.persistance.entity.Rating;

import java.util.List;

public class RatingMapper {

    public static GetCompleteRatingDto toGetCompleteRatingDto(Rating rating) {
        if (rating == null) return null;
        String movieTitle = rating.getMovie() != null ? rating.getMovie().getTitle() : null;
        String username = rating.getUser() != null ? rating.getUser().getUsername() : null;
        return new GetCompleteRatingDto(
                rating.getId(),
                rating.getMovieId(),
                movieTitle,
                username,
                rating.getRating()
        );
    }

    public static GetMovie.GetRating toGetMovieRatingDto(Rating entity){
        if (entity == null) return null;

        String username = entity.getUser() != null ? entity.getUser().getUsername() : null;

        return new GetMovie.GetRating(
                entity.getId(),
                entity.getRating(),
                username
        );

    }

    public static GetUser.GetRating toGetUserRatingDto(Rating entity){
        if (entity == null) return null;

        String movieTitle = entity.getMovie() != null ? entity.getMovie().getTitle() : null;

        return new GetUser.GetRating(
                entity.getId(),
                movieTitle,
                entity.getMovieId(),
                entity.getRating()
        );
    }

    public static List<GetMovie.GetRating> toGetMovieRatingDtoList(List<Rating> entityList){
        if (entityList == null) return null;
        return entityList.stream()
                .map(RatingMapper::toGetMovieRatingDto)
                .toList();
    }

    public static List<GetUser.GetRating> toGetUserRatingDtoList(List<Rating> entityList){
        if (entityList == null) return null;
        return entityList.stream()
                .map(RatingMapper::toGetUserRatingDto)
                .toList();
    }

    public static Rating toEntity(SaveRatingDto rating, Long userId) {
        if (rating == null) return null;

        Rating entity = new Rating();
        entity.setMovieId(rating.movieId());
        entity.setUserId(userId);
        entity.setRating(rating.rating());
        return entity;
    }

    public static void updateEntity(Rating entity, SaveRatingDto dto, Long userId) {
        if (dto == null || entity == null) return;

        entity.setRating(dto.rating());
        entity.setMovieId(dto.movieId());
        entity.setUserId(userId);
    }
}
