package com.moviemanagment.moviemanagment.service;

import com.moviemanagment.moviemanagment.dto.request.SaveRatingDto;
import com.moviemanagment.moviemanagment.dto.request.SaveUser;
import com.moviemanagment.moviemanagment.dto.response.GetCompleteRatingDto;
import com.moviemanagment.moviemanagment.dto.response.GetMovie;
import com.moviemanagment.moviemanagment.dto.response.GetUser;
import com.moviemanagment.moviemanagment.persistance.entity.Rating;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IRatingService {
    Page<GetCompleteRatingDto> findAll(Pageable pageable);
    Page<GetMovie.GetRating> findAllByMovieId(Long movieId, Pageable pageable);
    Page<GetUser.GetRating> findAllByUsername(String username, Pageable pageable);
    GetCompleteRatingDto findOneById(Long id);
    GetCompleteRatingDto createOne(SaveRatingDto ratingDto);
    GetCompleteRatingDto updateOneById(Long id, SaveRatingDto ratingDto);
    void deleteOneById(Long id);
}
