package com.moviemanagment.moviemanagment.service;

import com.moviemanagment.moviemanagment.dto.request.MovieSearchCriteria;
import com.moviemanagment.moviemanagment.dto.request.SaveMovie;
import com.moviemanagment.moviemanagment.dto.response.GetMovie;
import com.moviemanagment.moviemanagment.dto.response.GetMovieStatistic;
import com.moviemanagment.moviemanagment.dto.response.GetUser;
import com.moviemanagment.moviemanagment.persistance.entity.Movie;
import com.moviemanagment.moviemanagment.persistance.entity.Rating;
import com.moviemanagment.moviemanagment.util.MovieGenre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IMovieService {
    Page<GetMovie> getAllMovies(MovieSearchCriteria movieSearchCriteria, Pageable pageable);
    List<GetMovie> findAll(MovieSearchCriteria searchCriteria);//usando de froma dinamica me ahorro los list e abajo
    //List<GetMovie> findByGenreAndTitleContainingAndRealeaseYearGreaterThanEqual(MovieGenre genre, String title, Integer minReleaseYear);
    //List<GetMovie> findAllByTitle(String title);
    //List<GetMovie> findAllByGenre(MovieGenre genre);
    //List<GetMovie> findAllByGenreAndTitle(MovieGenre genre, String title);
    GetMovie creatOne(SaveMovie movie);
    GetMovie updateOneById(Long id, SaveMovie movie);
    void deleteOneById(Long id);
    GetMovieStatistic findOneById(Long id);

    interface iRatingService {
        List<Rating> findAll();
        List<Rating> findAllByMovieId(Long movieId);
        List<Rating> findAllByUsername(String username);
        Rating findOneById(Long id);
        Rating createOne(Rating rating);
        Rating updateOneById(Long id, Rating rating);
        void deleteOneById(Long id);

    }
}
