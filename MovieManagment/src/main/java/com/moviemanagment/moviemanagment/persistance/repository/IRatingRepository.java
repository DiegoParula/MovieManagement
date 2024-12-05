package com.moviemanagment.moviemanagment.persistance.repository;

import com.moviemanagment.moviemanagment.dto.response.GetCompleteRatingDto;
import com.moviemanagment.moviemanagment.persistance.entity.Rating;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IRatingRepository extends JpaRepository<Rating, Long> {

    Page<Rating> findByMovieId(Long movieId, Pageable pageable);
    Page<Rating> findByUserUsername(String username, Pageable pageable);
    //Otra forma usando Query
    @Query("SELECT r FROM Rating r JOIN r.user u WHERE u.username = ?1")
    Page<Rating> findByUsername(String username, Pageable pageable);


    boolean existsByMovieIdAndUserUsername(Long movieId, String username);
    @Query("select r.id from Rating r where r.movieId = ?1 and  r.user.username = ?2")
    Long getRatingIdByMovieIdAndUsername(Long movieId, String username);
    @Query("select avg(r.rating) from Rating r where r.movieId = ?1")
    double avgRatingByMovieId(Long id);
    @Query("select min(r.rating) from Rating r where r.movieId = ?1")
    int minRatingByMovieId(Long id);


    int countByMovieId(Long id);
    @Query("select max(r.rating) from Rating r where r.movieId = ?1")
    int maxRatingByMovieId(Long id);

    int countByUserUsername(String username);
    @Query("select avg(r.rating) from Rating r join r.user u where u.username = ?1")
    double avergadeRatingByUsername(String username);
    @Query("select min(r.rating) from Rating r join r.user u where u.username = ?1")
    int minRatingByUserUsername(String username);
    @Query("select max(r.rating) from Rating r join r.user u where u.username = ?1")
    int maxRatingByUserUsername(String username);
}
