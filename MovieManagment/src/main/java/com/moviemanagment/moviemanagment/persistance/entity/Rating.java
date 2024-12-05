package com.moviemanagment.moviemanagment.persistance.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Check;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"movie_id", "user_id"}))//para que no califique lo mismo y solo permita una calificacion por usuario y por pelicula
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@JsonProperty(access = JsonProperty.Access.READ_ONLY)// es solo de lectura no me lo pueden envir ya que es autogenerado
    private Long id;
    //repito los id para que las consultas no sean tan pesada
    @Column(name = "movie_id", nullable = false)
    private Long movieId;
    @Column(name = "user_id", nullable = false)
    private Long userId;
    @ManyToOne
    @JoinColumn(name = "movie_id", insertable = false, updatable = false)//insertable para que no los tome en cuenta al crear y solo sean para obtener info
    //@JsonBackReference("movie-to-ratings")
    private Movie movie;
    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    //@JsonBackReference("user-to-ratings")//es la parte secundaria de la relacion, no ser[a serializada para no generar ciclos infinitos
    private User user;
    @Column(nullable = false)
    @Check(constraints = "rating >=0 and rating <= 5")
    private int rating;
    @CreationTimestamp//se crea al momento de crear al objeto
    @JsonProperty(value = "created-at")
    @JsonFormat(pattern = "yyyy/MM/dd - HH:mm:ss")
    @Column(updatable = false, columnDefinition = "TIMESTAMP DEFAULT NOW()")//se puede ingresar pero no se puede actualizar
    private LocalDateTime createdAt;

    //SAQUE LAS ANOTACIONES DE JACKSON XQ EMPECE A USAR DTO

}
