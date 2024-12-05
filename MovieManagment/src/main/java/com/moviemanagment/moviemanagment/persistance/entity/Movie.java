package com.moviemanagment.moviemanagment.persistance.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.moviemanagment.moviemanagment.dto.request.SaveMovie;
import com.moviemanagment.moviemanagment.util.MovieGenre;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Movie {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@JsonProperty(access = JsonProperty.Access.READ_ONLY)// es solo de lectura no me lo pueden envir ya que es autogenerado
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String director;
    //@JsonProperty(value = "release-year")//dependiendo de los requerimientos nomencaltura de como lo ver representado al atributo
    private int releaseYear;

    @CreationTimestamp//se crea al momento de crear al objeto
    //@JsonProperty(value = "created-at")
    //@JsonFormat(pattern = "yyyy/MM/dd - HH:mm:ss")
    @Column(updatable = false, columnDefinition = "TIMESTAMP DEFAULT NOW()")//se puede ingresar pero no se puede actualizar
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)//para que guarde el valor o el nombre
    private MovieGenre genre;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "movie", cascade = {CascadeType.REMOVE})
    //@JsonManagedReference("movie-to-ratings")//indica q esta propieda es la parte administrada de la relacion, se va serializar con normalidad
    private List<Rating> ratings;
    //SAQUE LAS ANOTACIONES DE JACKSON XQ EMPECE A USAR DTO


    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", director='" + director + '\'' +
                ", releaseYear=" + releaseYear +

                ", genre=" + genre +

                '}';
    }
}
