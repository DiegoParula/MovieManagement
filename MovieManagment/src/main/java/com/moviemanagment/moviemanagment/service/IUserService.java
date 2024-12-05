package com.moviemanagment.moviemanagment.service;

import com.moviemanagment.moviemanagment.dto.request.SaveUser;
import com.moviemanagment.moviemanagment.dto.response.GetUser;
import com.moviemanagment.moviemanagment.dto.response.GetUserStatistic;
import com.moviemanagment.moviemanagment.persistance.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IUserService {

    Page<GetUser> findAll(Pageable pageable);
    Page<GetUser> findAllByName(String name, Pageable pageable);
    GetUserStatistic findOneByUsername(String username);
    User findOneEntityByUsername(String username);
    GetUser saveOneUser(SaveUser saveDto);
    GetUser updateOneByUsername(String username, SaveUser saveDto);
    void deleteOneByUsername(String username);
    void deleteAll();
}
