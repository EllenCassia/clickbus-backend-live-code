package br.com.clickbus.challenge.controller.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.HttpStatus;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import br.com.clickbus.challenge.exception.PlaceNotFoundException;

@ControllerAdvice
class ControllerExceptions {

    @ExceptionHandler(PlaceNotFoundException.class)
    public ResponseEntity<StandarError> handleNotFoundException(PlaceNotFoundException ex, HttpServletRequest request) {
        StandarError error = 
                new StandarError(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), ex.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
    
}