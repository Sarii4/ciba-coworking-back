package com.cibacoworking.cibacoworking.exception;

import java.nio.file.AccessDeniedException;

import javax.naming.AuthenticationException;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.cibacoworking.cibacoworking.models.BodyErrorMessage;

@ControllerAdvice
public class CibaCoworkingResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CibaCoworkingException.class)
    public ResponseEntity<BodyErrorMessage> handleSanSaException(CibaCoworkingException exception) {
        HttpStatus httpStatus = exception.getHttpStatus();
        if (httpStatus == null) {
            httpStatus = HttpStatus.NOT_FOUND;
        }
        BodyErrorMessage bodyErrorMessage = new BodyErrorMessage();
        bodyErrorMessage.setHttpStatus(httpStatus.value());
        bodyErrorMessage.setMessage(exception.getMessage());
        return ResponseEntity.status(httpStatus).body(bodyErrorMessage);
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<BodyErrorMessage> handleConnectionError(DataAccessException ex) {
        BodyErrorMessage response = new BodyErrorMessage();
        response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.setMessage("Error en la conexió de la base de dades: " + ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BodyErrorMessage> handleGeneralException(Exception ex) {
        BodyErrorMessage message = new BodyErrorMessage();
        message.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        message.setMessage("Error inesperat " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<String> handleAuthenticationException(AuthenticationException ex, WebRequest request) {

        System.err.println("Error d'autenticació: " + ex.getMessage());
        return new ResponseEntity<>("Autenticació fallida: " + ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> handleAccessDeniedException(AccessDeniedException ex, WebRequest request) {

        System.err.println("Accés denegat: " + ex.getMessage());
        return new ResponseEntity<>("Accés denegat: " + ex.getMessage(), HttpStatus.FORBIDDEN);
    }
}