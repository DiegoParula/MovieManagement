package com.moviemanagment.moviemanagment.persistance.specification;

import com.moviemanagment.moviemanagment.dto.request.MovieSearchCriteria;
import com.moviemanagment.moviemanagment.persistance.entity.Movie;
import com.moviemanagment.moviemanagment.persistance.entity.Rating;
import com.moviemanagment.moviemanagment.util.MovieGenre;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class FindAllMoviesSpecification implements Specification<Movie> {

    private MovieSearchCriteria searchCriteria;

    public FindAllMoviesSpecification(MovieSearchCriteria searchCriteria) {
        this.searchCriteria = searchCriteria;
    }

    @Override
    public Predicate toPredicate(Root<Movie> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        //root equivale al from movie ""
        //citeriaquery sirve para crear los criterios de la consulta en si mismo
        //citeryiabuilder se utiliza como una fabrica, son los bloques de construccion, permite construir predicados y expresiones

        List<Predicate> predicates = new ArrayList<>();
        if (StringUtils.hasText(this.searchCriteria.title())) {
            //m.title like '%asasas%'
            Predicate tittleLike = criteriaBuilder.like(root.get("title"), "%" + this.searchCriteria.title() + "%");
            predicates.add(tittleLike);
        }
        if (searchCriteria.genre() != null){
            //m.genre = ?
            Predicate genreEqual = criteriaBuilder.equal(root.get("genre"), this.searchCriteria.genre());
            predicates.add(genreEqual);
        }

        if (searchCriteria.minReleaseYear() != null && searchCriteria.minReleaseYear().intValue() > 0){
            Predicate realeaseYearGreaterThanEqual = criteriaBuilder.greaterThanOrEqualTo(root.get("releaseYear"), this.searchCriteria.minReleaseYear());
            predicates.add(realeaseYearGreaterThanEqual);
        }

        if (searchCriteria.maxRealeaseYear() != null && searchCriteria.maxRealeaseYear().intValue() > 0){
            Predicate realeaseYearLessThanEqual = criteriaBuilder.lessThanOrEqualTo(root.get("releaseYear"), this.searchCriteria.maxRealeaseYear());
            predicates.add(realeaseYearLessThanEqual);
        }

        if (searchCriteria.minAverageRating()!= null && searchCriteria.minAverageRating().intValue() > 0){

            Subquery<Double> averageRatingSubquery = getAverageRatingSubquery(root, query, criteriaBuilder);
            Predicate averageRatingGreaterThanEqual = criteriaBuilder.greaterThanOrEqualTo(averageRatingSubquery, this.searchCriteria.minAverageRating().doubleValue());
            predicates.add(averageRatingGreaterThanEqual);
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        //los predicados van a estar de forma dinamica dependiendo de los if de arriba
        //select m.* from movie where 1 = 1 and m.title like '%sasa%' and m.genre = ? and m.releaseYear >= ?

    }

    private static Subquery<Double> getAverageRatingSubquery(Root<Movie> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Subquery<Double> averageRatingSubquery= query.subquery(Double.class);//select avg(rating)
        Root<Rating> ratingRoot = averageRatingSubquery.from(Rating.class);
        averageRatingSubquery.select(criteriaBuilder.avg(ratingRoot.get("rating")));// avg(r1.rating)
        Predicate movieIdEqual = criteriaBuilder.equal(root.get("id"), ratingRoot.get("id"));
        averageRatingSubquery.where(movieIdEqual);
        return averageRatingSubquery;
    }
}
