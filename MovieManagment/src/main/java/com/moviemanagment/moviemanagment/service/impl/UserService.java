package com.moviemanagment.moviemanagment.service.impl;

import com.moviemanagment.moviemanagment.dto.request.SaveUser;
import com.moviemanagment.moviemanagment.dto.response.GetMovieStatistic;
import com.moviemanagment.moviemanagment.dto.response.GetUser;
import com.moviemanagment.moviemanagment.dto.response.GetUserStatistic;
import com.moviemanagment.moviemanagment.exception.ObjectNotFoundException;
import com.moviemanagment.moviemanagment.mapper.UserMapper;
import com.moviemanagment.moviemanagment.persistance.entity.User;
import com.moviemanagment.moviemanagment.persistance.repository.IRatingRepository;
import com.moviemanagment.moviemanagment.persistance.repository.IUserRespository;
import com.moviemanagment.moviemanagment.service.IUserService;
import com.moviemanagment.moviemanagment.service.Validator.PasswordValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional//lo pongo a nivel de clase
public class UserService implements IUserService {
    @Autowired
    private IUserRespository userRespository;
    @Autowired
    private IRatingRepository ratingRepository;
    @Transactional(readOnly = true)//porque no es necesario hacer rollback
    @Override
    public Page<GetUser> findAll(Pageable pageable) {
        Page<User> entities = userRespository.findAll(pageable);
        return entities.map(UserMapper::toGetDto);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<GetUser> findAllByName(String name, Pageable pageable) {
        Page<User> entities = userRespository.findByName(name, pageable);
        return entities.map(UserMapper::toGetDto);
    }
    @Transactional(readOnly = true)
    @Override
    public GetUserStatistic findOneByUsername(String username) {

        int totalRatings = ratingRepository.countByUserUsername(username);
        double averageRating = ratingRepository.avergadeRatingByUsername(username);
        int lowestRating= ratingRepository.minRatingByUserUsername(username);
        int highestRating=ratingRepository.maxRatingByUserUsername(username);

        return UserMapper.toGetStatisticDto(



                this.findOneEntityByUsername(username),
                totalRatings,
                averageRating,
                lowestRating,
                highestRating
                );

    }

    @Override
    public User findOneEntityByUsername(String username) {
       return userRespository.findByUsername(username)//lo tengo que desempaquetar por el true saco el optional el valor que contiene
               //Mapeo de Excepciones a Códigos HTTP con ResponseStatusException
               //.orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(404), "User not found: " + username));
            .orElseThrow(()-> new ObjectNotFoundException("User not found: " + username));

    }


    @Override
    public GetUser saveOneUser(SaveUser userDto) {
        PasswordValidator.validatePassword(userDto.password(), userDto.passwordRepeated());
        User newUser = UserMapper.toEntity(userDto);
        return UserMapper.toGetDto(userRespository.save(newUser));
    }

    @Override
    public GetUser updateOneByUsername(String username, SaveUser userDto) {
        PasswordValidator.validatePassword(userDto.password(), userDto.passwordRepeated());
        User oldUser = this.findOneEntityByUsername(username);
        UserMapper.updateEntity(oldUser, userDto);



        return UserMapper.toGetDto(userRespository.save(oldUser));
    }

    @Override
    public void deleteOneByUsername(String username) {
        /*User user = this.findOneByUsername(username);
        userRespository.delete(user);*/

        if(userRespository.deleteByUsername(username) != 1){
            throw new ObjectNotFoundException("User not found: " + username);
        }
    }

    //creo este metodo para probar a modo de ejemplo el funcionamento de rollback con Transactional
    @Override
    public void deleteAll() {
        userRespository.deleteAll();
        throw new RuntimeException();
    }
}
