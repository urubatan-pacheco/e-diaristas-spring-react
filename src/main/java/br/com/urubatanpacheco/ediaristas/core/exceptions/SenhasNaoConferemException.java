package br.com.urubatanpacheco.ediaristas.core.exceptions;

import org.springframework.validation.FieldError;

public class SenhasNaoConferemException extends ValidacaoException {

    public SenhasNaoConferemException(String message, FieldError fieldError) {
        super(message, fieldError);
    }
    
}
