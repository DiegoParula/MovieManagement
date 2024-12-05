package com.moviemanagment.moviemanagment.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.moviemanagment.moviemanagment.util.MovieGenre;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDateTime;


public record SaveMovie(
        @Size(min=4,max=255, message = "{generic.size}")
        @NotBlank(message = "{generic.notblank}")
        String title,
        @Size(min=4,max=255, message = "{generic.size}")
        //@NotBlank
        String director,

        MovieGenre genre,
        @Min(value=1900, message = "{generic.min}")
        @JsonProperty(value="release_year")
        int releaseYear

        //solo para prueba de formato fecha sin el autogenerado
        /*@JsonProperty("availability_end_time")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        @Future
        LocalDateTime availabilityEndTime*/

) implements Serializable {

}
