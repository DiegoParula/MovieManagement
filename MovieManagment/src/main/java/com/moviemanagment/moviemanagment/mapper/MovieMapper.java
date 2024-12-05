package com.moviemanagment.moviemanagment.mapper;

import com.moviemanagment.moviemanagment.dto.request.SaveMovie;
import com.moviemanagment.moviemanagment.dto.response.GetMovie;
import com.moviemanagment.moviemanagment.dto.response.GetMovieStatistic;
import com.moviemanagment.moviemanagment.persistance.entity.Movie;

import java.util.List;

public class MovieMapper {

    public static GetMovieStatistic toGetMovieStatistic(Movie movie, int totalRatings, double averageRating, int lowestRating, int highestRating) {
        if (movie == null) return null;

        return new GetMovieStatistic(
                movie.getId(),
                movie.getTitle(),
                movie.getDirector(),
                movie.getGenre(),
                movie.getReleaseYear(),
                totalRatings,
                averageRating,
                lowestRating,
                highestRating
        );
    }

//seran estaticos para que no tengamos que estar instanciandolos
    public static GetMovie toGetDto(Movie entity) {
        if (entity == null) return null;

        return new GetMovie(
                entity.getId(),
                entity.getTitle(),
                entity.getDirector(),
                entity.getGenre(),
                entity.getReleaseYear(),
                entity.getRatings() != null ? entity.getRatings().size():0

        );
    }

    public static List<GetMovie> toGetDtoList(List<Movie> entities) {
        if (entities == null) return null;

        return entities.stream()//List<Movie> -> Stream<Movie>
                .map(each -> MovieMapper.toGetDto(each))//Stream<Movie> -> Stream<GetMovie>
                .toList();
    }

    public static Movie toEntity(SaveMovie saveDto) {
        if (saveDto == null) return null;
        Movie movie = new Movie();
        movie.setTitle(saveDto.title());
        movie.setDirector(saveDto.director());
        movie.setGenre(saveDto.genre());
        movie.setReleaseYear(saveDto.releaseYear());
        return movie;
    }

    public static void updateEntity(Movie oldMovie, SaveMovie movieDTO) {
        if (oldMovie == null || movieDTO == null) return;
        oldMovie.setTitle(movieDTO.title());
        oldMovie.setGenre(movieDTO.genre());
        oldMovie.setDirector(movieDTO.director());
        oldMovie.setReleaseYear(movieDTO.releaseYear());
    }
}
