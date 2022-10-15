package br.com.urubatanpacheco.ediaristas.api.handlers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;

import br.com.urubatanpacheco.ediaristas.api.dtos.responses.ErrorResponse;
import br.com.urubatanpacheco.ediaristas.core.exceptions.ValidacaoException;
import br.com.urubatanpacheco.ediaristas.core.services.consultaEndereco.exceptions.EnderecoServiceException;

@RestControllerAdvice(annotations = RestController.class)
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
    private SnakeCaseStrategy camelCaseToSnakeCase =  new SnakeCaseStrategy();
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
        MethodArgumentNotValidException ex,
            HttpHeaders headers, 
            HttpStatus status, 
            WebRequest request) {
                var body = new HashMap<String, List<String>>();

                ex.getBindingResult().getFieldErrors().forEach(fieldError -> {
                    var field = camelCaseToSnakeCase.translate(fieldError.getField()) ;
                    if (!body.containsKey(field)) {
                        var fieldErrors = new ArrayList<String>();
                        fieldErrors.add(fieldError.getDefaultMessage());
                        body.put(field, fieldErrors);
                    } else {
                        body.get(field).add(fieldError.getDefaultMessage());
                    }
                });

                return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(EnderecoServiceException.class)
    public  ResponseEntity<Object> handleEnderecoServiceException(EnderecoServiceException exception, HttpServletRequest request) {
        var errorResponse = ErrorResponse.builder()
            .status(HttpStatus.BAD_REQUEST.value())
            .timestamp(LocalDateTime.now())
            .message(exception.getLocalizedMessage())
            .path(request.getRequestURI())
            .build();

        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(ValidacaoException.class)
    public  ResponseEntity<Object> handleValidacaoException(ValidacaoException exception) {
        var body = new HashMap<String, List<String>>();
        var fieldError = exception.getFieldError();
        var fieldErrors = new ArrayList<String>();

        fieldErrors.add(fieldError.getDefaultMessage());
        var field = camelCaseToSnakeCase.translate(fieldError.getField());

        body.put(field, fieldErrors );


        return ResponseEntity.badRequest().body(body);
    }
}

