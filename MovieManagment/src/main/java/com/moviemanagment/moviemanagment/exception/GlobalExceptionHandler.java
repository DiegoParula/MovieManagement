package com.moviemanagment.moviemanagment.exception;

import com.moviemanagment.moviemanagment.dto.response.ApiError;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
            Exception.class,
            ObjectNotFoundException.class,
            InvalidPasswordException.class,
            MethodArgumentTypeMismatchException.class,
            MethodArgumentNotValidException.class,
            HttpRequestMethodNotSupportedException.class,
            HttpMediaTypeNotSupportedException.class,
            HttpMessageNotReadableException.class,
            DuplicatedRatingException.class
    })
    public ResponseEntity<ApiError> handleAllException(Exception ex, HttpServletRequest request, HttpServletResponse response){

        int httpStatus = HttpStatus.INTERNAL_SERVER_ERROR.value();
        LocalDateTime timestamp = LocalDateTime.now();

        if (ex instanceof ObjectNotFoundException objectNotFoundException){
            return this.handleObjectNotFoundException(objectNotFoundException, request, response, timestamp);
        }else if (ex instanceof InvalidPasswordException invalidPasswordException){
            return this.handleInvalidPasswordException(invalidPasswordException, request, response, timestamp);
        }
        else if (ex instanceof MethodArgumentTypeMismatchException methodArgumentTypeMismatchException){
            return this.handleMethodArgumentTypeMismatchException(methodArgumentTypeMismatchException, request, response, timestamp);
        }
        else if (ex instanceof MethodArgumentNotValidException methodArgumentNotValidException){
            return this.handleMethodArgumentNotValidException(methodArgumentNotValidException, request, response, timestamp);
        }
        else if (ex  instanceof HttpRequestMethodNotSupportedException httpRequestMethodNotSupportedException){
            return this.handleHttpRequestMethodNotSupportedException(httpRequestMethodNotSupportedException, request, response, timestamp);
        }
        else if (ex instanceof HttpMediaTypeNotSupportedException httpMediaTypeNotSupportedException){
            return this.handleHttpMediaTypeNotSupportedException(httpMediaTypeNotSupportedException, request, response, timestamp );
        }
        else if (ex instanceof HttpMessageNotReadableException httpMessageNotReadableException){
            return this.handleHttpMessageNotReadableException(httpMessageNotReadableException, request, response, timestamp);
        }
        else if (ex instanceof DuplicatedRatingException duplicatedRatingException){
            return this.handleDuplicatedRatingException(duplicatedRatingException, request, response, timestamp);
        }

        else {
            return this.handleException(ex, request, response, timestamp);
        }




    }

    private ResponseEntity<ApiError> handleDuplicatedRatingException(DuplicatedRatingException duplicatedRatingException, HttpServletRequest request, HttpServletResponse response, LocalDateTime timestamp) {
        int httpStatus = HttpStatus.CONFLICT.value();


        ApiError apiError= new ApiError(
                httpStatus,
                request.getRequestURL().toString(),
                request.getMethod(),
                duplicatedRatingException.getMessage(),
                duplicatedRatingException.getMessage(),
                timestamp,
                null


        );
        return ResponseEntity.status(httpStatus).body(apiError);
    }

    private ResponseEntity<ApiError> handleHttpMessageNotReadableException(HttpMessageNotReadableException httpMessageNotReadableException, HttpServletRequest request, HttpServletResponse response, LocalDateTime timestamp) {
        int httpStatus = HttpStatus.BAD_REQUEST.value();


        ApiError apiError= new ApiError(
                httpStatus,
                request.getRequestURL().toString(),
                request.getMethod(),
                "Error reading the HTTP message body." +
                        "Make sure the request is correctly formatted and contains valida data.",
                httpMessageNotReadableException.getMessage(),
                timestamp,
                null


        );
        return ResponseEntity.status(httpStatus).body(apiError);
    }

    private ResponseEntity<ApiError> handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException httpMediaTypeNotSupportedException, HttpServletRequest request, HttpServletResponse response, LocalDateTime timestamp) {
        int httpStatus = HttpStatus.UNSUPPORTED_MEDIA_TYPE.value();


        ApiError apiError= new ApiError(
                httpStatus,
                request.getRequestURL().toString(),
                request.getMethod(),
                "Unsupported Media Type: The server is unable to process the requested entity in the format provided in the request." +
                        "Supported media types are: "+ httpMediaTypeNotSupportedException.getSupportedMediaTypes() +
                        " and you send: " + httpMediaTypeNotSupportedException.getContentType(),
                httpMediaTypeNotSupportedException.getMessage(),
                timestamp,
                null


        );
        return ResponseEntity.status(httpStatus).body(apiError);
    }

    private ResponseEntity<ApiError> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException httpRequestMethodNotSupportedException, HttpServletRequest request, HttpServletResponse response, LocalDateTime timestamp) {

        int httpStatus = HttpStatus.METHOD_NOT_ALLOWED.value();


        ApiError apiError= new ApiError(
                httpStatus,
                request.getRequestURL().toString(),
                request.getMethod(),
                "Method not allowed. Check the HTTP method of your request.",
                httpRequestMethodNotSupportedException.getMessage(),
                timestamp,
                null


        );
        return ResponseEntity.status(httpStatus).body(apiError);
    }

    private ResponseEntity<ApiError> handleMethodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException, HttpServletRequest request, HttpServletResponse response, LocalDateTime timestamp) {
        int httpStatus = HttpStatus.BAD_REQUEST.value();

        List<ObjectError> errors = methodArgumentNotValidException.getAllErrors();
        List<String> details = errors.stream().map(error ->{
            if(error instanceof FieldError fieldError){
                return fieldError.getField() + ": " + fieldError.getDefaultMessage();
            }
            return error.getDefaultMessage();

        }).toList();

        ApiError apiError= new ApiError(
                httpStatus,
                request.getRequestURL().toString(),
                request.getMethod(),
                "The request contains invalid or incomplete parameters. " +
                        "Please verify and provide the required information before trying again.",
                methodArgumentNotValidException.getMessage(),
                timestamp,
                details


        );
        return ResponseEntity.status(httpStatus).body(apiError);
    }

    private ResponseEntity<ApiError> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException methodArgumentTypeMismatchException, HttpServletRequest request, HttpServletResponse response, LocalDateTime timestamp) {
        int httpStatus = HttpStatus.BAD_REQUEST.value();
        Object valueRejected = methodArgumentTypeMismatchException.getValue();
        String propertyName = methodArgumentTypeMismatchException.getName();

        ApiError apiError= new ApiError(
                httpStatus,
                request.getRequestURL().toString(),
                request.getMethod(),
                "Invalid Request: the provided value " + valueRejected + "does not have the expected data type for the " + propertyName + " property",
                methodArgumentTypeMismatchException.getMessage(),
                timestamp,
                null


        );
        return ResponseEntity.status(httpStatus).body(apiError);
    }

    private ResponseEntity<ApiError> handleException(Exception ex, HttpServletRequest request, HttpServletResponse response, LocalDateTime timestamp) {
        int httpStatus = HttpStatus.INTERNAL_SERVER_ERROR.value();
        ApiError apiError= new ApiError(
                httpStatus,
                request.getRequestURL().toString(),
                request.getMethod(),
                "Something went wrong on our server. Please try again later.",
                ex.getMessage(),
                timestamp,
                null


        );
        return ResponseEntity.status(httpStatus).body(apiError);
    }

    private ResponseEntity<ApiError> handleInvalidPasswordException(InvalidPasswordException invalidPasswordException, HttpServletRequest request, HttpServletResponse response, LocalDateTime timestamp) {
        int httpStatus = HttpStatus.BAD_REQUEST.value();
        ApiError apiError= new ApiError(
                httpStatus,
                request.getRequestURL().toString(),
                request.getMethod(),
                "Invalid Password: The provided password is incorrect, does not meet required criteria, " + invalidPasswordException.getErrorDescription(),
                invalidPasswordException.getMessage(),
                timestamp,
                null


        );
        return ResponseEntity.status(httpStatus).body(apiError);
    }

    private ResponseEntity<ApiError> handleObjectNotFoundException(ObjectNotFoundException objectNotFoundException, HttpServletRequest request, HttpServletResponse response, LocalDateTime timestamp) {
        int httpStatus = HttpStatus.NOT_FOUND.value();
        ApiError apiError= new ApiError(
                httpStatus,
                request.getRequestURL().toString(),
                request.getMethod(),
                "The request information could not be found. Please check the url or tru another search.",
                objectNotFoundException.getMessage(),
                timestamp,
                null


        );
        return ResponseEntity.status(httpStatus).body(apiError);
    }
}
