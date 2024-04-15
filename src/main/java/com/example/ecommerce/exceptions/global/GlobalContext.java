package com.example.ecommerce.exceptions.global;


import com.example.ecommerce.exceptions.InditexParametersNotValid;
import com.example.ecommerce.exceptions.InditexPriceNotFound;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@Slf4j
@ControllerAdvice
public final class GlobalContext {

    private static final String error = "error";
    @ExceptionHandler(InditexPriceNotFound.class)
    public ResponseEntity<InditexPriceNotFound> handleInditexPriceNotFound(InditexPriceNotFound exception,
                                                                               WebRequest request) {
        return new ResponseEntity<>(new InditexPriceNotFound(error), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InditexParametersNotValid.class)
    public ResponseEntity<InditexParametersNotValid> handleInditexParametersNotValid(InditexParametersNotValid exception,
                                                                                   WebRequest request) {
        return new ResponseEntity<>(new InditexParametersNotValid(error), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Exception> handleException(Exception exception, WebRequest request) {
        log.error("Exception received, message: <{}>", exception.getMessage());
        return new ResponseEntity<>(new Exception(error), HttpStatus.BAD_REQUEST);
    }
}