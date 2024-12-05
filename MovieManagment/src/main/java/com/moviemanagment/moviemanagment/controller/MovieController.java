package com.moviemanagment.moviemanagment.controller;

import com.moviemanagment.moviemanagment.dto.request.MovieSearchCriteria;
import com.moviemanagment.moviemanagment.dto.request.SaveMovie;
import com.moviemanagment.moviemanagment.dto.response.ApiError;
import com.moviemanagment.moviemanagment.dto.response.GetMovie;
import com.moviemanagment.moviemanagment.dto.response.GetMovieStatistic;
import com.moviemanagment.moviemanagment.exception.InvalidPasswordException;
import com.moviemanagment.moviemanagment.exception.ObjectNotFoundException;
import com.moviemanagment.moviemanagment.persistance.entity.Movie;
import com.moviemanagment.moviemanagment.service.impl.MovieService;
import com.moviemanagment.moviemanagment.service.impl.RatingService;
import com.moviemanagment.moviemanagment.service.impl.UserService;
import com.moviemanagment.moviemanagment.util.MovieGenre;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/movies")
public class MovieController {
  @Autowired
  private MovieService movieService;
  @Autowired
  private RatingService ratingService;



  /*
  @GetMapping
  public List<Movie> findAll(){
      return movieService.findAll();
  }*/

    //filtro por genero, por drama o por genere y drama o listo todo
  @GetMapping
  public ResponseEntity<List<GetMovie>> findAll(@RequestParam(required = false, name="title") String title,
                                @RequestParam(required = false, name = "genre") MovieGenre genre,
                                @RequestParam(required=false, name = "min_release_year") Integer minRealeaseYear,
                                                @RequestParam(required=false, name = "max_release_year") Integer maxRealeaseYear,
                                                @RequestParam(required=false, name = "min_average_rating") Integer minAverageRating){
//me ahorro de hacer todos los if la consulta dinamica usando las specification con los predicados, lo mismo para ahorrarme varios endpoint para cada peticion
      //List<GetMovie> movies = null;
/*
      if (StringUtils.hasText(title) && genre !=null) {
          movies = movieService.findAllByGenreAndTitle(genre,title);
      }else if(StringUtils.hasText(title)){
          movies = movieService.findAllByTitle(title);
      }else if (genre != null){
          movies = movieService.findAllByGenre(genre);
      }else {
          movies = movieService.findAll();
      }*/
      //Opcion 1
      //return new ResponseEntity<>(movies, HttpStatus.OK);
      //Opcion 2
      //return ResponseEntity.status(HttpStatus.OK).body(movies);
      //Opcion 3
      MovieSearchCriteria searchCriteria = new MovieSearchCriteria(title, genre, minRealeaseYear, maxRealeaseYear, minAverageRating);
      List<GetMovie> movies = movieService.findAll(searchCriteria);
      return ResponseEntity.ok(movies);
  }
    //pageable
    @GetMapping(params = "page")// Mapea solo cuando  'page' query parameter esta presente
    public ResponseEntity<Page<GetMovie>> getAllMovies(@RequestParam(required = false, name="title") String title,
                                                  @RequestParam(required = false, name = "genre") MovieGenre genre,
                                                  @RequestParam(required=false, name = "min_release_year") Integer minRealeaseYear,
                                                  @RequestParam(required=false, name = "max_release_year") Integer maxRealeaseYear,
                                                  @RequestParam(required=false, name = "min_average_rating") Integer minAverageRating,
                                                  Pageable pageable
                                                       //puedo pasar los requestparam de pageable por separado
//                                                       @RequestParam(required=false, defaultValue = "0") Integer pageNumber,
//                                                  @RequestParam(required = false, defaultValue = "10") Integer pageSize,
//                                                    @RequestParam(required = false, defaultValue = "id") String sortBy
//
                                                    ){
      //tengo q crear para pasar el pageable de los parametros que le pase por separado
//        Sort movieSort = Sort.by(sortBy.trim());
//        Pageable moviepageable = PageRequest.of(pageNumber, pageSize, movieSort);
        MovieSearchCriteria searchCriteria = new MovieSearchCriteria(title, genre, minRealeaseYear, maxRealeaseYear, minAverageRating);
        Page<GetMovie> movies = movieService.getAllMovies(searchCriteria, pageable);
        return ResponseEntity.ok(movies);
    }
  /*
  @GetMapping("/{title}")
    public ResponseEntity<List<GetMovie>> findOneByTitle(@PathVariable String title){
      //try {
         return ResponseEntity.ok(movieService.findAllByTitle(title));
     // }catch (ObjectNotFoundException ex){
     //     return ResponseEntity.notFound().build();
     // }

  }*/

  @GetMapping("/{id}")
    public ResponseEntity<GetMovieStatistic> finOneById(@PathVariable Long id){

      //try {
          return ResponseEntity.ok(movieService.findOneById(id));
     // }catch(ObjectNotFoundException ex){
      //    return ResponseEntity.notFound().build();
     //}

  }

  @GetMapping(value = "/{id}/ratings")
  public ResponseEntity<Page<GetMovie.GetRating>> finAllRatingsForMovieById(@PathVariable Long id, Pageable pageable){
      return ResponseEntity.ok(ratingService.findAllByMovieId(id, pageable));
  }

  @PostMapping()
    public ResponseEntity<GetMovie> createOne(@Valid @RequestBody SaveMovie movieDto){
      return ResponseEntity.status(HttpStatus.CREATED).body(movieService.creatOne(movieDto));
  }

  @PutMapping("/{id}")
    public ResponseEntity<GetMovie> updateOne(@PathVariable Long id, @Valid @RequestBody SaveMovie movieDto){

      //try {
          GetMovie updateMovie = movieService.updateOneById(id, movieDto);
          return ResponseEntity.ok(updateMovie);

      //}catch(ObjectNotFoundException e){
     //     return ResponseEntity.notFound().build();
    //  }
      /*
      if (movieService.findOneById(movieDto.getId()) == null) {
          return ResponseEntity.notFound().build();
      }else {
          return ResponseEntity.ok(movieService.updateOneById(id, movieDto));
      }*/
  }

  @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOne(@PathVariable Long id){
      //try{
          movieService.deleteOneById(id);
          return ResponseEntity.noContent().build();//noContent devuelve 204 operacion exitosa
      //}catch(ObjectNotFoundException e){
     //     return ResponseEntity.notFound().build();
     // }
  }




/* otra forma del findall llamandolos dependiendo los params y haciendo un endpoint para cada caso
    @GetMapping(params = {"title", "genre"})
    public List<Movie> findAllByTitleAndGenre(@RequestParam String title,
                               @RequestParam MovieGenre genre){

        return movieService.findAllByGenreAndTitle(genre,title);
    }

    @GetMapping(params = {"title"})
    public List<Movie> findAllByTitle(@RequestParam String title){

        return movieService.findAllByTitle(title);
    }

    @GetMapping(params = {"genre"})
    public List<Movie> findAllByTitleAndGenre(@RequestParam MovieGenre genre){

        return movieService.findAllByGenre(genre);
    }
    @GetMapping(params = {"!title", "!genre"})
    public List<Movie> findAllB(){

        return movieService.findAll();
    }*/



}
