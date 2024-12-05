package com.moviemanagment.moviemanagment.controller;

import com.moviemanagment.moviemanagment.dto.request.SaveRatingDto;
import com.moviemanagment.moviemanagment.dto.response.GetCompleteRatingDto;
import com.moviemanagment.moviemanagment.service.impl.RatingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ratings")
public class RatingController {

    @Autowired
    private RatingService ratingService;

    @GetMapping
    public ResponseEntity<Page<GetCompleteRatingDto>> finAll(Pageable pageable){
        Page<GetCompleteRatingDto> ratings = ratingService.findAll(pageable);
        return ResponseEntity.ok(ratings);
    }

    @GetMapping("/{ratingId}")
    public ResponseEntity<GetCompleteRatingDto> findOneById(@PathVariable Long ratingId){
        return ResponseEntity.ok(ratingService.findOneById(ratingId));
    }

    @PostMapping
    public ResponseEntity<GetCompleteRatingDto> createOne(@RequestBody @Valid SaveRatingDto saveRatingDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(ratingService.createOne(saveRatingDto));
    }

    @PutMapping("/{ratingId}")
    public ResponseEntity<GetCompleteRatingDto> updateOne(@PathVariable Long ratingId,  @RequestBody @Valid SaveRatingDto saveRatingDto){
        return ResponseEntity.ok(ratingService.updateOneById(ratingId, saveRatingDto));
    }

    @DeleteMapping("/{ratingId}")
    public ResponseEntity<Void> deleteOneById(@PathVariable Long ratingId){
       ratingService.deleteOneById(ratingId);
       return ResponseEntity.noContent().build();
    }
}
