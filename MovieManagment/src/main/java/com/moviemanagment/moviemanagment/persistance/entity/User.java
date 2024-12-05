package com.moviemanagment.moviemanagment.persistance.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@JsonProperty(access = JsonProperty.Access.READ_ONLY)// es solo de lectura no me lo pueden envir ya que es autogenerado, no es necesario pero a modo de ejemplo
    private Long id;
    @Column(nullable = false, unique = true)
    private String username;
    private String name;
    @Column(nullable = false)
    private String password;
    @CreationTimestamp//se crea al momento de crear al objeto
   // @JsonProperty(value = "created-at")
    //@JsonFormat(pattern = "yyyy/MM/dd - HH:mm:ss")
    @Column(updatable = false, columnDefinition = "TIMESTAMP DEFAULT NOW()")//se puede ingresar pero no se puede actualizar
    private LocalDateTime createdAt;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user", cascade = {CascadeType.REMOVE})
    //@JsonManagedReference("user-to-ratings")
    private List<Rating> ratings;
    //SAQUE LAS ANOTACIONES DE JACKSON XQ EMPECE A USAR DTO

    //soft delete: no se borra el registro, solo se oculta con un campo llamado satatus
    //private UserStatus status;
    //al soft delete tambi[en se le conoce como borrado logico, ya que consiste en encender u apagar un booleano o una enumeracion
    ///Al hard delete tambien se le conoce como borrado fisico ya que se borra literalmente de la base de datos, eliminando sus apuntadores y todo
}
