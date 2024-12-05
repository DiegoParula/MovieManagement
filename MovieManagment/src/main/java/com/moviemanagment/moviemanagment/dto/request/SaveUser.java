package com.moviemanagment.moviemanagment.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;


import java.io.Serializable;

public record SaveUser(
        @Pattern(regexp = "[a-zA-z-9-_]{8,255}", message = "{saveUser.username.pattern}")
        String username,
        //@NotBlank
        @Size(min = 5, max = 255, message = "{generic.size}")
        String name,

        @NotBlank(message = "{generic.notblank}")
        @Size(min = 10, max = 255, message = "{generic.size}")
        String password,
        @JsonProperty(value = "password_repeated")
        @NotBlank(message = "{generic.notblank}")
        @Size(min = 10, max = 255, message = "{generic.size}")
        String passwordRepeated
) implements Serializable {
}
