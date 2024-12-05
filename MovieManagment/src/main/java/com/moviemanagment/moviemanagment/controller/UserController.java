package com.moviemanagment.moviemanagment.controller;

import com.moviemanagment.moviemanagment.dto.request.SaveUser;
import com.moviemanagment.moviemanagment.dto.response.GetMovieStatistic;
import com.moviemanagment.moviemanagment.dto.response.GetUser;
import com.moviemanagment.moviemanagment.dto.response.GetUserStatistic;
import com.moviemanagment.moviemanagment.exception.ObjectNotFoundException;
import com.moviemanagment.moviemanagment.persistance.entity.User;
import com.moviemanagment.moviemanagment.service.impl.RatingService;
import com.moviemanagment.moviemanagment.service.impl.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private RatingService ratingService;

    @GetMapping
    public ResponseEntity<Page<GetUser>> findAll(@RequestParam(required = false) String name, Pageable pageable){

        Page<GetUser> users = null;
        if( StringUtils.hasText(name)){
            users = userService.findAllByName(name, pageable);

        }else {
            users = userService.findAll(pageable);
        }


        return ResponseEntity.ok(users);
    }
    @GetMapping("/{username}")
    public ResponseEntity<GetUserStatistic> findOneByUsername(@PathVariable String username){
        //try {
            return ResponseEntity.ok(userService.findOneByUsername(username));
        //}catch (ObjectNotFoundException e){
        //    return ResponseEntity.notFound().build();
        //}

        
    }

    @GetMapping("/{username}/ratings")
    public ResponseEntity<Page<GetUser.GetRating>> findRatingsForUserByUsername(@PathVariable String username, Pageable pageable){
        return ResponseEntity.ok(ratingService.findAllByUsername(username, pageable));
    }

    @PostMapping()
    public ResponseEntity<GetUser> save(@Valid @RequestBody SaveUser userDto){
        GetUser newUser = userService.saveOneUser(userDto);
        return ResponseEntity.ok(newUser);
    }
    /*
    @PutMapping("/{id}")
    public ResponseEntity<GetUser> update(@PathVariable Long id, @Valid @RequestBody SaveUser userDto){
        try{
            GetUser newUser = userService.saveOneUser(userDto);
            return ResponseEntity.ok(newUser);
        }catch (ObjectNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }*/

    @PutMapping("/{username}")
    public ResponseEntity<GetUser> updateByUsername(@PathVariable String username, @Valid @RequestBody SaveUser userDto){
        //try {
            GetUser newUser = userService.updateOneByUsername(username, userDto);
            return ResponseEntity.ok(newUser);
        //}catch (ObjectNotFoundException e){
        //    return ResponseEntity.notFound().build();
       // }
    }
    //tengo que implementar manejo de transacciones porque este delete no esta definido en JPA
    @DeleteMapping("/{username}")
    public ResponseEntity<Void> delete(@PathVariable String username){
        //try {
            userService.deleteOneByUsername(username);
            return ResponseEntity.noContent().build();
      //  }catch (ObjectNotFoundException e){
       //    return ResponseEntity.notFound().build();
       // }
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAll(){
        userService.deleteAll();
        return ResponseEntity.noContent().build();
    }

}
