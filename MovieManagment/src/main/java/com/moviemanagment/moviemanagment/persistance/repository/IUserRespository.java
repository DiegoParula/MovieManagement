package com.moviemanagment.moviemanagment.persistance.repository;

import com.moviemanagment.moviemanagment.persistance.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface IUserRespository extends JpaRepository<User, Long> {
    Page<User> findByName(String name, Pageable pageable);
    Optional<User> findByUsername(String username);
    @Modifying//indica a JPA que la consulta puede tener efectos secundarios en el estado persistente
    //de la base de datos(es decir, son SQL como DELETE, INSERT o UODATE) y se debe ejecutar en un contexto transaccional
    //@Transactional
    int deleteByUsername(String username);//int asi devuelve la cantidad de resgitros que elimino de la bd

}
