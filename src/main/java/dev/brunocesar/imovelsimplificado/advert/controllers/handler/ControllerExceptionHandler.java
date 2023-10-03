package dev.brunocesar.imovelsimplificado.advert.controllers.handler;

import dev.brunocesar.imovelsimplificado.advert.exceptions.ApplicationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ControllerExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(ControllerExceptionHandler.class);


    @ExceptionHandler
    public ResponseEntity<ApplicationErrorResponse> handleApplicationException(ApplicationException ex) {
        var response = new ApplicationErrorResponse();
        response.setHttpStatus(ex.getHttpStatus());
        response.setErrorMessage(List.of(ex.getMessage()));
        LOG.error("Erro: [{}].", ex.getMessage(), ex);
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApplicationErrorResponse> handleValidationErrors(MethodArgumentNotValidException ex) {
        var errors = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList());

        var response = new ApplicationErrorResponse();
        response.setHttpStatus(HttpStatus.BAD_REQUEST.value());
        response.setErrorMessage(errors);
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<ApplicationErrorResponse> handlerMissingRequestHeaderException(MissingRequestHeaderException ex) {
        var response = new ApplicationErrorResponse();
        response.setHttpStatus(HttpStatus.UNAUTHORIZED.value());
        response.setErrorMessage(List.of("Acesso negado. Você deve estar autenticado no sistema para acessar a URL solicitada."));
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }
}