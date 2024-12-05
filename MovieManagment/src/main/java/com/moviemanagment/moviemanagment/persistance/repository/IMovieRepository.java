package com.moviemanagment.moviemanagment.persistance.repository;

import com.moviemanagment.moviemanagment.persistance.entity.Movie;
import com.moviemanagment.moviemanagment.util.MovieGenre;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface IMovieRepository extends JpaRepository<Movie, Long>, JpaSpecificationExecutor<Movie> {

    //List<Movie> findByTitle(String title);
    //List<Movie> findByGenre(MovieGenre genre);
    //List<Movie> findByGenreAndTitle(MovieGenre genre, String title);
    //List<Movie> findByGenreAndTitleContainingAndRealeaseYearGreaterThanEqual(MovieGenre genre, String title, Integer minReleaseYear);
    //otra formar de paginar, listar y ordenar usando query methods
    //Page<Movie> findAllByGenreOrderByTitle(MovieGenre genre, Pageable pageable);
    Page<Movie> findAllByGenre(MovieGenre genre, Pageable pageable);

//    @Query("select max(r.rating) from Movie m join m.ratings r where m.id = ?1")
//    int maxRatingById(Long id);
//    @Query("select count(r.rating) from Movie m join m.ratings r where m.id=?1")
//    int countRatingsById(Long id);
}
