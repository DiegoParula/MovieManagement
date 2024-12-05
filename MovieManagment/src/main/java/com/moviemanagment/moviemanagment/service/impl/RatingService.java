package com.moviemanagment.moviemanagment.service.impl;

import com.moviemanagment.moviemanagment.dto.request.SaveRatingDto;
import com.moviemanagment.moviemanagment.dto.request.SaveUser;
import com.moviemanagment.moviemanagment.dto.response.GetCompleteRatingDto;
import com.moviemanagment.moviemanagment.dto.response.GetMovie;
import com.moviemanagment.moviemanagment.dto.response.GetUser;
import com.moviemanagment.moviemanagment.exception.DuplicatedRatingException;
import com.moviemanagment.moviemanagment.mapper.RatingMapper;
import com.moviemanagment.moviemanagment.persistance.entity.Rating;
import com.moviemanagment.moviemanagment.persistance.entity.User;
import com.moviemanagment.moviemanagment.persistance.repository.IRatingRepository;
import com.moviemanagment.moviemanagment.persistance.repository.IUserRespository;
import com.moviemanagment.moviemanagment.service.IRatingService;
import com.moviemanagment.moviemanagment.service.IUserService;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RatingService implements IRatingService {

    @Autowired
    private IRatingRepository ratingRepository;

    @Autowired
    private IUserService iUserService;

    @Autowired
    private EntityManager entityManager;// lo uso para desvoncular la entidad del contexto en el que se encuentra as[i luego puedo realizar el select

    @Transactional(readOnly = true)//porque no es necesario hacer rollback
    @Override
    public Page<GetCompleteRatingDto> findAll(Pageable pageable) {

        return ratingRepository.findAll(pageable).map(RatingMapper::toGetCompleteRatingDto);
    }

    @Transactional(readOnly = true)//porque no es necesario hacer rollback
    @Override
    public Page<GetMovie.GetRating> findAllByMovieId(Long movieId, Pageable pageable) {
        return ratingRepository.findByMovieId(movieId, pageable).map(RatingMapper::toGetMovieRatingDto);
    }

    @Transactional(readOnly = true)//porque no es necesario hacer rollback
    @Override
    public Page<GetUser.GetRating> findAllByUsername(String username, Pageable pageable) {
        return ratingRepository.findByUsername(username, pageable).map(RatingMapper::toGetUserRatingDto);
    }

    @Transactional(readOnly = true)//porque no es necesario hacer rollback
    @Override
    public GetCompleteRatingDto findOneById(Long id) {

        return RatingMapper.toGetCompleteRatingDto(this.findOneEntityById(id));
    }

    @Override
    public GetCompleteRatingDto createOne(SaveRatingDto rating) {
        //Opcion 1
        boolean ratingExists = ratingRepository.existsByMovieIdAndUserUsername(rating.movieId(), rating.username());


        if(ratingExists) {
            throw new DuplicatedRatingException(rating.username(), rating.movieId());
        }
          //Opcion 2 si existe lo actauliza, me gusta mas el uno porque indica el conflicto
//        Long ratingId = ratingRepository.getRatingIdByMovieIdAndUsername(rating.movieId(), rating.username());
//        if(ratingId != null && ratingId.longValue() > 0) {
//            return this.updateOneById(ratingId, rating);
//        }


        User userEntity = iUserService.findOneEntityByUsername(rating.username());
        Rating entity = RatingMapper.toEntity(rating, userEntity.getId());
        Rating savedRating = ratingRepository.save(entity);
        // lo uso para desvincular la entidad del contexto en el que se encuentra asi luego puedo realizar el select
        entityManager.detach(entity);//detach significa desatar de un contexto, xq si no devolvia null user y movie
        return ratingRepository.findById(entity.getId())
                .map(RatingMapper::toGetCompleteRatingDto)
                .get();

    }



    private Rating findOneEntityById(Long id) {
        return ratingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("[rating" + Long.toString(id)+"]"));
    }

    @Override
    public GetCompleteRatingDto updateOneById(Long id, SaveRatingDto rating) {
        Rating oldRating = this.findOneEntityById(id);
        User userEntity = iUserService.findOneEntityByUsername(rating.username());
        RatingMapper.updateEntity(oldRating, rating, userEntity.getId());
        ratingRepository.save(oldRating);
        Rating updatedRating = this.findOneEntityById(id);


        return RatingMapper.toGetCompleteRatingDto(updatedRating) ;
    }

    @Override
    public void deleteOneById(Long id) {

        //1er opcion es mas eficiente que la 2da ya que el exists obtiene una sola columna
        // y el findbyid obtiene todas las columnas
        if(ratingRepository.existsById(id)){
            ratingRepository.deleteById(id);
            return;
        }
        throw new RuntimeException("[rating" + Long.toString(id)+"] not found");

        /*2da opcion
        Rating rating = this.findOneById(id);
        ratingRepository.delete(rating);
        * */
    }
}
