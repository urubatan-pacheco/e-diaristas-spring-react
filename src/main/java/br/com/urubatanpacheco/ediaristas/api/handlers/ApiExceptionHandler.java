package br.com.urubatanpacheco.ediaristas.api.handlers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.AbstractMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;

import br.com.urubatanpacheco.ediaristas.api.dtos.responses.ErrorResponse;
import br.com.urubatanpacheco.ediaristas.core.exceptions.TokenNaBlackListException;
import br.com.urubatanpacheco.ediaristas.core.exceptions.ValidacaoException;
import br.com.urubatanpacheco.ediaristas.core.services.consultaEndereco.exceptions.EnderecoServiceException;
import br.com.urubatanpacheco.ediaristas.core.services.token.exceptions.TokenServiceException;

@RestControllerAdvice(annotations = RestController.class)
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
    private SnakeCaseStrategy camelCaseToSnakeCase =  new SnakeCaseStrategy();

    @ExceptionHandler(EnderecoServiceException.class)
    public ResponseEntity<Object> handleEnderecoServiceException(EnderecoServiceException exception, HttpServletRequest request) {
        return criarErroResponse(HttpStatus.BAD_REQUEST, exception.getLocalizedMessage(), request.getRequestURI());
    }    


    @ExceptionHandler(ValidacaoException.class)
    public  ResponseEntity<Object> handleValidacaoException(ValidacaoException exception, HttpServletRequest request) {
        var body = new HashMap<String, List<String>>();
        var fieldError = exception.getFieldError();
        var fieldErrors = new ArrayList<String>();

        fieldErrors.add(fieldError.getDefaultMessage());
        var field = camelCaseToSnakeCase.translate(fieldError.getField());

        body.put(field, fieldErrors );

        return criarErroResponse(HttpStatus.BAD_REQUEST, exception.getLocalizedMessage(), request.getRequestURI(), body);
    }


    @ExceptionHandler(TokenServiceException.class)
    public  ResponseEntity<Object>  handleTokenServiceException(TokenServiceException exception, HttpServletRequest request) {
        return criarErroResponse(HttpStatus.UNAUTHORIZED, exception.getLocalizedMessage(), request.getRequestURI());
    }

    @ExceptionHandler(TokenNaBlackListException.class)
    public  ResponseEntity<Object> handleTokenNaBlackListException(TokenNaBlackListException exception, HttpServletRequest request) {
        return criarErroResponse(HttpStatus.UNAUTHORIZED, exception.getLocalizedMessage(), request.getRequestURI());
    }



    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
        MethodArgumentNotValidException ex,
            HttpHeaders headers, 
            HttpStatus status, 
            WebRequest request) {
                return handleBindException(ex, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleBindException(
        BindException ex, 
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

    private ResponseEntity<Object> criarErroResponse(HttpStatus status, String message, String path) {
        return criarErroResponse( status,  message,  path, null);
    }

        private ResponseEntity<Object> criarErroResponse(HttpStatus status, String message, String path,AbstractMap<String, List<String>> errors) {
        var errorResponse = ErrorResponse.builder()
            .status(status.value())
            .timestamp(LocalDateTime.now())
            .message(message)
            .path(path)
            .errors(errors)
            .build();

        return new ResponseEntity<>(errorResponse, status);
    }  


    
    
}

