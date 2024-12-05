package com.moviemanagment.moviemanagment.service.impl;

import com.moviemanagment.moviemanagment.dto.request.MovieSearchCriteria;
import com.moviemanagment.moviemanagment.dto.request.SaveMovie;
import com.moviemanagment.moviemanagment.dto.response.GetMovie;
import com.moviemanagment.moviemanagment.dto.response.GetMovieStatistic;
import com.moviemanagment.moviemanagment.exception.ObjectNotFoundException;
import com.moviemanagment.moviemanagment.mapper.MovieMapper;
import com.moviemanagment.moviemanagment.persistance.entity.Movie;
import com.moviemanagment.moviemanagment.persistance.repository.IMovieRepository;
import com.moviemanagment.moviemanagment.persistance.repository.IRatingRepository;
import com.moviemanagment.moviemanagment.persistance.specification.FindAllMoviesSpecification;
import com.moviemanagment.moviemanagment.service.IMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MovieService implements IMovieService {
    @Autowired
    private IMovieRepository movieRepository;

    @Autowired
    private IRatingRepository ratingRepository;

//    @Transactional(readOnly = true)//porque no es necesario hacer rollback
//    @Override
//    public List<GetMovie> findAll() {
//        List<Movie> entities = movieRepository.findAll();
//        return MovieMapper.toGetDtoList(entities);
//    }
//    @Transactional(readOnly = true)//porque no es necesario hacer rollback
//    @Override
//    public List<GetMovie> findAllByTitle(String title) {
//        List<Movie> entities = movieRepository.findByTitle(title);
//        return MovieMapper.toGetDtoList(entities);
//    }
//    @Transactional(readOnly = true)//porque no es necesario hacer rollback
//    @Override
//    public List<GetMovie> findAllByGenre(MovieGenre genre) {
//        List<Movie> entities = movieRepository.findByGenre(genre);
//        return MovieMapper.toGetDtoList(entities);
//    }
//
//    @Transactional(readOnly = true)//porque no es necesario hacer rollback
//    @Override
//    public List<GetMovie> findAllByGenreAndTitle(MovieGenre genre, String title) {
//        List<Movie> entities = movieRepository.findByGenreAndTitle(genre, title);
//        return MovieMapper.toGetDtoList(entities);
//    }

    @Override
    public Page<GetMovie> getAllMovies(MovieSearchCriteria movieSearchCriteria, Pageable pageable) {
        FindAllMoviesSpecification moviesSpecification = new FindAllMoviesSpecification(movieSearchCriteria);
        Page<Movie> entities = movieRepository.findAll(moviesSpecification, pageable);

        return entities.map(MovieMapper::toGetDto);
    }

    @Override
    public List<GetMovie> findAll(MovieSearchCriteria searchCriteria) {
        FindAllMoviesSpecification moviesSpecification = new FindAllMoviesSpecification(searchCriteria);
        List<Movie> entities = movieRepository.findAll(moviesSpecification);

        return MovieMapper.toGetDtoList(entities);

    }

//    @Override
//    public List<GetMovie> findByGenreAndTitleContainingAndRealeaseYearGreaterThanEqual(MovieGenre genre, String title, Integer minReleaseYear) {
//        List<Movie> entities = movieRepository.findByGenreAndTitleContainingAndRealeaseYearGreaterThanEqual(genre, title, minReleaseYear);
//        return MovieMapper.toGetDtoList(entities);
//    }

    @Override
    public GetMovie creatOne(SaveMovie movieDto) {
        Movie newMovie = MovieMapper.toEntity(movieDto);
        return MovieMapper.toGetDto(
                movieRepository.save(newMovie)
        );
    }

    @Override
    public GetMovie updateOneById(Long id, SaveMovie movieDTO) {
        Movie oldMovie = this.findOneEntityById(id);
        MovieMapper.updateEntity(oldMovie, movieDTO);

        return MovieMapper.toGetDto(
                movieRepository.save(oldMovie)
        );
    }

    @Override
    public void deleteOneById(Long id) {
        Movie movie = this.findOneEntityById(id);
        movieRepository.delete(movie);

    }

    @Override
    public GetMovieStatistic findOneById(Long id) {
        int totalRatings = ratingRepository.countByMovieId(id);
        double averageRating = ratingRepository.avgRatingByMovieId(id);
        int lowestRating = ratingRepository.minRatingByMovieId(id);
        int highestRating = ratingRepository.maxRatingByMovieId(id);
        return MovieMapper.toGetMovieStatistic(
                this.findOneEntityById(id),
                totalRatings,
                averageRating,
                lowestRating,
                highestRating
        );


    }





    //@Transactional(readOnly = true)
    private Movie findOneEntityById(Long id) {
        return movieRepository.findById(id)
                //Mapeo de Excepciones a Códigos HTTP con ResponseStatusException
                //.orElseThrow(()-> new ResponseStatusException(HttpStatusCode.valueOf(404), "Product not found: " + id));
            .orElseThrow(()-> new ObjectNotFoundException("[movie:"+ Long.toString(id)+"]"));
    }
}
