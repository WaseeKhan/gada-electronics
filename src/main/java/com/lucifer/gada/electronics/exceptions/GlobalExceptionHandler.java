package com.lucifer.gada.electronics.exceptions;

import com.lucifer.gada.electronics.payload.ApiResponseMessage;
;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler {

    Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponseMessage> resourceNotFoundExceptionHandler(ResourceNotFoundException ex){

        logger.info("Global Exception Handler Invoked!!");

        ApiResponseMessage response = ApiResponseMessage.builder().message(ex.getMessage()).status(HttpStatus.NOT_FOUND).success(true).build();

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }



    //ConstraintViolationException

    //DataIntegrityViolationExceptionHandler

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponseMessage> DataIntegrityViolationExceptionHandler(DataIntegrityViolationException ex){

        logger.info("DataIntegrityViolationExceptionHandler Method Invoked!!");

        ApiResponseMessage response = ApiResponseMessage.builder().message(ex.getMessage()).success(true).status(HttpStatus.BAD_REQUEST).build();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

    }


    @ExceptionHandler(BadApiRequest.class)
    public ResponseEntity<ApiResponseMessage> HandleBadApiRequest(BadApiRequest ex){

        logger.info("Global HandleBadApiRequest Invoked!! BAD API REQUEST");

        ApiResponseMessage response = ApiResponseMessage.builder().message(ex.getMessage()).status(HttpStatus.BAD_REQUEST).success(false).build();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


}
