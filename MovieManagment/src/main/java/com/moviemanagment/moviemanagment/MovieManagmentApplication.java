package com.moviemanagment.moviemanagment;

import com.moviemanagment.moviemanagment.persistance.repository.IMovieRepository;
import com.moviemanagment.moviemanagment.util.MovieGenre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;


@SpringBootApplication
public class MovieManagmentApplication {

    public static void main(String[] args) {
        SpringApplication.run(MovieManagmentApplication.class, args);
    }

    //para probar el ejemplo del querymethod
//    @Autowired
//    private IMovieRepository movieRepository;
//
//    @Bean
//    public CommandLineRunner testFindAllMovieByGenreOrderBy() {
//        return args -> {
//            System.out.println("Peliculas de DRAMA ordenadas por título");
//            Sort.Direction direction = Sort.Direction.fromString("DESC");
//            Sort sort = Sort.by(direction, "releaseYear").and(Sort.by(Sort.Direction.ASC, "title"));
//
//            Pageable pageable = PageRequest.of(0,10, sort);
//            movieRepository.findAllByGenre(MovieGenre.DRAMA, pageable)
//                    .forEach(System.out::println);
//        };
//    }
}
